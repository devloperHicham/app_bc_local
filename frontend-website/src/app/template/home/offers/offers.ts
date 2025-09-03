import { ChangeDetectorRef, Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { SearchCompDetail } from '../../searchComparison/search-comp-detail/search-comp-detail';
import { CommonModule } from '@angular/common';
import { TranslateModule, TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-offers',
  standalone: true,
  imports: [SearchCompDetail, CommonModule, RouterLink, TranslateModule],
  templateUrl: './offers.html',
  styleUrl: './offers.css',
})
export class Offers {
  isModalOpen: boolean = false;

  constructor(private readonly cdr: ChangeDetectorRef, private readonly translate :TranslateService) {}
  openModal() {
    this.isModalOpen = true;
  }

  closeModal() {
    this.isModalOpen = false;
  }
}
