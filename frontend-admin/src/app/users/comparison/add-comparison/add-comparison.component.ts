import { Component, CUSTOM_ELEMENTS_SCHEMA, OnInit } from "@angular/core";
import { ComparisonService } from "../../services/comparison.service";
import { ContainerService } from "../../../admin/services/container.service";
import { SharedModule } from "../../../share/shared.module";
import { FormBuilder, FormGroup, Validators, FormArray } from "@angular/forms";
import { NgxSpinnerService } from "ngx-spinner";
import { ConfigService } from "../../../services/config/config.service";
import { ApiResponses } from "../../../modules/api-responses";
import { Router } from "@angular/router";
import { Container } from "../../../admin/modules/container";
import { ContainerComparison } from "../../modules/container-comparison";
import { DateRange } from "../../modules/date-range";
import { GargoService } from "../../../admin/services/gargo.service";
import { TransportationService } from "../../../admin/services/transportation.service";
import { CompaniesService } from "../../../admin/services/companies.service";
import { PortService } from "../../../admin/services/port.service";
import { Port } from "../../../admin/modules/port";
import { Company } from "../../../admin/modules/company";
import { Transportation } from "../../../admin/modules/transportation";
import { Gargo } from "../../../admin/modules/gargo";
import dayjs from "dayjs";
import customParseFormat from "dayjs/plugin/customParseFormat";
dayjs.extend(customParseFormat);

