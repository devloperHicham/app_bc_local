import {
  Component,
  CUSTOM_ELEMENTS_SCHEMA,
  OnInit,
  Renderer2,
} from "@angular/core";
import { ComparisonService } from "../../services/comparison.service";
import { SharedModule } from "../../../share/shared.module";
import { Router, RouterLink } from "@angular/router";
import { Config } from "datatables.net";
import { Comparison } from "../../modules/comparison";
import { ConfigService } from "../../../services/config/config.service";
import Swal, { SweetAlertResult } from "sweetalert2";
import { Port } from "../../../admin/modules/port";
import { Company } from "../../../admin/modules/company";
import { CompaniesService } from "../../../admin/services/companies.service";
import { PortService } from "../../../admin/services/port.service";
import { FormBuilder, FormGroup } from "@angular/forms";

@Component({
  selector: "app-index-comparison",
  standalone: true,
  imports: [SharedModule, RouterLink],
  templateUrl: "./index-comparison.component.html",
  styleUrls: ["./index-comparison.component.scss"],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class IndexComparisonComponent implements OnInit {
  dtOptions: Config = {};
  comparisons: Comparison[] = [];
  ports: Port[] = [];
  companies: Company[] = [];
  selectedComparisonIds: string[] = [];

  // Add filter properties
  filterForm!: FormGroup;
  currentFilters: any = {};

  constructor(
    private readonly comparisonService: ComparisonService,
    private readonly companiesService: CompaniesService,
    private readonly portService: PortService,
    private readonly renderer: Renderer2,
    private readonly router: Router,
    private readonly configService: ConfigService,
    private readonly fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.filterForm = this.fb.group({
      portFromId: [null],
      portToId: [null],
      dateDepart: [null],
      dateArrive: [null],
      companyId: [null],
    });
    this.getPorts();
    this.getCompanies();
    this.initializeDataTable();
  }

  private initializeDataTable(): void {
    this.dtOptions = {
      serverSide: true,
      processing: true,
      pagingType: "full_numbers",
      ajax: (params: any, callback: any) => {
        this.comparisonService
          .getPaginatedData(params, this.currentFilters)
          .subscribe(callback);
      },
      lengthMenu: [25, 50, 100, 200],
      columns: this.getTableColumns(),
      rowCallback: this.handleRowCallback.bind(this),
    };
  }

  // Add search method
  onSearch(): void {
    // Get current filter values
    this.currentFilters = this.filterForm.value;

    Object.keys(this.currentFilters).forEach((key) => {
      this.currentFilters[key] ??= null;
    });

    // Convertir les dates au format attendu par le backend
    if (this.currentFilters.dateDepart) {
      this.currentFilters.dateDepart = this.formatDate(
        this.currentFilters.dateDepart
      );
    }
    if (this.currentFilters.dateArrive) {
      this.currentFilters.dateArrive = this.formatDate(
        this.currentFilters.dateArrive
      );
    }

    this.refreshDataTable();
  }

  // Add reset method
  onReset(): void {
    this.filterForm.reset();
    this.currentFilters = {
      portFromId: null,
      portToId: null,
      dateDepart: null,
      dateArrive: null,
      companyId: null,
    };
    this.refreshDataTable();
  }

  private formatDate(dateString: string): string {
    const [year, month, day] = dateString.split("-");
    return `${day}/${month}/${year}`;
  }

  private getTableColumns(): any[] {
    return [
      {
        title: `<div class="form-check">
                  <input type="checkbox" id="select-all-checkbox" class="form-check-input" />
                </div>`,
        data: null,
        orderable: false,
        searchable: false,
        render: this.renderActionCheckBoxs.bind(this),
        className: "text-center",
      },
      { data: "portFromName", title: "Port From", searchable: true },
      { data: "portToName", title: "Port To", searchable: true },
      { data: "dateDepart", title: "Date départ", searchable: true },
      { data: "dateArrive", title: "Date arrive", searchable: true },
      { data: "companyName", title: "Company", searchable: true },
      { data: "transportationName", title: "Transport by", searchable: true },
      { data: "gargoName", title: "Gargo type", searchable: true },
      { data: "containerName", title: "Contenaire", searchable: true },
      { data: "price", title: "Prix", searchable: true },
      {
        title: "Actions",
        data: null,
        orderable: false,
        searchable: false,
        render: this.renderActionButtons.bind(this),
        className: "text-center",
      },
    ];
  }

  private renderActionButtons(data: any, type: any, row: any): string {
    return `
      <button class="btn btn-danger btn-sm mr-2 delete-btn" data-id="${row.id}">
        <ion-icon name="trash-sharp"></ion-icon>
      </button>
    `;
  }

  private renderActionCheckBoxs(data: any, type: any, row: any): string {
    return `
    <div class="form-check">
      <input type="checkbox" class="form-check-input comparison-checkbox" value="${row.id}">
    </div>
  `;
  }

  private handleRowCallback(row: Node, data: any): Node {
    setTimeout(() => {
      this.setupRowEventListeners(row, data);
      this.setupCheckboxListener(row, data);
      this.setupSelectAllListener();
    });
    return row;
  }

  private setupSelectAllListener(): void {
    const selectAllCheckbox = document.getElementById(
      "select-all-checkbox"
    ) as HTMLInputElement;

    if (selectAllCheckbox) {
      this.renderer.listen(selectAllCheckbox, "change", (event) => {
        const isChecked = (event.target as HTMLInputElement).checked;

        const checkboxes = document.querySelectorAll<HTMLInputElement>(
          ".comparison-checkbox"
        );

        checkboxes.forEach((checkbox) => {
          if (checkbox !== selectAllCheckbox) {
            checkbox.checked = isChecked;

            const id = checkbox.value;

            if (isChecked && !this.selectedComparisonIds.includes(id)) {
              this.selectedComparisonIds.push(id);
            } else if (!isChecked) {
              this.selectedComparisonIds = [];
            }
          }
        });
      });
    }
  }

  private setupCheckboxListener(row: Node, data: any): void {
    const checkbox = (row as HTMLElement).querySelector(
      ".comparison-checkbox"
    ) as HTMLInputElement;

    if (checkbox) {
      this.renderer.listen(checkbox, "change", (event) => {
        const isChecked = event.target.checked;
        const id = event.target.value;

        if (isChecked) {
          this.selectedComparisonIds.push(id);
        } else {
          this.selectedComparisonIds = this.selectedComparisonIds.filter(
            (selectedId) => selectedId !== id
          );
        }
      });
    }
  }

  private setupRowEventListeners(row: Node, data: any): void {
    this.setupEditButtonListener(row, data);
    this.setupDeleteButtonListener(row, data);
  }

  private setupEditButtonListener(row: Node, data: any): void {
    const editBtn = (row as HTMLElement).querySelector(".edit-btn");
    if (editBtn) {
      this.renderer.listen(editBtn, "click", () => {
        this.router.navigate(["/edit-comparison", data.id]);
      });
    }
  }

  private setupDeleteButtonListener(row: Node, data: any): void {
    const deleteBtn = (row as HTMLElement).querySelector(".delete-btn");
    if (deleteBtn) {
      this.renderer.listen(deleteBtn, "click", () => {
        this.showDeleteConfirmation(data.id);
      });
    }
  }

  private showDeleteConfirmation(id: string): void {
    Swal.fire({
      title: "Etes-vous sûr de vouloir supprimer ?",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
      confirmButtonText: "Oui, supprimer",
      cancelButtonText: "Annuler",
    }).then((result) => this.handleDeleteConfirmationResult(result, id));
  }

  private handleDeleteConfirmationResult(
    result: SweetAlertResult<any>,
    id: string
  ): void {
    if (result.isConfirmed) {
      this.delete(id);
    }
  }

  private delete(id: string): void {
    this.comparisonService.delete(id).subscribe({
      next: (response) => this.handleDeleteSuccess(response),
      error: () => this.handleDeleteError(),
    });
  }

  private handleDeleteSuccess(response: any): void {
    if (response.isSuccess) {
      this.configService.showSuccessAlert("Comparaison supprimée avec succès.");
    } else {
      this.configService.showErrorAlert("Une erreur s'est produite.");
    }
    this.refreshDataTable();
  }

  private handleDeleteError(): void {
    this.configService.showErrorAlert(
      "Une erreur est survenue lors de la suppression."
    );
  }

  private refreshDataTable(): void {
    ($(".dataTable") as any).DataTable().ajax.reload(null, false);
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

  confirmBulkDelete(): void {
    Swal.fire({
      title: `Supprimer tous les éléments ?`,
      icon: "warning",
      showCancelButton: true,
      confirmButtonText: "Oui, supprimer",
      cancelButtonText: "Annuler",
    }).then((result) => {
      if (result.isConfirmed) {
        this.allDelete();
      }
    });
  }

  allDelete(): void {
    this.comparisonService.allDelete(this.selectedComparisonIds).subscribe({
      next: () => {
        this.configService.showSuccessAlert(
          "Comparaisons supprimées avec succès."
        );
        this.selectedComparisonIds = [];
        this.refreshDataTable();
      },
      error: () => {
        this.configService.showErrorAlert("Erreur lors de la suppression.");
      },
    });
  }
}
