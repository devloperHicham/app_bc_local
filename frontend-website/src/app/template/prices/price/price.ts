import { ChangeDetectorRef, Component } from '@angular/core';
import { NoPrice } from '../no-price/no-price';
import { CommonModule } from '@angular/common';
import { DetailPriceFree } from '../detail-price-free/detail-price-free';
import { DetailPriceBusniss } from '../detail-price-busniss/detail-price-busniss';
import { RouterLink } from '@angular/router';
import { PriceFaq } from '../price-faq/price-faq';
import { TranslateModule, TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-price',
  standalone: true,
  imports: [DetailPriceFree, DetailPriceBusniss, NoPrice, CommonModule, RouterLink, PriceFaq, TranslateModule],
  templateUrl: './price.html',
  styleUrl: './price.css',
})
export class Price {
  activeTab: 'free' | 'business' = 'free'; // default
  currentBg: string = '#FAFBFD'; // Default background
  
  constructor(private readonly cdr: ChangeDetectorRef, private readonly translate :TranslateService) {}

  selectTab(tab: 'free' | 'business') {
    this.activeTab = tab;

    if (tab === 'free') {
      this.currentBg = '#FAFBFD';
    } else if (tab === 'business') {
      this.currentBg = '#fff9f5';
    }
  }
}
