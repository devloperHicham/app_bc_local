import { Component, signal, computed, inject } from '@angular/core';
import { Router, NavigationEnd, RouterOutlet } from '@angular/router';
import { filter } from 'rxjs';
import { Nav } from './template/nav/nav';
import { Footer } from './template/footer/footer';
import { CommonModule } from '@angular/common';
import { MarketplaceService } from './services/home/marketplace-service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, Nav, Footer],
  templateUrl: './app.html',
  styleUrls: ['./app.css'],
})
export class App {
  private readonly router = inject(Router);
  private readonly layoutService = inject(MarketplaceService);

  protected currentUrl = signal('');

  //Only route-based full page layout
  protected isFullPage = computed(() => {
    return (
      this.currentUrl().startsWith('/login') ||
      this.currentUrl().startsWith('/inscription-users') ||
      this.currentUrl().startsWith('/payment-orders')
    );
  });

  // Used directly for scroll and show marketplaces with full page
  protected showLayout = this.layoutService.showLayout;

  constructor() {
    this.router.events
      .pipe(
        filter(
          (event): event is NavigationEnd => event instanceof NavigationEnd
        )
      )
      .subscribe((event) => {
        this.currentUrl.set(event.urlAfterRedirects);
        // Adjust padding only for normal pages
        document.body.style.paddingTop = this.isFullPage()
          ? '0'
          : 'calc(40px + 64px)';
        //this is for if nzvigzte to another page start from top
        setTimeout(() => {
          window.scrollTo({ top: 0, behavior: 'smooth' });
        }, 100);
      });
  }
}
