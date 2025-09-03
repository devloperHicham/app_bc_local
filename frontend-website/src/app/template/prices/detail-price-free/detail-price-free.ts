import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component } from '@angular/core';
import { TranslateModule, TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-detail-price-free',
  standalone: true,
  imports: [TranslateModule, CommonModule],
  templateUrl: './detail-price-free.html',
  styleUrl: './detail-price-free.css'
})
export class DetailPriceFree {
  constructor(private readonly cdr: ChangeDetectorRef, private readonly translate :TranslateService) {}
}