@Component({
  selector: "app-add-comparison",
  standalone: true,
  imports: [SharedModule],
  templateUrl: "./add-comparison.component.html",
  styleUrl: "./add-comparison.component.scss",
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class AddComparisonComponent implements OnInit {
  form!: FormGroup;
  subjectData: FormGroup[] = [];
  submitted = false;
  ports: Port[] = [];
  companies: Company[] = [];
  transportations: Transportation[] = [];
  gargos: Gargo[] = [];
  containers: Container[] = [];
  container_comparisons: ContainerComparison[][] = [];
  results: DateRange[] = [];

  constructor(
    private readonly fb: FormBuilder,
    private readonly comparisonService: ComparisonService,
    private readonly containerService: ContainerService,
    private readonly gargoService: GargoService,
    private readonly transportationService: TransportationService,
    private readonly companiesService: CompaniesService,
    private readonly portService: PortService,
    private readonly configService: ConfigService,
    private readonly spinner: NgxSpinnerService,
    private readonly router: Router
  ) {}

  ngOnInit(): void {
    this.getCompanies();
    this.getContainers();
    this.getTransportations();
    this.getGargos();
    this.getPorts();
    this.initializeForm();
  }

  initializeForm(): void {
    this.form = this.fb.group({
      portFromId: ["", [Validators.required]],
      portToId: ["", [Validators.required]],
      companyId: ["", [Validators.required]],
      transportationId: ["", [Validators.required]],
      gargoId: ["", [Validators.required]],
      dateDepart: this.fb.array([]),
      dateArrive: this.fb.array([]),
      price: this.fb.array([]),
      containerId: this.fb.array([]),
    });
  }

  // Helper methods to access FormArrays
  get dateDepartArray(): FormArray {
    return this.form.get("dateDepart") as FormArray;
  }

  get dateArriveArray(): FormArray {
    return this.form.get("dateArrive") as FormArray;
  }

  get priceArray(): FormArray {
    return this.form.get("price") as FormArray;
  }

  get containerIdArray(): FormArray {
    return this.form.get("containerId") as FormArray;
  }

  //@return response()
  get f() {
    return this.form.controls;
  }

  // Method to add a row to subject data
  addRow(): void {
    const newRow = this.fb.group({
      containerId: ["", Validators.required],
      price: ["", Validators.required],
    });
    this.subjectData.push(newRow);

    // Add empty controls to the form arrays
    this.priceArray.push(this.fb.control(""));
    this.containerIdArray.push(this.fb.control(""));
  }

  // Method to remove a row from subject data
  removeRow(index: number): void {
    this.subjectData.splice(index, 1);
    this.priceArray.removeAt(index);
    this.containerIdArray.removeAt(index);
  }

  getDataContainers(event: Event, index: number): void {
    const val = event.target as HTMLInputElement;

    // Fetch data for the selected Containers including price and date
    this.containerService.show(val.value).subscribe({
      next: (data: Container | null) => {
        if (!data) {
          this.router.navigateByUrl("comparisons");
          return;
        }
        const container = {
          id: data.id,
          containerName: data.containerName,
        };

        // Assuming you have matiers and teachers as 2D arrays
        this.container_comparisons[index] = [container];

        // Update the form group for the specified row index
        const rowFormGroup = this.subjectData.at(index);
        if (rowFormGroup) {
          rowFormGroup.patchValue({
            containerId: data.id,
          });
          this.containerIdArray.at(index).setValue(data.id);
        }
      },
      error: () => {
        this.configService.showErrorAlert(
          "Une erreur est survenue lors de la récupération des donnes."
        );
      },
    });
  }

  getDataPrices(val: Event, rowIndex: number): void {
    const target = val.target as HTMLInputElement;
    const id = parseInt(target.value);
    // Update the form price_classe for the specified row index
    const rowFormPrice = this.subjectData.at(rowIndex);
    if (rowFormPrice) {
      rowFormPrice.get("price")?.setValue(id);
      this.priceArray.at(rowIndex).setValue(id);
    }
  }

  //set data to insert in database
  submit(): void {
    this.submitted = true;
    if (this.form.invalid) {
      this.configService.showErrorAlert(
        "Veuillez corriger les erreurs dans le formulaire."
      );
      return;
    }

    this.spinner.show();
    this.comparisonService.create(this.form.value).subscribe({
      next: (res: ApiResponses<void>) => {
        this.spinner.hide();
        if (res.isSuccess) {
          this.configService.showSuccessAlert("Action réussie.");
          this.router.navigateByUrl(this.configService.ENDPOINTS.comparisons);
        } else {
          this.configService.showErrorAlert("Une erreur s'est produite.");
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

  // Method to get all container
  getContainers(): void {
    this.containerService.getAll().subscribe({
      next: (data: Container[] | null) => {
        this.containers = data || [];
      },
      error: () => {
        this.configService.showErrorAlert(
          "Une erreur est survenue lors de la récupération des data."
        );
      },
    });
  }

  // Method to get all ports
  getPorts(): void {
    this.portService.getAll().subscribe({
      next: (data: Port[] | null) => {
        this.ports = data || [];
      },
      error: () => {
        this.configService.showErrorAlert(
          "Une erreur est survenue lors de la récupération des data."
        );
      },
    });
  }

  // Method to get all Transportations
  getTransportations(): void {
    this.transportationService.getAll().subscribe({
      next: (data: Transportation[] | null) => {
        this.transportations = data || [];
      },
      error: () => {
        this.configService.showErrorAlert(
          "Une erreur est survenue lors de la récupération des data."
        );
      },
    });
  }

  // Method to get all gargo
  getGargos(): void {
    this.gargoService.getAll().subscribe({
      next: (data: Gargo[] | null) => {
        this.gargos = data || [];
      },
      error: () => {
        this.configService.showErrorAlert(
          "Une erreur est survenue lors de la récupération des data."
        );
      },
    });
  }

  // Method to get all companies
  getCompanies(): void {
    this.companiesService.getAll().subscribe({
      next: (data: Company[] | null) => {
        this.companies = data || [];
      },
      error: () => {
        this.configService.showErrorAlert(
          "Une erreur est survenue lors de la récupération des data."
        );
      },
    });
  }

  // Method to copie from EXCEL then past to table
  async pasteFromClipboard() {
    try {
      const text = await navigator.clipboard.readText();

      if (!text) {
        this.configService.showErrorAlert(
          "Presse-papiers vide ou non pris en charge."
        );
        return;
      }

      this.results = [];
      this.dateDepartArray.clear();
      this.dateArriveArray.clear();

      const lines = text.trim().split(/\r?\n/);

      for (const line of lines) {
        const cols = line.split("\t");

        // Extract all valid dates from columns
        const dates: { raw: string; parsed: dayjs.Dayjs }[] = cols
          .map((cell) => {
            const formatted = this.formatDate(cell);
            return formatted ? dayjs(formatted, "DD/MM/YYYY") : null;
          })
          .filter((d): d is dayjs.Dayjs => d !== null)
          .map((parsed) => ({ raw: parsed.format("DD/MM/YYYY"), parsed }));

        if (dates.length >= 2) {
          // Sort by date
          dates.sort((a, b) => a.parsed.valueOf() - b.parsed.valueOf());

          const dateDepart = dates[0].raw;
          const dateArrive = dates[1].raw;

          this.results.push({ dateDepart, dateArrive });
          this.dateDepartArray.push(this.fb.control(dateDepart));
          this.dateArriveArray.push(this.fb.control(dateArrive));
        }
      }

      if (this.results.length === 0) {
        this.configService.showErrorAlert(
          "Aucune donnée valide trouvée dans le presse-papiers."
        );
      }
    } catch (err) {
      this.configService.showErrorAlert(
        "Erreur lors de la lecture du presse-papiers."
      );
      console.error(err);
    }
  }

  formatDate(input: string): string | null {
    const dateStr = input.trim();

    // Simplified regex patterns for extracting possible date parts
    const patterns = [
      /\d{4}[-/]\d{2}[-/]\d{2}( \d{2}:\d{2})?/, // 2025-07-12 or 2025-07-12 13:00
      /\d{1,2}[-/]\d{1,2}[-/]\d{4}/, // 7/9/2025 or 07-09-2025
      /[A-Z]{3}-\d{1,2}-\d{4}/, // JUL-05-2025
      /\d{1,2}-[A-Z]{3}-\d{4}/, // 05-JUL-2025
      /\d{4}\/\d{2}\/\d{2}/, // 2025/08/22
      /[A-Z]{3,9} \d{1,2} \d{4}/, // July 5 2025
      /\d{1,2} [A-Z]{3,9} \d{4}/, // 21 Jul 2025
      /\d{1,2}(st|nd|rd|th)? [A-Z]{3,9} \d{4}/, // 21st Jul 2025
    ];

    let rawDate: string | null = null;

    for (const pattern of patterns) {
      const result = pattern.exec(dateStr);
      if (result) {
        rawDate = result[0];
        break;
      }
    }

    if (!rawDate) return null;

    const formatsToTry = [
      "D/M/YYYY",
      "DD/MM/YYYY",
      "M/D/YYYY",
      "MM/DD/YYYY",
      "YYYY-MM-DD",
      "YYYY/MM/DD",
      "YYYY-MM-DD HH:mm",
      "D/M/YYYY HH:mm",
      "DD-MMM-YYYY",
      "D-MMM-YYYY",
      "MMM-DD-YYYY",
      "MMM D YYYY",
      "MMMM D YYYY",
      "D MMM YYYY",
      "Do MMM YYYY",
      "D MMMM YYYY",
      "ddd Do MMM YYYY",
      "dddd, DD-MMM-YYYY",
    ];

    for (const format of formatsToTry) {
      const parsed = dayjs(rawDate, format, true);
      if (parsed.isValid()) {
        return parsed.format("DD/MM/YYYY");
      }
    }

    return null;
  }

  clearTable() {
    this.results = [];
    // Clear the date arrays when clearing the table
    while (this.dateDepartArray.length) {
      this.dateDepartArray.removeAt(0);
    }
    while (this.dateArriveArray.length) {
      this.dateArriveArray.removeAt(0);
    }
  }
}
