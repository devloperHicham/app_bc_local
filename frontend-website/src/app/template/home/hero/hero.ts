import { CommonModule } from "@angular/common";
import {
  AfterViewInit,
  AfterViewChecked,
  ChangeDetectorRef,
  Component,
  ElementRef,
  ViewChild,
  OnInit,
  inject,
} from "@angular/core";
import {
  FormBuilder,
  FormGroup,
  FormsModule,
  Validators,
} from "@angular/forms";
import { Router } from "@angular/router";
import { TranslateModule, TranslateService } from "@ngx-translate/core";
import flatpickr from "flatpickr";
import "flatpickr/dist/flatpickr.min.css";
import { HeroService } from "../services/hero-service";
import {
  Company,
  Container,
  Port,
  Transportation,
} from "../../../modules/data-json";
import { SharedModule } from "../../../share/share-module";
import { ConfigService } from "../../../services/config/config";
import { NgxSpinnerService } from "ngx-spinner";
import { AuthService } from "../../../auth/services/auth.service";
import { take } from "rxjs";
type PortFilterType =
  | "fromSchedule"
  | "toSchedule"
  | "fromComparison"
  | "toComparison";
@Component({
  selector: "app-hero",
  standalone: true,
  imports: [SharedModule, CommonModule, FormsModule, TranslateModule],
  templateUrl: "./hero.html",
  styleUrls: ["./hero.css"],
})
export class Hero implements AfterViewInit, AfterViewChecked, OnInit {
  form!: FormGroup;
  submitted = false;
  ports: Port[] = [];
  containers: Container[] = [];
  transportations: Transportation[] = [];
  companies: Company[] = [];
  selectedCompany: string | null = null;
  activeTab: "marine" | "schedules" = "marine";
  private readonly authService = inject(AuthService);
  readonly auth$ = this.authService.isAuthenticated$; // Observable for authentication status
  private readonly heroService = inject(HeroService);
  private readonly router = inject(Router);
  private readonly spinner = inject(NgxSpinnerService);
  private readonly configService = inject(ConfigService);
  private readonly fb = inject(FormBuilder);

  selectedPortFromSchedule = "";
  filteredPortsFromSchedule: any[] = [];
  dropdownOpenFromSchedule = false;
  selectedPortFromScheduleCode: string | null = null;

  selectedPortToSchedule = "";
  filteredPortsToSchedule: any[] = [];
  dropdownOpenToSchedule = false;
  selectedPortToScheduleCode: string | null = null;

  selectedPortFromComparison = "";
  filteredPortsFromComparison: any[] = [];
  dropdownOpenFromComparison = false;
  selectedPortFromComparisonCode: string | null = null;

  selectedPortToComparison = "";
  filteredPortsToComparison: any[] = [];
  dropdownOpenToComparison = false;
  selectedPortToComparisonCode: string | null = null;

  constructor(
    private readonly cdr: ChangeDetectorRef,
    private readonly translate: TranslateService
  ) { }

  initializeForm(): void {
    this.form = this.fb.group({
      comparisonSearch: this.fb.group({
        selectedPortFromComparison: ["", Validators.required],
        selectedPortToComparison: ["", Validators.required],
        isIntervalMode: [false],
        startDateComparison: [""],
        endDateComparison: [""],
        selectedTransportation: ["", Validators.required],
        selectedContainer: ["", Validators.required],
      }),

      scheduleSearch: this.fb.group({
        selectedPortFromSchedule: ["", Validators.required],
        selectedPortToSchedule: ["", Validators.required],
        selectedCompany: ["", Validators.required],
        searchOn: ["1", Validators.required], // default value
        startDateSchedule: [""],
        endDateSchedule: [""],
        weeksAhead: ["", Validators.required],
      }),

      // Filters (checkboxes apply to both maybe?)
      isCheapest: [false],
      isFastest: [false],
      isDirect: [false],
    });
  }
  ngOnInit(): void {
    this.initializeForm();
    // Restore saved data
    const savedData = this.heroService.getForm();
    if (savedData) {
      const activeForm =
        this.activeTab === "marine"
          ? this.form.get("scheduleSearch")
          : this.form.get("comparisonSearch");
      activeForm?.patchValue(savedData);
    }
    // Load ports data on initialization
    this.heroService.getPorts().subscribe((data) => {
      this.ports = data;
    });
    // Load containers data on initialization
    this.heroService.getContainers().subscribe((data) => {
      this.containers = data;
    });
    // Load transportations data on initialization
    this.heroService.getTransportations().subscribe((data) => {
      this.transportations = data;
    });
    // Load companies data on initialization
    this.heroService.getCompanies().subscribe((data) => {
      this.companies = data;
    });
  }

