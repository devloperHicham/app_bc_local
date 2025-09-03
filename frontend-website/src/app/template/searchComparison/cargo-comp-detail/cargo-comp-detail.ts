import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, HostListener } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { TranslateModule, TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-cargo-comp-detail',
  standalone: true,
  imports: [RouterLink, CommonModule, FormsModule, TranslateModule],
  templateUrl: './cargo-comp-detail.html',
  styleUrl: './cargo-comp-detail.css',
})
export class CargoCompDetail {
  popoversVisible: { [key: number]: boolean } = {};

  constructor(
    private readonly cdr: ChangeDetectorRef,
    private readonly translate: TranslateService
  ) {}

  togglePopover(event: Event, index: number) {
    event.stopPropagation();
    this.popoversVisible[index] = !this.popoversVisible[index];
  }

  @HostListener('document:click', ['$event'])
  onDocumentClick(event: Event) {
    const containers = document.querySelectorAll('.popup-container');
    let clickedInside = false;

    containers.forEach((container, index) => {
      if (
        (event.target as HTMLElement).closest('.popup-container') === container
      ) {
        clickedInside = true;
      } else {
        this.popoversVisible[index] = false;
      }
    });

    if (!clickedInside) {
      this.popoversVisible = {};
    }
  }

  onKeyMenuItem(event: KeyboardEvent): void {
    if (event.key === 'Enter' || event.key === ' ') {
      event.preventDefault();
    }
  }
}
