import { ChangeDetectorRef, Component } from '@angular/core';
import { TranslateModule, TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-price-faq',
  standalone: true,
  imports: [TranslateModule],
  templateUrl: './price-faq.html',
  styleUrl: './price-faq.css'
})
export class PriceFaq {
  constructor(private readonly cdr: ChangeDetectorRef, private readonly translate :TranslateService) {}
}
