import { CommonModule } from '@angular/common';
import {
  AfterViewInit,
  Component,
  ElementRef,
  ViewChild,
  ChangeDetectorRef,
  OnDestroy,
  inject,
  OnInit,
} from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import flatpickr from 'flatpickr';
import 'flatpickr/dist/flatpickr.min.css';
import { SearchCompDetail } from '../search-comp-detail/search-comp-detail';
import { TranslateModule, TranslateService } from '@ngx-translate/core';
import { Comparison } from '../../home/modules/comparison';
import { HeroService } from '../../home/services/hero-service';
import { ConfigService } from '../../../services/config/config';
import { Company, Container, Transportation } from '../../../modules/data-json';
import { NgxSpinnerService } from 'ngx-spinner';
import { SharedModule } from '../../../share/share-module';

@Component({
  selector: 'app-search-comp-result',
  standalone: true,
  imports: [
    SharedModule,
    RouterLink,
    CommonModule,
    FormsModule,
    SearchCompDetail,
    TranslateModule,
  ],
  templateUrl: './search-comp-result.html',
  styleUrls: ['./search-comp-result.css'],
})
export class SearchCompResult implements OnInit, AfterViewInit, OnDestroy {
  private readonly heroService = inject(HeroService);
  private readonly configService = inject(ConfigService);
  private readonly router = inject(Router);
  selectedCompareison: Comparison | null = null;
  containers: Container[] = [];
  transportations: Transportation[] = [];
  companies: Company[] = [];
  selectedCompany: string | null = null;
  Math = Math;
  comparisons: Comparison[] = [];
  currentPage = 1;
  totalPages: number[] = [];
  pageSize = 50;
  totalRecords = 0;
  isLoading: boolean = false;
  filters: any = {};

  constructor(
    private readonly cdr: ChangeDetectorRef,
    private readonly translate: TranslateService,
    private readonly spinner: NgxSpinnerService,
  ) { }

  ngOnInit(): void {
    // Load containers data on initialization
    this.heroService.getContainers().subscribe((data) => {
      this.containers = data;
    });
    // Load transportations data on initialization
    this.heroService.getTransportations().subscribe((data) => {
      this.transportations = data;
    });
    // Load companies data on initialization
    this.heroService.getCompanies().subscribe((data) => {
      this.companies = data;
    });
    // Listen for navigation events
    this.spinner.show();
    this.filters = this.heroService.getForm() ?? {};
    this.loadPage(this.currentPage);
  }

  loadPage(page: number): void {
    this.spinner.show();
    const dtParams = {
      start: (page - 1) * this.pageSize,
      length: this.pageSize,
      draw: page,
      order: [{ column: 0, dir: 'asc' }],
      columns: [{ data: 'dateDepart' }],
    };

    this.heroService.getPaginatedDataComparison(dtParams, this.filters).subscribe({
      next: (res) => {
        this.comparisons = res.data || [];
        this.totalRecords = res.recordsTotal || 0;

        const totalPages = Math.ceil(this.totalRecords / this.pageSize);
        this.totalPages = Array.from({ length: totalPages }, (_, i) => i + 1);

        this.currentPage = page;
        this.spinner.hide();
        this.cdr.detectChanges();
      },
      error: (error) => {
        this.comparisons = [];
        this.totalRecords = 0;
        this.totalPages = [1];
        this.spinner.hide();
        this.cdr.detectChanges();

        // Show error message to user
        this.configService.showErrorAlert('Failed to load data. Please try again.');
      },
    });
  }

  previousPage(): void {
    if (this.currentPage > 1) this.loadPage(this.currentPage - 1);
  }

  nextPage(): void {
    if (this.currentPage < this.totalPages.length)
      this.loadPage(this.currentPage + 1);
  }

  trackById(index: number, cmp: Comparison) {
    return cmp.id;
  }
  togglePast(id: string): void {
    this.heroService.togglePastComparison(id).subscribe({
      next: (response) =>
        this.configService.showSuccessAlert('Successfully registered.'),
      error: () => console.log('error past schedule'),
    });
  }

  toggleFavorite(id: string): void {
    this.heroService.toggleFavoriteComparison(id).subscribe({
      next: (response) =>
        this.configService.showSuccessAlert('Successfully registered.'),
      error: () => console.log('error favorite schedule'),
    });
  }
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
  @ViewChild('intervalDateInput', { static: false })
  intervalDateInput?: ElementRef;
  @ViewChild('singleDateInput', { static: false }) singleDateInput?: ElementRef;

  private intervalPicker?: flatpickr.Instance;
  private singlePicker?: flatpickr.Instance;
  isModalOpen: boolean = false;
  isIntervalMode = false;

  ngAfterViewInit(): void {
    this.initAllDatePickers();
  }

  ngOnDestroy(): void {
    this.destroyAllPickers();
  }

  // This will be called when checkbox changes
  onCheckboxChange(): void {
    this.cdr.detectChanges(); // Force change detection
    setTimeout(() => this.initAllDatePickers(), 0); // Wait for DOM update
  }

  private initAllDatePickers(): void {
    this.destroyAllPickers();

    if (this.isIntervalMode) {
      if (this.intervalDateInput?.nativeElement) {
        this.intervalPicker = flatpickr(this.intervalDateInput.nativeElement, {
          mode: 'range',
          dateFormat: 'Y-m-d',
          minDate: 'today',
        });
      }
    } else if (this.singleDateInput?.nativeElement) {
      this.singlePicker = flatpickr(this.singleDateInput.nativeElement, {
        dateFormat: 'Y-m-d',
        minDate: 'today',
      });
    }
  }

  private destroyAllPickers(): void {
    if (this.intervalPicker) {
      this.intervalPicker.destroy();
      this.intervalPicker = undefined;
    }
    if (this.singlePicker) {
      this.singlePicker.destroy();
      this.singlePicker = undefined;
    }
  }

  openModal(comp: Comparison) {
    this.selectedCompareison = comp;
    this.isModalOpen = true;
  }

  closeModal() {
    this.selectedCompareison = null;
    this.isModalOpen = false;
  }
  onKeyMenuItem(event: KeyboardEvent): void {
    if (event.key === 'Enter' || event.key === ' ') {
      event.preventDefault();
    }
  }
}
