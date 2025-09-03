import { Injectable, signal } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class MarketplaceService {
  showLayout = signal(true); // this is for display marketplace with full page
}
