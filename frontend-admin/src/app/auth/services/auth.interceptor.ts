import { Injectable, inject } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse,
} from '@angular/common/http';
import { Observable, throwError, from } from 'rxjs';
import { catchError, switchMap, mergeMap, take } from 'rxjs/operators';
import { AuthService } from './auth.service';
import { Router } from '@angular/router';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  private readonly authService = inject(AuthService);
  private readonly router = inject(Router);

  private readonly skipUrls = [
    '/authauthentication/users/login',
    '/authentication/users/refresh',
    '/authentication/users/register',
  ];

  intercept(
    request: HttpRequest<unknown>,
    next: HttpHandler
  ): Observable<HttpEvent<unknown>> {
    // Skip interceptor for specific URLs
    if (this.skipUrls.some((url) => request.url.includes(url))) {
      return next.handle(request);
    }

    return from(this.authService.getAccessToken()).pipe(
      take(1),
      mergeMap((token) => {
        if (token) {
          request = this.addTokenToRequest(request, token);
        }
        return next.handle(request).pipe(
          catchError((error: HttpErrorResponse) => {
            if (error.status === 401) {
              return this.handle401Error(request, next);
            }
            return throwError(() => error);
          })
        );
      })
    );
  }

  private addTokenToRequest(
    request: HttpRequest<unknown>,
    token: string
  ): HttpRequest<unknown> {
    return request.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`,
      },
    });
  }

  private handle401Error(
    request: HttpRequest<unknown>,
    next: HttpHandler
  ): Observable<HttpEvent<unknown>> {
    return this.authService.refreshToken().pipe(
      switchMap((tokens) => {
        const newRequest = this.addTokenToRequest(
          request,
          tokens.response?.accessToken ?? tokens.accessToken!
        );
        return next.handle(newRequest);
      }),
      catchError((error) => {
        this.authService.logout();
        this.router.navigate(['/login']);
        return throwError(() => error);
      })
    );
  }
}
