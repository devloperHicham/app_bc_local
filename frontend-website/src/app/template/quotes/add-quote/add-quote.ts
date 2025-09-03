import { CommonModule } from '@angular/common';
import {
  Component,
  ElementRef,
  EventEmitter,
  Output,
  ViewChild,
  OnDestroy,
  AfterViewInit,
  ChangeDetectorRef,
} from '@angular/core';
import { FormsModule } from '@angular/forms';
import { TranslateModule, TranslateService } from '@ngx-translate/core';
import flatpickr from 'flatpickr';
// Make sure to import the flatpickr CSS
import 'flatpickr/dist/flatpickr.min.css';

@Component({
  selector: 'app-add-quote',
  standalone: true,
  imports: [CommonModule, FormsModule, TranslateModule],
  templateUrl: './add-quote.html',
  styleUrls: ['./add-quote.css'],
})
export class AddQuote implements AfterViewInit, OnDestroy {
  @Output() closeEvent = new EventEmitter<void>();
  @ViewChild('singleDateInput') singleDateInput?: ElementRef<HTMLInputElement>;
  private singlePicker: flatpickr.Instance | null = null;
  isInsuranceEnabled: boolean = false;
  isDangerousEnabled: boolean = false;

  constructor(
    private readonly cdr: ChangeDetectorRef,
    private readonly translate: TranslateService
  ) {}

  close() {
    this.closeEvent.emit();
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
