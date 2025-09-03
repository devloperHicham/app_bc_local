import { ChangeDetectorRef, Component } from '@angular/core';
import { MapsAboutUs } from '../maps-about-us/maps-about-us';
import { HeroAboutUs } from '../hero-about-us/hero-about-us';
import { News } from '../../home/news/news';
import { Faq } from '../../home/faq/faq';
import { TranslateModule, TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-about-us',
  standalone: true,
  imports: [MapsAboutUs, HeroAboutUs, News, Faq, TranslateModule],
  templateUrl: './about-us.html',
  styleUrl: './about-us.css'
})
export class AboutUs {
    constructor(private readonly cdr: ChangeDetectorRef, private readonly translate :TranslateService) {}

}
