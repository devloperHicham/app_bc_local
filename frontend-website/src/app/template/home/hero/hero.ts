import { CommonModule } from "@angular/common";
import {
  AfterViewInit,
  AfterViewChecked,
  ChangeDetectorRef,
  Component,
  ElementRef,
  ViewChild,
  OnInit,
} from "@angular/core";
import { FormsModule } from "@angular/forms";
import { RouterLink } from "@angular/router";
import { TranslateModule, TranslateService } from "@ngx-translate/core";
import flatpickr from "flatpickr";
import "flatpickr/dist/flatpickr.min.css";
import { HeroSerice } from "../services/hero-serice";
import { Container, Port, Transportation } from "../../../modules/data-json";

@Component({
  selector: "app-hero",
  standalone: true,
  imports: [RouterLink, CommonModule, FormsModule, TranslateModule],
  templateUrl: "./hero.html",
  styleUrls: ["./hero.css"],
})
export class Hero implements AfterViewInit, AfterViewChecked, OnInit {
  ports: Port[] = [];
  containers: Container[] = [];
  transportations: Transportation[] = [];
  selectedPortFrom = "";
  filteredPortsFrom: any[] = [];
  dropdownOpenFrom = false;
  selectedPortTo = "";
  filteredPortsTo: any[] = [];
  dropdownOpenTo = false;

  constructor(
    private readonly heroSerice: HeroSerice,
    private readonly cdr: ChangeDetectorRef,
    private readonly translate: TranslateService
  ) {}

  ngOnInit(): void {
    // Load ports data on initialization
    this.heroSerice.getPorts().subscribe((data) => {
      this.ports = data;
    });
    // Load containers data on initialization
    this.heroSerice.getContainers().subscribe((data) => {
      this.containers = data;
    });
    // Load transportations data on initialization
    this.heroSerice.getTransportations().subscribe((data) => {
      this.transportations = data;
    });
  }

  filterPorts(type: "from" | "to", event: any) {
    const val = event.target.value.toLowerCase().trim();

    if (val.length < 3) {
      if (type === "from") {
        this.filteredPortsFrom = [];
        this.dropdownOpenFrom = false;
      } else {
        this.filteredPortsTo = [];
        this.dropdownOpenTo = false;
      }
      return;
    }

    const filtered = this.ports.filter(
      (port) =>
        port.PORTNAME.toLowerCase().startsWith(val) || // port name starts with input
        port.CODEPORT.toLowerCase().startsWith(val) // port code starts with input
    );

    if (type === "from") {
      this.filteredPortsFrom = filtered;
      this.dropdownOpenFrom = filtered.length > 0;
    } else {
      this.filteredPortsTo = filtered;
      this.dropdownOpenTo = filtered.length > 0;
    }
  }

  selectPort(type: "from" | "to", port: any) {
    if (type === "from") {
      this.selectedPortFrom = port.PORTNAME + ", " + port.ISO3;
      this.filteredPortsFrom = [];
      this.dropdownOpenFrom = false;
    } else {
      this.selectedPortTo = port.PORTNAME + ", " + port.ISO3;
      this.filteredPortsTo = [];
      this.dropdownOpenTo = false;
    }
  }

  closeDropdown(type: "from" | "to") {
    setTimeout(() => {
      if (type === "from") this.dropdownOpenFrom = false;
      else this.dropdownOpenTo = false;
    }, 150); // allow click to select
  }

  //******************************************************************************************** */
  //************************************ * this code for design **********************************/
  //******************************************************************************************** */
  activeTab: "marine" | "schedules" = "marine";
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
