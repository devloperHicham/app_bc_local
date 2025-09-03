import { ChangeDetectorRef, Component } from '@angular/core';
import { Faq } from '../../home/faq/faq';
import { TranslateModule, TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-contact',
  standalone: true,
  imports: [Faq, TranslateModule],
  templateUrl: './contact.html',
  styleUrl: './contact.css'
})
export class Contact {
  constructor(private readonly cdr: ChangeDetectorRef, private readonly translate :TranslateService) {}

}
