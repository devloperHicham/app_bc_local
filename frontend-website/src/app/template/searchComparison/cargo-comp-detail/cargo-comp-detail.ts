import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, HostListener, inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, Validators } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { TranslateModule, TranslateService } from '@ngx-translate/core';
import { Comparison } from '../../home/modules/comparison';
import { HeroService } from '../../home/services/hero-service';

@Component({
  selector: 'app-cargo-comp-detail',
  standalone: true,
  imports: [RouterLink, CommonModule, FormsModule, TranslateModule],
  templateUrl: './cargo-comp-detail.html',
  styleUrl: './cargo-comp-detail.css',
})
export class CargoCompDetail implements OnInit {
  private readonly heroService = inject(HeroService);
  form!: FormGroup;
  comparisons: Comparison | null = null;


  constructor(
    private readonly fb: FormBuilder,
    private readonly cdr: ChangeDetectorRef,
    private readonly translate: TranslateService
  ) {}

  ngOnInit(): void {
    this.comparisons = this.heroService.getComparison();
     this.form = this.fb.group({
      productName: ["", [Validators.required]],
      weight: ["", [Validators.required]],
      infoDetail: [""],
      insurance: [""],
      customsDlearance: [""],
      certification: [""],
      inspectionService: [""],
    });
  }

  /*************************************this for design web *******************/
  popoversVisible: { [key: number]: boolean } = {};
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
