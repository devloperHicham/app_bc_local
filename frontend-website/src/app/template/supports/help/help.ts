import { ChangeDetectorRef, Component } from '@angular/core';
import { Faq } from '../../home/faq/faq';
import { TranslateModule, TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-help',
  standalone: true,
  imports: [Faq, TranslateModule],
  templateUrl: './help.html',
  styleUrl: './help.css'
})
export class Help {
  constructor(private readonly cdr: ChangeDetectorRef, private readonly translate :TranslateService) {}
}
