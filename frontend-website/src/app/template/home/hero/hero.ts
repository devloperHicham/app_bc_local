import { CommonModule } from '@angular/common';
import {
  AfterViewInit,
  AfterViewChecked,
  ChangeDetectorRef,
  Component,
  ElementRef,
  ViewChild,
  OnInit,
} from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { TranslateModule, TranslateService } from '@ngx-translate/core';
import flatpickr from 'flatpickr';
import 'flatpickr/dist/flatpickr.min.css';
import { Port } from '../modules/port';
import { HeroSerice } from '../services/hero-serice';

@Component({
  selector: 'app-hero',
  standalone: true,
  imports: [RouterLink, CommonModule, FormsModule, TranslateModule],
  templateUrl: './hero.html',
  styleUrls: ['./hero.css'],
})
export class Hero implements AfterViewInit, AfterViewChecked, OnInit {
  activeTab: 'marine' | 'schedules' = 'marine';
  currentBg: string = '/assets/images/marine-bg.png';
  isPopoverVisible = false;
  isIntervalMode = false;
  ports: Port[] = [];

  @ViewChild('intervalDateInput', { static: false })
  intervalDateInput?: ElementRef;

  @ViewChild('singleDateInput', { static: false })
  singleDateInput?: ElementRef;

  @ViewChild('singleDateScheduleInput', { static: false })
  singleDateScheduleInput?: ElementRef;

  private intervalPicker?: flatpickr.Instance;
  private singlePicker?: flatpickr.Instance;
  private schedulePicker?: flatpickr.Instance;

  private initialized = false;

  constructor(
    private readonly heroSerice: HeroSerice,
    private readonly cdr: ChangeDetectorRef,
    private readonly translate: TranslateService
  ) {}

  ngOnInit(): void {
    this.heroSerice.getPorts().subscribe((data) => {
      this.ports = data;
    });
  }

  //******************************************************************************************** */
  //************************************ * this code for design **********************************/
  //******************************************************************************************** */
  ngAfterViewInit(): void {
    this.initAllDatePickers();
    this.initialized = true;
  }

  ngAfterViewChecked(): void {
    if (!this.initialized && this.inputsAreAvailable()) {
      this.initAllDatePickers();
      this.initialized = true;
    }
  }

  selectTab(tab: 'marine' | 'schedules') {
    this.activeTab = tab;
    this.currentBg =
      tab === 'marine'
        ? '/assets/images/marine-bg.png'
        : '/assets/images/schedules-bg.png';

    this.initialized = false; // force re-init
    this.cdr.detectChanges(); // ensure DOM is updated
  }

  toggleDateMode(): void {
    this.isIntervalMode = !this.isIntervalMode;
    this.initialized = false; // force re-init
    this.cdr.detectChanges(); // allow DOM update
  }

  togglePopover(event: Event) {
    event.stopPropagation();
    this.isPopoverVisible = !this.isPopoverVisible;
  }

  onKeyMenuItem(event: KeyboardEvent): void {
    if (event.key === 'Enter' || event.key === ' ') {
      event.preventDefault();
    }
  }

  private inputsAreAvailable(): boolean {
    return (
      (!!this.intervalDateInput?.nativeElement && this.isIntervalMode) ||
      (!!this.singleDateInput?.nativeElement && !this.isIntervalMode) ||
      !!this.singleDateScheduleInput?.nativeElement
    );
  }

  private initAllDatePickers(): void {
    this.destroyAllPickers();

    // Always initialize the schedule picker if visible
    if (this.singleDateScheduleInput?.nativeElement) {
      this.schedulePicker = flatpickr(
        this.singleDateScheduleInput.nativeElement,
        {
          dateFormat: 'Y-m-d',
          minDate: 'today',
        }
      );
    }

    // Initialize marine mode-specific picker
    if (this.isIntervalMode) {
      if (this.intervalDateInput?.nativeElement) {
        this.intervalPicker = flatpickr(this.intervalDateInput.nativeElement, {
          mode: 'range',
          dateFormat: 'Y-m-d',
        });
      }
    } else if (this.singleDateInput?.nativeElement) {
      this.singlePicker = flatpickr(this.singleDateInput.nativeElement, {
        dateFormat: 'Y-m-d',
      });
    }
  }

  private destroyAllPickers(): void {
    this.intervalPicker?.destroy();
    this.singlePicker?.destroy();

    this.intervalPicker = undefined;
    this.singlePicker = undefined;
  }
}
