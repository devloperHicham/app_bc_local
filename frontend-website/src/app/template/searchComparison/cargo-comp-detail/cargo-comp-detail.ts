import { CommonModule } from "@angular/common";
import {
  ChangeDetectorRef,
  Component,
  HostListener,
  inject,
  OnInit,
} from "@angular/core";
import {
  FormBuilder,
  FormGroup,
  FormsModule,
  Validators,
} from "@angular/forms";
import { Router, RouterLink } from "@angular/router";
import { TranslateModule, TranslateService } from "@ngx-translate/core";
import { Comparison } from "../../home/modules/comparison";
import { HeroService } from "../../home/services/hero-service";
import { ConfigService } from "../../../services/config/config";
import { NgxSpinnerService } from "ngx-spinner";
import { ApiResponses } from "../../../modules/api-responses";
import { SharedModule } from "../../../share/share-module";
import { Commodity } from "../../../modules/data-json";

@Component({
  selector: "app-cargo-comp-detail",
  standalone: true,
  imports: [
    SharedModule,
    RouterLink,
    CommonModule,
    FormsModule,
    TranslateModule,
  ],
  templateUrl: "./cargo-comp-detail.html",
  styleUrl: "./cargo-comp-detail.css",
})
export class CargoCompDetail implements OnInit {
  private readonly heroService = inject(HeroService);
  private readonly configService = inject(ConfigService);
  private readonly spinner = inject(NgxSpinnerService);
  private readonly router = inject(Router);
  private readonly fb = inject(FormBuilder);
  form!: FormGroup;
  submitted = false;
  comparisons: Comparison | null = null;
  commodities: Commodity[] = [];

  constructor(
    private readonly cdr: ChangeDetectorRef,
    private readonly translate: TranslateService
  ) {}

  ngOnInit(): void {
    // Load containers data on initialization
    this.loadCommodities();
    this.comparisons = this.heroService.getComparison();

    // ✅ initialize form and include comparison ID or code
    this.form = this.fb.group({
      commodityCode: ["", Validators.required],
      weight: ["", Validators.required],
      weightType: ["", Validators.required],
      infoDetail: [""],
      insurance: [false],
      customsClearance: [false],
      certification: [false],
      inspectionService: [false],
      comparisonId: [this.comparisons?.id || null], // ✅ send ID
      codeTransation: [this.comparisons?.codeTransation || null], // optional
    });
  }

  loadCommodities(): void {
    this.heroService.getCommodities().subscribe({
      next: (data) => {
        // Combine code + name for display
        this.commodities = data.map((item) => ({
          ...item,
          displayName: `${item.CODE} — ${item.COMMODITYNAME}`,
        }));
      },
      error: (err) => console.error("Error loading commodities", err),
    });
  }
  submit(): void {
    this.submitted = true;
    if (this.form.invalid) {
      this.configService.showErrorAlert(
        "Veuillez corriger les erreurs dans le formulaire."
      );
      return;
    }

    this.spinner.show();

    // ✅ Send form + comparison reference
    this.heroService.create(this.form.value).subscribe({
      next: (res: ApiResponses<void>) => {
        this.spinner.hide();
        if (res.isSuccess) {
          this.configService.showSuccessAlert("Successfully registered.");
          this.router.navigateByUrl(
            this.configService.ENDPOINTS.whishlistBooking
          );
        } else {
          this.configService.showErrorAlert("Une erreur s’est produite.");
        }
      },
      error: () => {
        this.spinner.hide();
        this.configService.showErrorAlert(
          "Une erreur est survenue lors de l'enregistrement."
        );
      },
    });
  }

  /*******************************************************************************************/
  /*************************************** this for design web *******************************/
  /*******************************************************************************************/
  popoversVisible: { [key: number]: boolean } = {};
  togglePopover(event: Event, index: number) {
    event.stopPropagation();
    this.popoversVisible[index] = !this.popoversVisible[index];
  }

  @HostListener("document:click", ["$event"])
  onDocumentClick(event: Event) {
    const containers = document.querySelectorAll(".popup-container");
    let clickedInside = false;

    containers.forEach((container, index) => {
      if (
        (event.target as HTMLElement).closest(".popup-container") === container
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
    if (event.key === "Enter" || event.key === " ") {
      event.preventDefault();
    }
  }
}
