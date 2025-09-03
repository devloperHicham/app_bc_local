import { ChangeDetectorRef, Component } from '@angular/core';
import { Faq } from '../../home/faq/faq';
import { TranslateModule, TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-whishlist-booking',
  standalone: true,
  imports: [Faq, TranslateModule],
  templateUrl: './whishlist-booking.html',
  styleUrl: './whishlist-booking.css'
})
export class WhishlistBooking {
  constructor(private readonly cdr: ChangeDetectorRef, private readonly translate :TranslateService) {}
}
