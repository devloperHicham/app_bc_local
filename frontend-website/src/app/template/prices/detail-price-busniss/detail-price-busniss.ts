import { ChangeDetectorRef, Component } from '@angular/core';
import { TranslateModule, TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-detail-price-busniss',
  standalone: true,
  imports: [TranslateModule],
  templateUrl: './detail-price-busniss.html',
  styleUrl: './detail-price-busniss.css'
})
export class DetailPriceBusniss {
  constructor(private readonly cdr: ChangeDetectorRef, private readonly translate :TranslateService) {}
}
