import { AfterViewInit, Component, inject } from '@angular/core';
import { Hero } from '../hero/hero';
import { Offers } from '../offers/offers';
import { Marketplaces } from '../marketplaces/marketplaces';
import { WorkUs } from '../work-us/work-us';
import { ChooseUs } from '../choose-us/choose-us';
import { Prices } from '../prices/prices';
import { News } from '../news/news';
import { Faq } from '../faq/faq';
import { MarketplaceService } from '../../../services/home/marketplace-service';
@Component({
  selector: 'app-home',
  standalone: true,
  imports: [Hero, Offers, Marketplaces, WorkUs, ChooseUs, Prices, News, Faq],
  templateUrl: './home.html',
  styleUrls: ['./home.css'],
})
export class Home implements AfterViewInit {
  private lastVisible = false;
  private readonly layoutService = inject(MarketplaceService);
  // ------- start  this for display app-marketplaces with full page  ---//
  ngAfterViewInit(): void {
    const target = document.querySelector('app-marketplaces');

    if (!target) return;

    const observer = new IntersectionObserver(
      (entries) => {
        entries.forEach((entry) => {
          if (entry.isIntersecting && !this.lastVisible) {
            this.lastVisible = true;
            this.layoutService.showLayout.set(false);
          } else if (!entry.isIntersecting && this.lastVisible) {
            this.lastVisible = false;
            this.layoutService.showLayout.set(true);
          }
        });
      },
      {
        threshold: 0.6,
      }
    );

    observer.observe(target);
  }

  // ------- end app-marketplaces with full page  ---//
}
