import { ChangeDetectorRef, Component } from '@angular/core';
import { DetailQuote } from '../detail-quote/detail-quote';
import { CommonModule } from '@angular/common';
import { TranslateModule, TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-list-quote',
  standalone: true,
  imports: [DetailQuote, CommonModule, TranslateModule],
  templateUrl: './list-quote.html',
  styleUrl: './list-quote.css',
})
export class ListQuote {
  isModalOpen: boolean = false;

  constructor(
    private readonly cdr: ChangeDetectorRef,
    private readonly translate: TranslateService
  ) {}

  openModal() {
    this.isModalOpen = true;
  }

  closeModal() {
    this.isModalOpen = false;
  }
}
