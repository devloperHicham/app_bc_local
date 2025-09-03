import { ChangeDetectorRef, Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { TranslateModule, TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-faq',
  standalone: true,
  imports: [RouterLink, TranslateModule],
  templateUrl: './faq.html',
  styleUrl: './faq.css'
})
export class Faq {
  constructor(private readonly cdr: ChangeDetectorRef, private readonly translate :TranslateService) {}

}
