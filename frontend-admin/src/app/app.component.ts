import { Component, inject, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavComponent } from './template/nav/nav.component';
import { HeaderComponent } from './template/header/header.component';
import { AuthService } from './auth/services/auth.service';
import { Router, RouterOutlet } from '@angular/router';
import { filter, take, Subscription } from 'rxjs';
import { TokenStorageService } from './auth/services/token-storage.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, CommonModule, NavComponent, HeaderComponent],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent implements OnInit, OnDestroy {
  title = 'Application Operateurs';
  showLayout = false;
  private authSubscription?: Subscription;

  private readonly authService = inject(AuthService);
  private readonly router = inject(Router);
  private readonly tokenStorage = inject(TokenStorageService);

  ngOnInit() {
    // Check if the token is expired on initialization
    this.checkTokenExpiration();
    this.setupAuthMonitoring();
  }

  ngOnDestroy() {
    this.authSubscription?.unsubscribe();
  }

  private checkTokenExpiration(): void {
    if (this.tokenStorage.isTokenExpired()) {
      this.authService.logout();
      this.router.navigate(['/login'], {
        queryParams: { reason: 'session_expired' },
      });
    }
  }

  private setupAuthMonitoring(): void {
    this.authSubscription = this.authService.isAuthenticated$
      .pipe(filter((isAuthenticated) => isAuthenticated !== null))
      .subscribe({
        next: (isAuthenticated) => {
          this.showLayout = isAuthenticated;
          if (isAuthenticated) {
            this.handleAuthenticatedUser();
          } else {
            this.navigateToLogin();
          }
        },
        error: (err) => {
          console.error('Auth error:', err);
          this.navigateToLogin();
        },
      });
  }

  private handleAuthenticatedUser(): void {
    this.authService.currentUser$.pipe(take(1)).subscribe({
      next: (user) => {
        const route = user?.role === 'ADMIN' ? '/home-admin' : '/home-users';
        this.router.navigate([route]);
      },
      error: () => this.navigateToLogin(),
    });
  }

  private navigateToLogin(): void {
    if (!this.router.url.includes('login')) {
      this.router.navigate(['/login']);
    }
  }
}
