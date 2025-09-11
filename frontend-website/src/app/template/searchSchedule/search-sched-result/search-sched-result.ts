import {
  AfterViewInit,
  ChangeDetectorRef,
  Component,
  ElementRef,
  OnDestroy,
  ViewChild,
} from "@angular/core";
import { RouterLink } from "@angular/router";
import { Faq } from "../../home/faq/faq";
import flatpickr from "flatpickr";
import { CommonModule } from "@angular/common";
import { DetailSchedResult } from "../detail-sched-result/detail-sched-result";
import { TranslateModule, TranslateService } from "@ngx-translate/core";
import { Comparison } from "../../home/modules/comparison";
import { HeroSerice } from "../../home/services/hero-serice";

@Component({
  selector: "app-search-sched-result",
  standalone: true,
  imports: [RouterLink, Faq, CommonModule, DetailSchedResult, TranslateModule],
  templateUrl: "./search-sched-result.html",
  styleUrl: "./search-sched-result.css",
})
export class SearchSchedResult implements AfterViewInit, OnDestroy {
  comparisons: Comparison[] = []; 
  currentPage = 1;
  pageSize = 10; // 10 items per page
  totalRecords = 0;
  filters: any = {}; // Use this to store search/filter values
  constructor(
    private readonly cdr: ChangeDetectorRef,
    private readonly translate: TranslateService,
    private readonly heroSerice: HeroSerice
  ) {}

  ngOnInit(): void {
    this.loadPage(this.currentPage);
  }

  loadPage(page: number) {
    const dtParams = {
      start: (page - 1) * this.pageSize,
      length: this.pageSize,
      draw: 1,
      order: [{ column: 0, dir: 'asc' }],
      columns: [{ data: 'portFromName' }] // default sort column
    };

    this.heroSerice.getPaginatedData(dtParams, this.filters).subscribe(res => {
      this.comparisons = res.data;
      this.totalRecords = res.recordsTotal;
      this.currentPage = page;
    });
  }

  nextPage() {
    if (this.currentPage * this.pageSize < this.totalRecords) {
      this.loadPage(this.currentPage + 1);
    }
  }

  previousPage() {
    if (this.currentPage > 1) {
      this.loadPage(this.currentPage - 1);
    }
  }

  //******************************************************************************************** */
  //************************************ * this code for design **********************************/
  //******************************************************************************************** */
  @ViewChild("singleDateInput") singleDateInput?: ElementRef<HTMLInputElement>;
  private singlePicker: flatpickr.Instance | null = null;
  isInsuranceEnabled: boolean = false;
  isDangerousEnabled: boolean = false;
  isContentVisible = false;
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
        dateFormat: "Y-m-d",
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
    if (event.key === "Enter" || event.key === " ") {
      event.preventDefault();
    }
  }
}
