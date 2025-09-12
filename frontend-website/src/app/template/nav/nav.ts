import { CommonModule } from "@angular/common";
import {
  ChangeDetectorRef,
  Component,
  HostListener,
  inject,
  signal,
} from "@angular/core";
import { DropdownProfile } from "../../admin/dropdown-profile/dropdown-profile";
import { NavigationEnd, Router } from "@angular/router";
import { AddQuote } from "../quotes/add-quote/add-quote";
import { TranslateModule, TranslateService } from "@ngx-translate/core";
import { AuthService } from "../../auth/services/auth.service";

@Component({
  selector: "app-nav",
  standalone: true,
  imports: [DropdownProfile, CommonModule, AddQuote, TranslateModule],
  templateUrl: "./nav.html",
  styleUrls: ["./nav.css"],
})
export class Nav {
  isModalOpen: boolean = false;
  private readonly router = inject(Router);
  activeMenuItem = signal<string | null>("home");
  currentLanguage: string = "en";
  isOpen = false;
  private readonly authService = inject(AuthService);
  public readonly auth$ = this.authService.isAuthenticated$; // Observable for authentication status

  constructor(
    private readonly translate: TranslateService,
    private readonly cdr: ChangeDetectorRef
  ) {
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        const url = event.urlAfterRedirects;
        if (url.includes("marketplaces"))
          this.activeMenuItem.set("marketplaces");
        else if (url.includes("comming-soon"))
          this.activeMenuItem.set("comming-soon");
        else if (url.includes("about-us")) this.activeMenuItem.set("about-us");
        else if (url.includes("contacts")) this.activeMenuItem.set("contacts");
        else if (url.includes("prices")) this.activeMenuItem.set("prices");
        else this.activeMenuItem.set("home");
      }
    });
  }

  handleMenuItemClick(action: string): void {
    switch (action) {
      case "home":
        this.router.navigate(["home"]);
        break;
      case "marketplaces":
        window.open("https://marketplace.booking-container.com", "_blank");
        break;
      case "about-us":
        this.router.navigate(["about-us"]);
        break;
      case "comming-soon":
        this.router.navigate(["comming-soon"]);
        break;
      case "contacts":
        this.router.navigate(["contacts"]);
        break;
      case "prices":
        this.router.navigate(["prices"]);
        break;
      case "login":
        this.router.navigate(["login"]);
        break;
      case "inscription-users":
        this.router.navigate(["inscription-users"]);
        break;
    }
  }

  onKeyMenuItem(event: KeyboardEvent, action: string): void {
    if (event.key === "Enter" || event.key === " ") {
      event.preventDefault();
      this.handleMenuItemClick(action);
    }
  }

  openModal() {
    this.isModalOpen = true;
  }

  closeModal() {
    this.isModalOpen = false;
  }

  toggleDropdown(): void {
    this.isOpen = !this.isOpen;
  }

  @HostListener("document:click", ["$event"])
  onClickOutside(event: Event) {
    if (!(event.target as HTMLElement).closest(".language-selector")) {
      this.isOpen = false;
    }
  }

  changeLanguage(language: string): void {
    this.translate.use(language);
    this.currentLanguage = language;
    this.isOpen = false;
  }
}
