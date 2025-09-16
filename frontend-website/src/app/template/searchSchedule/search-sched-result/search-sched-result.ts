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

@Component({
  selector: 'app-search-sched-result',
  standalone: true,
  imports: [RouterLink, Faq, CommonModule, DetailSchedResult, TranslateModule],
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
  pageSize = 5;
  filters: any = {};

  constructor(
    private readonly cdr: ChangeDetectorRef,
    private readonly translate: TranslateService
  ) {}

  ngOnInit(): void {
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
      portFromCode: this.filters.portFromCode ?? null,
      portToCode: this.filters.portToCode ?? null,
      dateDepart: this.filters.dateDepart ?? null,
      dateArrive: this.filters.dateArrive ?? null,
      companyId: this.filters.companyId ?? null,
    };

    this.heroService.getPaginatedDataSchedule(dtParams, filters).subscribe({
      next: (res) => {
        this.schedules = res.data;
        console.log(this.schedules);
        // calculate total pages
        const pageCount = Math.ceil(res.recordsTotal / this.pageSize);
        this.totalPages = Array.from({ length: pageCount }, (_, i) => i + 1);
        this.currentPage = page;
      },
      error: () => {
        console.log('error to get data');
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
