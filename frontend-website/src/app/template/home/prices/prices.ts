import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { TranslateModule, TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-prices',
  standalone: true,
  imports: [CommonModule, RouterLink, TranslateModule],
  templateUrl: './prices.html',
  styleUrl: './prices.css'
})
export class Prices {
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
