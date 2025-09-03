import { ChangeDetectorRef, Component } from '@angular/core';
import { TranslateModule, TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-payment-conditions',
  standalone: true,
  imports: [TranslateModule],
  templateUrl: './payment-conditions.html',
  styleUrl: './payment-conditions.css'
})
export class PaymentConditions {
  constructor(private readonly cdr: ChangeDetectorRef, private readonly translate :TranslateService) {}
}
