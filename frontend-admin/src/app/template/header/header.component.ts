import { Component, CUSTOM_ELEMENTS_SCHEMA, OnInit } from "@angular/core";
import { AuthService } from "../../auth/services/auth.service";
import { Router, RouterLink, RouterLinkActive } from "@angular/router";
import { AsyncPipe } from "@angular/common";
import { ConfigService } from "../../services/config/config.service";
import { NotificationService } from "../../users/services/notification.service";
import { NotificationResponse } from "../../users/modules/notification";

@Component({
  selector: "app-header",
  standalone: true,
  templateUrl: "./header.component.html",
  styleUrl: "./header.component.css",
  imports: [RouterLink, RouterLinkActive, AsyncPipe],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class HeaderComponent implements OnInit {
  userId: string | null = null;
  notificationsCount: number | null = null;
  constructor(
    private readonly authService: AuthService,
    private readonly notificationService: NotificationService,
    private readonly configService: ConfigService,
    private readonly router: Router
  ) {
    this.userId = this.authService.getUserId();
  }

  ngOnInit(): void {
    this.getNotifications();
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
    return this.authService.hasRole("ADMIN");
  }

  // Method to get all gargo
  getNotifications(): void {
    this.notificationService.show().subscribe({
      next: (data: NotificationResponse | null) => {
        this.notificationsCount = data?.notifications ?? 0;
      },
      error: () => {
        this.configService.showErrorAlert(
          "Une erreur est survenue lors de la récupération des data."
        );
      },
    });
  }
  onNotificationsClick(): void {
    if ((this.notificationsCount ?? 0) > 0) {
      this.notificationService.update().subscribe({
        next: () => {
          this.notificationsCount = 0; // reset badge
          this.router.navigate(["/notifications"]); // ✅ navigate after update
        },
        error: () => {
          this.configService.showErrorAlert(
            "Erreur lors de la mise à jour des notifications."
          );
          this.router.navigate(["/notifications"]); // still navigate on error
        },
      });
    } else {
      this.router.navigate(["/notifications"]); // if no unread, just navigate
    }
  }
}
