import { CommonModule } from '@angular/common';
import {
  ChangeDetectorRef,
  Component,
  EventEmitter,
  inject,
  Input,
  Output,
} from '@angular/core';
import { Router } from '@angular/router';
import { TranslateModule, TranslateService } from '@ngx-translate/core';
import { ConfigService } from '../../../services/config/config';
import { HeroService } from '../../home/services/hero-service';
import { Comparison } from '../../home/modules/comparison';

@Component({
  selector: 'app-search-comp-detail',
  standalone: true,
  imports: [CommonModule, TranslateModule],
  templateUrl: './search-comp-detail.html',
  styleUrl: './search-comp-detail.css',
})
export class SearchCompDetail {
  @Input() comparisons!: Comparison; // this for pass data from parent
  @Output() closeDetail = new EventEmitter<void>();
  @Output() closeEvent = new EventEmitter<void>();
  activeSection: 'rate' | 'terms' | null = 'rate';
  private readonly configService = inject(ConfigService);
  private readonly heroService = inject(HeroService);
  private readonly router = inject(Router);

  constructor(
    private readonly cdr: ChangeDetectorRef,
    private readonly translate: TranslateService
  ) {}

  calculateDaysBetween(startDateStr: string, endDateStr: string): number {
    // Convert DD/MM/YYYY to Date objects
    const startParts = startDateStr.split('/');
    const endParts = endDateStr.split('/');

    const startDate = new Date(
      parseInt(startParts[2]), // year
      parseInt(startParts[1]) - 1, // month (0-indexed)
      parseInt(startParts[0]) // day
    );

    const endDate = new Date(
      parseInt(endParts[2]), // year
      parseInt(endParts[1]) - 1, // month (0-indexed)
      parseInt(endParts[0]) // day
    );

    // Calculate difference in milliseconds and convert to days
    const timeDiff = endDate.getTime() - startDate.getTime();
    const daysDiff = Math.ceil(timeDiff / (1000 * 3600 * 24));

    return daysDiff;
  }

  //this for book and pass date to detail page
  bookNow(comp: Comparison): void {
    this.heroService.setComparison(comp);
    this.router.navigate(['/trip-comp-details']);
  }

  /*************************************************************************** */
  /**************************this is for design ***************************** */
  /*************************************************************************** */
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
