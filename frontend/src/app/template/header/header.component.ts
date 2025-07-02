import { Component, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { AuthService } from '../../auth/services/auth.service';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { AsyncPipe } from '@angular/common'; // <-- Add this import

@Component({
  selector: 'app-header',
  standalone: true,
  templateUrl: './header.component.html',
  styleUrl: './header.component.css',
  imports: [RouterLink, RouterLinkActive, AsyncPipe],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class HeaderComponent {
  userId: string | null = null;
  constructor(private readonly authService: AuthService) {
    this.userId = this.authService.getUserId();
  }

  // Get current user observable
  get currentUser$() {
    return this.authService.currentUser$;
  }

  logout(): void {
    this.authService.logout();
  }
  /**
   * Checks if the user is an admin or not.
   *
   * @returns {boolean} true if the user is an admin, false otherwise
   */
  isAdmin(): boolean {
    return this.authService.hasRole('ADMIN');
  }
}
