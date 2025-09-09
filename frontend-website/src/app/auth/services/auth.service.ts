import { Injectable, inject, makeEnvironmentProviders } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { BehaviorSubject, Observable, throwError, of } from 'rxjs';
import {
  catchError,
  map,
  tap,
  switchMap,
  filter,
  take,
  finalize,
} from 'rxjs/operators';
import { Router } from '@angular/router';
import { TokenStorageService } from './token-storage.service';
import { InactivityService } from '../utils/inactivity.service';
import { AuthState } from '../modules/auth-state.model';
import { AuthUser } from '../modules/auth-user.model';
import { AuthTokens, DecodedToken } from '../modules/auth-tokens.model';
import { ConfigService } from '../../services/config/config';
import { jwtDecode } from 'jwt-decode';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly authState$ = new BehaviorSubject<AuthState>({
    user: null,
    isAuthenticated: false,
    error: null,
    isLoading: false,
  });

  private readonly http = inject(HttpClient);
  private readonly tokenStorage = inject(TokenStorageService);
  private readonly router = inject(Router);
  private readonly inactivityService = inject(InactivityService);
  private readonly configService = inject(ConfigService);
  private refreshTokenInProgress = false;

  // Messages error
  private readonly errorMessages = {
    NETWORK_ERROR: 'Erreur réseau. Veuillez vérifier votre connexion.',
    UNEXPECTED_ERROR: 'Une erreur inattendue est survenue. Veuillez réessayer.',
    INVALID_TOKEN: "Jeton d'accès invalide",
    NO_REFRESH_TOKEN: 'Aucun jeton de rafraîchissement disponible',
    REFRESH_IN_PROGRESS: 'Rafraîchissement du jeton déjà en cours',
    USER_NOT_FOUND: 'Utilisateur introuvable après connexion',
    TOKEN_EXPIRED: 'Session expirée. Veuillez vous reconnecter.',
    LOGIN_FAILED: 'Échec de la connexion. Email ou mot de passe incorrect.',
  };

  constructor() {
    this.setupInactivityMonitoring();
    this.initializeAuthState();
  }

  initializeAuthState(): void {
    this.setLoading(true);
    const accessToken = this.tokenStorage.getAccessToken();

    if (!accessToken) {
      this.setLoading(false);
      return;
    }

    if (this.isTokenExpired(accessToken)) {
      this.handleExpiredToken();
      return;
    }

    const user = this.getUserFromToken(accessToken);
    this.authState$.next({
      user,
      isAuthenticated: true,
      error: null,
      isLoading: false,
    });
  }

  get currentUser$(): Observable<AuthUser | null> {
    return this.authState$.pipe(
      map((state) => state.user),
      filter(() => !this.authState$.value.isLoading)
    );
  }

  get isAuthenticated$(): Observable<boolean> {
    return this.authState$.pipe(
      map((state) => state.isAuthenticated),
      filter(() => !this.authState$.value.isLoading)
    );
  }

  get error$(): Observable<string | null> {
    return this.authState$.pipe(map((state) => state.error));
  }

  get isLoading$(): Observable<boolean> {
    return this.authState$.pipe(map((state) => state.isLoading));
  }

  login(credentials: {
    email: string;
    password: string;
  }): Observable<AuthUser> {
    this.setLoading(true);
    const url = `${this.configService.URL_API_AUTH}${this.configService.ENDPOINTS.login}`;

    return this.http.post<AuthTokens>(url, credentials).pipe(
      tap((tokens) => this.handleLoginSuccess(tokens)),
      map(() => {
        const user = this.authState$.value.user;
        if (!user) {
          throw new Error(this.errorMessages.USER_NOT_FOUND);
        }
        return user;
      }),
      catchError((error: HttpErrorResponse) => {
        this.setError(this.getErrorMessage(error));
        this.setLoading(false);
        return throwError(() => error);
      }),
      finalize(() => this.setLoading(false))
    );
  }

  logout(clearTokens: boolean = true): void {
    if (clearTokens) {
      this.tokenStorage.clear();
    }

    this.authState$.next({
      user: null,
      isAuthenticated: false,
      error: null,
      isLoading: false,
    });

    this.router.navigate(['/login']);
  }

  refreshToken(): Observable<AuthTokens> {
    if (this.refreshTokenInProgress) {
      return of(this.authState$.value).pipe(
        filter((state) => !state.isLoading),
        take(1),
        switchMap((state) => {
          if (state.isAuthenticated) {
            return of({} as AuthTokens);
          }
          return throwError(
            () => new Error(this.errorMessages.REFRESH_IN_PROGRESS)
          );
        })
      );
    }

    this.refreshTokenInProgress = true;
    this.setLoading(true);

    const refreshToken = this.tokenStorage.getRefreshToken();
    const url = `${this.configService.URL_API_AUTH}${this.configService.ENDPOINTS.refresh}`;

    if (!refreshToken) {
      this.logout(false);
      this.refreshTokenInProgress = false;
      return throwError(() => new Error(this.errorMessages.NO_REFRESH_TOKEN));
    }

    return this.http.post<AuthTokens>(url, { refreshToken }).pipe(
      tap((tokens) => this.handleRefreshTokenSuccess(tokens)),
      catchError((error: HttpErrorResponse) => {
        this.logout(false);
        this.setError(this.getErrorMessage(error));
        return throwError(() => error);
      }),
      finalize(() => {
        this.refreshTokenInProgress = false;
        this.setLoading(false);
      })
    );
  }

  getAccessToken(): Observable<string> {
    const accessToken = this.tokenStorage.getAccessToken();

    if (accessToken && !this.isTokenExpired(accessToken)) {
      return of(accessToken);
    }

    return this.refreshToken().pipe(
      map((tokens) => tokens.response?.accessToken ?? tokens.accessToken!)
    );
  }

  private handleLoginSuccess(tokens: AuthTokens): void {
    this.tokenStorage.saveTokens(tokens);
    const accessToken = tokens.response?.accessToken ?? tokens.accessToken!;
    const user = this.getUserFromToken(accessToken);

    this.authState$.next({
      user,
      isAuthenticated: true,
      error: null,
      isLoading: false,
    });

    this.redirectBasedOnRole(user.role);
  }

  private handleRefreshTokenSuccess(tokens: AuthTokens): void {
    this.tokenStorage.saveTokens(tokens);
    const accessToken = tokens.response?.accessToken ?? tokens.accessToken!;
    const user = this.getUserFromToken(accessToken);

    this.authState$.next({
      user,
      isAuthenticated: true,
      error: null,
      isLoading: false,
    });
  }

  private handleExpiredToken(): void {
    const refreshToken = this.tokenStorage.getRefreshToken();

    if (refreshToken && !this.isTokenExpired(refreshToken)) {
      this.refreshToken().subscribe({
        error: () => this.setError(this.errorMessages.TOKEN_EXPIRED),
      });
    } else {
      this.logout(false);
    }
  }
  getUserId(): string | null {
    return this.authState$.value.user?.id ?? null;
  }
  private getUserFromToken(token: string): AuthUser {

    try {
      const decoded = jwtDecode<DecodedToken>(token);

      return {
        id: decoded.userId,
        firstName: decoded.userFirstName ?? '',
        lastName: decoded.userLastName ?? '',
        email: decoded.userEmail ?? '',
        phoneNumber: decoded.userPhoneNumber ?? '',
        role: 'CLIENT',
        isAuthenticated: true,
      };
    } catch (error) {
      console.error('Erreur lors du décodage du jeton:', error);
      this.logout(false);
      throw new Error(this.errorMessages.INVALID_TOKEN);
    }
  }

  private isTokenExpired(token: string): boolean {
    try {
      const decoded = jwtDecode<DecodedToken>(token);
      return Date.now() >= decoded.exp * 1000;
    } catch {
      return true;
    }
  }

  private redirectBasedOnRole(role: string): void {
    this.router.navigateByUrl('/home');
  }

  private setupInactivityMonitoring(): void {
    this.inactivityService.startMonitoring();

    this.inactivityService.onInactive$.subscribe(() => {
      if (this.authState$.value.isAuthenticated) {
        this.logout();
      }
    });
  }

  private setError(error: string | null): void {
    this.authState$.next({ ...this.authState$.value, error });
  }

  private setLoading(isLoading: boolean): void {
    this.authState$.next({ ...this.authState$.value, isLoading });
  }

  private getErrorMessage(error: HttpErrorResponse): string {
    if (error.error?.message) {
      return error.error.message; // Keep server message as is
    }

    if (error.status === 0) {
      return this.errorMessages.NETWORK_ERROR;
    }

    if (error.status === 401) {
      return this.errorMessages.LOGIN_FAILED;
    }

    return this.errorMessages.UNEXPECTED_ERROR;
  }

  hasRole(requiredRole: string): boolean {
    const currentUser = this.authState$.value.user;
    return currentUser?.role === requiredRole;
  }
}

export function provideAuthService() {
  return makeEnvironmentProviders([AuthService]);
}
