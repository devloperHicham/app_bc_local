import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, HostListener, inject } from '@angular/core';
import { Router } from '@angular/router';
import { TranslateModule, TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-dropdown-profile',
  standalone: true,
  imports: [CommonModule, TranslateModule],
  templateUrl: './dropdown-profile.html',
  styleUrl: './dropdown-profile.css',
})
export class DropdownProfile {
  private readonly router = inject(Router);
  isDropdownOpen = false;
  userName = 'Haitame Hadraoui';
  userEmail = 'haitamehadraoui@gmail.com';
  userType = 'Shipper';

  constructor(private readonly cdr: ChangeDetectorRef, private readonly translate :TranslateService) {}
  
  toggleDropdown(event: Event): void {
    event.stopPropagation();
    this.isDropdownOpen = !this.isDropdownOpen;
  }

  onKeyToggle(event: KeyboardEvent): void {
    if (event.key === 'Enter' || event.key === ' ') {
      event.preventDefault(); // Prevent default behavior like scrolling
      this.toggleDropdown(event);
    }
  }

  handleMenuItemClick(action: string): void {
    switch (action) {
      case 'profiles':
        this.router.navigate(['profiles']);
        break;
      case 'whishlist-booking':
        this.router.navigate(['whishlist-booking']);
        break;
      case 'quote-lists':
        this.router.navigate(['quote-lists']);
        break;
      case 'manage-script':
        this.router.navigate(['manage-script']);
        break;
      case 'help-centers':
        this.router.navigate(['help-centers']);
        break;
      case 'logout':
        this.router.navigate(['login']);
        break;
    }
    this.isDropdownOpen = false;
  }

  onKeyMenuItem(event: KeyboardEvent, action: string): void {
    if (event.key === 'Enter' || event.key === ' ') {
      event.preventDefault(); // Prevent default behavior
      this.handleMenuItemClick(action);
    }
  }

  @HostListener('document:click', ['$event'])
  onDocumentClick(event: Event): void {
    if (this.isDropdownOpen) {
      this.isDropdownOpen = false;
    }
  }
}
