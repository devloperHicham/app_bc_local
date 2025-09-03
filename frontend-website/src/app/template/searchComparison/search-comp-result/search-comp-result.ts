import { CommonModule } from '@angular/common';
import {
  AfterViewInit,
  Component,
  ElementRef,
  ViewChild,
  ChangeDetectorRef,
  OnDestroy,
} from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import flatpickr from 'flatpickr';
import 'flatpickr/dist/flatpickr.min.css';
import { SearchCompDetail } from '../search-comp-detail/search-comp-detail';
import { TranslateModule, TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-search-comp-result',
  standalone: true,
  imports: [
    RouterLink,
    CommonModule,
    FormsModule,
    SearchCompDetail,
    TranslateModule,
  ],
  templateUrl: './search-comp-result.html',
  styleUrls: ['./search-comp-result.css'],
})
export class SearchCompResult implements AfterViewInit, OnDestroy {
  isModalOpen: boolean = false;
  isIntervalMode = false;

  @ViewChild('intervalDateInput', { static: false })
  intervalDateInput?: ElementRef;
  @ViewChild('singleDateInput', { static: false }) singleDateInput?: ElementRef;

  private intervalPicker?: flatpickr.Instance;
  private singlePicker?: flatpickr.Instance;

  constructor(
    private readonly cdr: ChangeDetectorRef,
    private readonly translate: TranslateService
  ) {}

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

  openModal() {
    this.isModalOpen = true;
  }

  closeModal() {
    this.isModalOpen = false;
  }
}
