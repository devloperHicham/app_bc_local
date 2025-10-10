import { CommonModule } from "@angular/common";
import { ChangeDetectorRef, Component, inject, OnInit } from "@angular/core";
import { TranslateModule, TranslateService } from "@ngx-translate/core";
import { Comparison } from "../../home/modules/comparison";
import { HeroService } from "../../home/services/hero-service";
import { AuthService } from "../../../auth/services/auth.service";
import { FormBuilder, FormGroup } from "@angular/forms";
import { SharedModule } from "../../../share/share-module";
import { RouterLink } from "@angular/router";
import { async, take } from "rxjs";

@Component({
  selector: "app-trip-comp-detail",
  standalone: true,
  imports: [SharedModule, CommonModule, TranslateModule, RouterLink],
  templateUrl: "./trip-comp-detail.html",
  styleUrl: "./trip-comp-detail.css",
})
export class TripCompDetail implements OnInit {
  private readonly heroService = inject(HeroService);
  private readonly authService = inject(AuthService);
  private readonly fb = inject(FormBuilder);
  form!: FormGroup;
  comparisons: Comparison | null = null;
  isTermsOpen: boolean = false;
  isContactsOpen: boolean = false;

  constructor(
    private readonly cdr: ChangeDetectorRef,
    private readonly translate: TranslateService
  ) {}

  ngOnInit(): void {
    this.comparisons = this.heroService.getComparison();
    this.initializeForm;
  }

  get currentUser$() {
    return this.authService.currentUser$;
  }
  initializeForm(): void {
    // 1️⃣ Create the empty form first
    this.form = this.fb.group({
      firstName: [""],
      lastName: [""],
      email: [""],
      companyName: [""],
    });
    // 2️⃣ Subscribe to user data
  this.currentUser$.pipe(take(1)).subscribe((user) => {
    if (user) {
      this.form.patchValue({
        firstName: user.firstName,
        lastName: user.lastName,
        email: user.email,
        companyName: user.companyName,
      });
    }
  });
  }

  calculateDaysBetween(startDateStr: string, endDateStr: string): number {
    // Convert DD/MM/YYYY to Date objects
    const startParts = startDateStr.split("/");
    const endParts = endDateStr.split("/");

    const startDate = new Date(
      parseInt(startParts[2]), // year
      parseInt(startParts[1]) - 1, // month (0-indexed)
      parseInt(startParts[0]) // day
    );

    const endDate = new Date(
      parseInt(endParts[2]), // year
      parseInt(endParts[1]) - 1, // month (0-indexed)
      parseInt(endParts[0]) // day
    );

    // Calculate difference in milliseconds and convert to days
    const timeDiff = endDate.getTime() - startDate.getTime();
    const daysDiff = Math.ceil(timeDiff / (1000 * 3600 * 24));

    return daysDiff;
  }

  /*************************************************************************** */
  /**************************this is for design ***************************** */
  /*************************************************************************** */
  toggleSection(section: "terms" | "contacts") {
    if (section === "terms") {
      this.isTermsOpen = !this.isTermsOpen;
    } else if (section === "contacts") {
      this.isContactsOpen = !this.isContactsOpen;
    }
  }

  handleKeyDown(event: KeyboardEvent, section: "terms" | "contacts") {
    if (event.key === "Enter" || event.key === " ") {
      this.toggleSection(section);
      event.preventDefault(); // prevent scrolling on space
    }
  }
}
