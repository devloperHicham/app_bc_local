import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { TranslateModule, TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-trip-comp-detail',
  standalone: true,
  imports: [CommonModule, RouterLink, TranslateModule],
  templateUrl: './trip-comp-detail.html',
  styleUrl: './trip-comp-detail.css',
})
export class TripCompDetail {
  isTermsOpen: boolean = false;
  isContactsOpen: boolean = false;

  constructor(
    private readonly cdr: ChangeDetectorRef,
    private readonly translate: TranslateService
  ) {}

  toggleSection(section: 'terms' | 'contacts') {
    if (section === 'terms') {
      this.isTermsOpen = !this.isTermsOpen;
    } else if (section === 'contacts') {
      this.isContactsOpen = !this.isContactsOpen;
    }
  }

  handleKeyDown(event: KeyboardEvent, section: 'terms' | 'contacts') {
    if (event.key === 'Enter' || event.key === ' ') {
      this.toggleSection(section);
      event.preventDefault(); // prevent scrolling on space
    }
  }
}
