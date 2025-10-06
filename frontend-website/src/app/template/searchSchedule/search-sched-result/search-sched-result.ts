import {
  AfterViewInit,
  ChangeDetectorRef,
  Component,
  ElementRef,
  inject,
  OnDestroy,
  OnInit,
  ViewChild,
} from '@angular/core';
import { RouterLink } from '@angular/router';
import { Faq } from '../../home/faq/faq';
import flatpickr from 'flatpickr';
import { CommonModule } from '@angular/common';
import { DetailSchedResult } from '../detail-sched-result/detail-sched-result';
import { TranslateModule, TranslateService } from '@ngx-translate/core';
import { Schedule } from '../../home/modules/schedule';
import { HeroService } from '../../home/services/hero-service';
import { ConfigService } from '../../../services/config/config';
import { NgxSpinnerService } from 'ngx-spinner';
import { SharedModule } from '../../../share/share-module';

@Component({
  selector: 'app-search-sched-result',
  standalone: true,
  imports: [SharedModule, RouterLink, Faq, CommonModule, DetailSchedResult, TranslateModule],
  templateUrl: './search-sched-result.html',
  styleUrl: './search-sched-result.css',
})
export class SearchSchedResult implements AfterViewInit, OnDestroy, OnInit {
  selectedSchedule: Schedule | null = null;
  expandedScheduleId: string | null = null;
  private readonly heroService = inject(HeroService);
  private readonly configService = inject(ConfigService);
  Math = Math;
  schedules: Schedule[] = [];
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

    this.spinner.show();
    this.filters = this.heroService.getForm() ?? {};
    this.loadPage(this.currentPage);
  }

  loadPage(page: number): void {
    // 👇 build dtParams manually from page + pageSize
    const dtParams = {
      start: (page - 1) * this.pageSize,
      length: this.pageSize,
      draw: page, // can use page number as draw
      order: [{ column: 0, dir: 'asc' }],
      columns: [{ data: 'dateDepart' }], // default sort column
    };
    const filters = {
      selectedPortFromSchedule: this.filters?.selectedPortFromSchedule ?? null,
      selectedPortToSchedule: this.filters?.selectedPortToSchedule ?? null,
      selectedCompany: this.filters?.selectedCompany ?? null,
      searchOn: this.filters?.searchOn ?? null,
      startDateSchedule: this.filters?.startDateSchedule ?? null,
      endDateSchedule: this.filters?.endDateSchedule ?? null,
      weeksAhead: this.filters?.weeksAhead ?? null
    };

    this.spinner.show();
    this.heroService.getPaginatedDataSchedule(dtParams, filters).subscribe({
      next: (res) => {
        this.schedules = res.data || [];
        this.totalRecords = res.recordsTotal || 0;

        const totalPages = Math.ceil(this.totalRecords / this.pageSize);
        this.totalPages = Array.from({ length: totalPages }, (_, i) => i + 1);

        this.currentPage = page;
        this.spinner.hide();
        this.cdr.detectChanges();
      },
      error: (error) => {
        this.schedules = [];
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

  togglePast(id: string): void {
    this.heroService.togglePastSchedule(id).subscribe({
      next: (response) =>
        this.configService.showSuccessAlert('Successfully registered.'),
      error: () => console.log('error past schedule'),
    });
  }

  toggleFavorite(id: string): void {
    this.heroService.toggleFavoriteSchedule(id).subscribe({
      next: (response) =>
        this.configService.showSuccessAlert('Successfully registered.'),
      error: () => console.log('error favorite schedule'),
    });
  }

  //******************************************************************************************** */
  //************************************ * this code for design **********************************/
  //******************************************************************************************** */
  @ViewChild('singleDateInput') singleDateInput?: ElementRef<HTMLInputElement>;
  private singlePicker: flatpickr.Instance | null = null;
  isInsuranceEnabled: boolean = false;
  isDangerousEnabled: boolean = false;
  isContentVisible = false;
  toggleContent(cmp: Schedule): void {
    // If clicking same card, close it
    if (this.expandedScheduleId === cmp.id) {
      this.expandedScheduleId = null;
      this.selectedSchedule = null;
    } else {
      // Show this card's detail
      this.expandedScheduleId = cmp.id;
      this.selectedSchedule = cmp;
    }
  }

  hideDetail(): void {
    this.expandedScheduleId = null;
    this.selectedSchedule = null;
  }

  trackById(index: number, sched: Schedule) {
    return sched.id;
  }

  ngAfterViewInit(): void {
    this.initPicker();
  }

  ngOnDestroy(): void {
    this.destroyPicker();
  }

  private initPicker(): void {
    if (this.singleDateInput?.nativeElement && !this.singlePicker) {
      this.singlePicker = flatpickr(this.singleDateInput.nativeElement, {
        dateFormat: 'Y-m-d',
        allowInput: true,
        clickOpens: true,
      });
    }
  }

  private destroyPicker(): void {
    if (this.singlePicker) {
      this.singlePicker.destroy();
      this.singlePicker = null;
    }
  }

  onKeyMenuItem(event: KeyboardEvent): void {
    if (event.key === 'Enter' || event.key === ' ') {
      event.preventDefault();
    }
  }
}