  setComparisonDate(input: string, isRange: boolean): void {
    const comparisonGroup = this.form.get("comparisonSearch") as FormGroup;

    if (isRange && input.includes("to")) {
      const [start, end] = input.split("to").map(d => d.trim());
      comparisonGroup.patchValue({
        startDateComparison: this.formatDate(start),
        endDateComparison: this.formatDate(end),
        isIntervalMode: true
      });
    } else {
      comparisonGroup.patchValue({
        startDateComparison: this.formatDate(input),
        endDateComparison: null, // single date mode
        isIntervalMode: false
      });
    }
  }

  setScheduleDate(input: string): void {
    const scheduleGroup = this.form.get("scheduleSearch") as FormGroup;
    const weeksAheadControl = this.form.get("weeksAhead");

    // Convert input to Date for calculations
    const startDate = new Date(input);

    // Extract weeksAhead value safely
    const weeksAhead = Number(weeksAheadControl?.value ?? 0);

    // Compute end date by adding weeks
    const endDate = new Date(startDate);
    endDate.setDate(startDate.getDate() + weeksAhead * 7);

    // Patch formatted string values into form
    scheduleGroup.patchValue({
      startDateSchedule: this.formatDate(startDate.toISOString()), // convert Date → string for your helper
      endDateSchedule: this.formatDate(endDate.toISOString()),
      isIntervalMode: true
    });
  }
  // helper to normalize date format
  private formatDate(dateStr: string): string {
    const date = new Date(dateStr);
    const d = date.getDate().toString().padStart(2, '0');
    const m = (date.getMonth() + 1).toString().padStart(2, '0');
    const y = date.getFullYear();
    return `${d}/${m}/${y}`; // dd/MM/yyyy format
  }

  filterPorts(type: PortFilterType, event: any) {
    const val = event.target.value.toLowerCase().trim();

    if (val.length < 3) {
      if (type === "fromSchedule") {
        this.filteredPortsFromSchedule = [];
        this.dropdownOpenFromSchedule = false;
      } else if (type === "toSchedule") {
        this.filteredPortsToSchedule = [];
        this.dropdownOpenToSchedule = false;
      } else if (type === "fromComparison") {
        this.filteredPortsFromComparison = [];
        this.dropdownOpenFromComparison = false;
      } else {
        this.filteredPortsToComparison = [];
        this.dropdownOpenToComparison = false;
      }
      return;
    }

    const filtered = this.ports.filter(
      (port) =>
        port.PORTNAME.toLowerCase().startsWith(val) || // port name starts with input
        port.CODEPORT.toLowerCase().startsWith(val) // port code starts with input
    );

    if (type === "fromSchedule") {
      this.filteredPortsFromSchedule = filtered;
      this.dropdownOpenFromSchedule = filtered.length > 0;
    } else if (type === "toSchedule") {
      this.filteredPortsToSchedule = filtered;
      this.dropdownOpenToSchedule = filtered.length > 0;
    } else if (type === "fromComparison") {
      this.filteredPortsFromComparison = filtered;
      this.dropdownOpenFromComparison = filtered.length > 0;
    } else {
      this.filteredPortsToComparison = filtered;
      this.dropdownOpenToComparison = filtered.length > 0;
    }
  }
  //@return response()
  get f() {
    return this.form.controls;
  }

  selectPort(type: PortFilterType, port: any) {
    if (type === "fromSchedule") {
      this.selectedPortFromSchedule = port.PORTNAME + ", " + port.ISO3;
      this.selectedPortFromScheduleCode = port.CODEPORT;
      this.filteredPortsFromSchedule = [];
      this.dropdownOpenFromSchedule = false;
      this.form
        .get("scheduleSearch.selectedPortFromSchedule")
        ?.setValue(port.CODEPORT);
    } else if (type === "toSchedule") {
      this.selectedPortToSchedule = port.PORTNAME + ", " + port.ISO3;
      this.selectedPortToScheduleCode = port.CODEPORT;
      this.filteredPortsToSchedule = [];
      this.dropdownOpenToSchedule = false;
      this.form
        .get("scheduleSearch.selectedPortToSchedule")
        ?.setValue(port.CODEPORT);
    } else if (type === "fromComparison") {
      this.selectedPortFromComparison = port.PORTNAME + ", " + port.ISO3;
      this.selectedPortFromComparisonCode = port.CODEPORT;
      this.filteredPortsFromComparison = [];
      this.dropdownOpenFromComparison = false;
      this.form
        .get("comparisonSearch.selectedPortFromComparison")
        ?.setValue(port.CODEPORT);
    } else {
      this.selectedPortToComparison = port.PORTNAME + ", " + port.ISO3;
      this.selectedPortToComparisonCode = port.CODEPORT;
      this.filteredPortsToComparison = [];
      this.dropdownOpenToComparison = false;
      this.form
        .get("comparisonSearch.selectedPortToComparison")
        ?.setValue(port.CODEPORT);
    }
  }

