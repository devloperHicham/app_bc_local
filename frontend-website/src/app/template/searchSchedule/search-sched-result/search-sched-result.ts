import {
  AfterViewInit,
  ChangeDetectorRef,
  Component,
  ElementRef,
  OnDestroy,
  ViewChild,
} from '@angular/core';
import { RouterLink } from '@angular/router';
import { Faq } from '../../home/faq/faq';
import flatpickr from 'flatpickr';
import { CommonModule } from '@angular/common';
import { DetailSchedResult } from '../detail-sched-result/detail-sched-result';
import { TranslateModule, TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-search-sched-result',
  standalone: true,
  imports: [RouterLink, Faq, CommonModule, DetailSchedResult, TranslateModule],
  templateUrl: './search-sched-result.html',
  styleUrl: './search-sched-result.css',
})
export class SearchSchedResult implements AfterViewInit, OnDestroy {
  @ViewChild('singleDateInput') singleDateInput?: ElementRef<HTMLInputElement>;
  private singlePicker: flatpickr.Instance | null = null;
  isInsuranceEnabled: boolean = false;
  isDangerousEnabled: boolean = false;
  isContentVisible = false;

  constructor(
    private readonly cdr: ChangeDetectorRef,
    private readonly translate: TranslateService
  ) {}

  toggleContent(): void {
    this.isContentVisible = !this.isContentVisible;
  }

  hideDetail() {
    this.isContentVisible = false;
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
