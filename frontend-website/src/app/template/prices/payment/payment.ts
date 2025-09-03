import { ChangeDetectorRef, Component } from '@angular/core';
import { TranslateModule, TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-payment',
  standalone: true,
  imports: [TranslateModule],
  templateUrl: './payment.html',
  styleUrl: './payment.css'
})
export class Payment {
  constructor(private readonly cdr: ChangeDetectorRef, private readonly translate :TranslateService) {}
}