  closeDropdown(type: PortFilterType) {
    setTimeout(() => {
      if (type === "fromSchedule") {
        this.dropdownOpenFromSchedule = false;
      } else if (type === "toSchedule") {
        this.dropdownOpenToSchedule = false;
      } else if (type === "fromComparison") {
        this.dropdownOpenFromComparison = false;
      } else {
        this.dropdownOpenToComparison = false;
      }
    }, 150);
  }

  //set data to insert in localstore then go to another page
  async submit(): Promise<void> {
    const activeForm =
      this.activeTab === "marine"
        ? (this.form.get("comparisonSearch") as FormGroup)
        : (this.form.get("scheduleSearch") as FormGroup);

    // Validation
    activeForm.markAllAsTouched();
    if (!activeForm.valid) {
      this.spinner.show();
      this.configService.showErrorAlert("Please fill all required fields!");
      setTimeout(() => this.spinner.hide(), 5000);
      return;
    }

    // Save form data for restoration
    this.heroService.saveForm(activeForm.value);
    await new Promise(r => setTimeout(r, 100)); // petit délai de 100ms pour s’assurer de la persistance

    // Auth check
    const isAuth = await this.auth$.pipe(take(1)).toPromise();
    if (!isAuth) {
      this.router.navigate(["/login"], {
        queryParams: { returnUrl: this.router.url },
      });
      return;
    }

    // Navigate to correct results page
    if (this.activeTab === "marine") {
      window.location.href = "/search-comp-results";
    } else {
      this.router.navigateByUrl("/search-sched-results");
    }
  }

  //******************************************************************************************** */
  //************************************ * this code for design **********************************/
  //******************************************************************************************** */
  currentBg: string = "/assets/images/marine-bg.png";
  isPopoverVisible = false;
  isIntervalMode = false;
  private initialized = false;

  @ViewChild("intervalDateInput", { static: false })
  intervalDateInput?: ElementRef;

  @ViewChild("singleDateInput", { static: false })
  singleDateInput?: ElementRef;

  @ViewChild("singleDateScheduleInput", { static: false })
  singleDateScheduleInput?: ElementRef;

  private intervalPicker?: flatpickr.Instance;
  private singlePicker?: flatpickr.Instance;
  private schedulePicker?: flatpickr.Instance;
  ngAfterViewInit(): void {
    this.initAllDatePickers();
    this.initialized = true;
  }

  ngAfterViewChecked(): void {
    if (!this.initialized && this.inputsAreAvailable()) {
      this.initAllDatePickers();
      this.initialized = true;
    }
  }

  selectTab(tab: "marine" | "schedules") {
    this.activeTab = tab;
    this.currentBg =
      tab === "marine"
        ? "/assets/images/marine-bg.png"
        : "/assets/images/schedules-bg.png";

    this.initialized = false; // force re-init
    this.cdr.detectChanges(); // ensure DOM is updated
  }

  toggleDateMode(): void {
    this.isIntervalMode = !this.isIntervalMode;
    this.initialized = false; // force re-init
    this.cdr.detectChanges(); // allow DOM update
  }

  togglePopover(event: Event) {
    event.stopPropagation();
    this.isPopoverVisible = !this.isPopoverVisible;
  }

  onKeyMenuItem(event: KeyboardEvent): void {
    if (event.key === "Enter" || event.key === " ") {
      event.preventDefault();
    }
  }

  private inputsAreAvailable(): boolean {
    return (
      (!!this.intervalDateInput?.nativeElement && this.isIntervalMode) ||
      (!!this.singleDateInput?.nativeElement && !this.isIntervalMode) ||
      !!this.singleDateScheduleInput?.nativeElement
    );
  }

  private initAllDatePickers(): void {
    this.destroyAllPickers();

    // Always initialize the schedule picker if visible
    if (this.singleDateScheduleInput?.nativeElement) {
      this.schedulePicker = flatpickr(
        this.singleDateScheduleInput.nativeElement,
        {
          dateFormat: "Y-m-d",
          minDate: "today",
        }
      );
    }

    // Initialize marine mode-specific picker
    if (this.isIntervalMode) {
      if (this.intervalDateInput?.nativeElement) {
        this.intervalPicker = flatpickr(this.intervalDateInput.nativeElement, {
          mode: "range",
          dateFormat: "Y-m-d",
        });
      }
    } else if (this.singleDateInput?.nativeElement) {
      this.singlePicker = flatpickr(this.singleDateInput.nativeElement, {
        dateFormat: "Y-m-d",
      });
    }
  }

  private destroyAllPickers(): void {
    this.intervalPicker?.destroy();
    this.singlePicker?.destroy();

    this.intervalPicker = undefined;
    this.singlePicker = undefined;
  }
}
