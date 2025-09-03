import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, EventEmitter, Output } from '@angular/core';
import { RouterLink } from '@angular/router';
import { TranslateModule, TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-search-comp-detail',
  standalone:true,
  imports: [RouterLink, CommonModule, TranslateModule],
  templateUrl: './search-comp-detail.html',
  styleUrl: './search-comp-detail.css',
})
export class SearchCompDetail {
  @Output() closeEvent = new EventEmitter<void>();
  activeSection: 'rate' | 'terms' | null = 'rate';

  constructor(private readonly cdr: ChangeDetectorRef, private readonly translate :TranslateService) {}

  close() {
    this.closeEvent.emit();
  }

  toggleSection(section: 'rate' | 'terms') {
    this.activeSection = this.activeSection === section ? null : section;
  }

  isRateOpen(): boolean {
    return this.activeSection === 'rate';
  }

  isTermsOpen(): boolean {
    return this.activeSection === 'terms';
  }

    handleKeyDown(event: KeyboardEvent, section: 'terms' | 'rate') {
    if (event.key === 'Enter' || event.key === ' ') {
      this.toggleSection(section);
      event.preventDefault(); // prevent scrolling on space
    }
  }
}
