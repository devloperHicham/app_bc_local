import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

export const roleRedirectGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  const userRole = authService.hasRole('ADMIN'); // 'admin' or 'user'

  if (userRole) {
    return router.createUrlTree(['/home-admin']);
  } else {
    return router.createUrlTree(['/home-users']);
  }
};
