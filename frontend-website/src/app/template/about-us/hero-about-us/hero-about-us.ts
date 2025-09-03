import { ChangeDetectorRef, Component } from '@angular/core';
import { TranslateModule, TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-hero-about-us',
  standalone: true,
  imports: [TranslateModule],
  templateUrl: './hero-about-us.html',
  styleUrl: './hero-about-us.css'
})
export class HeroAboutUs {
    constructor(private readonly cdr: ChangeDetectorRef, private readonly translate :TranslateService) {}
}
