import {
  Component,
  CUSTOM_ELEMENTS_SCHEMA,
  OnInit,
  Renderer2,
} from '@angular/core';
import { ScheduleService } from '../../services/schedule.service';
import { SharedModule } from '../../../share/shared.module';
import { Router } from '@angular/router';
import { Config } from 'datatables.net';
import { Schedule } from '../../modules/schedule';
import { ConfigService } from '../../../services/config/config.service';
import Swal, { SweetAlertResult } from 'sweetalert2';
import { Port } from '../../../admin/modules/port';
import { PortService } from '../../../admin/services/port.service';
import { FormBuilder, FormGroup } from '@angular/forms';
import { UserService } from '../../../admin/services/user.service';
import { User } from '../../../admin/modules/user';

@Component({
  selector: 'app-historique-schedule',
  standalone: true,
  imports: [SharedModule],
  templateUrl: './historique-schedule.component.html',
  styleUrl: './historique-schedule.component.scss',
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class HistoriqueScheduleComponent implements OnInit {
  dtOptions: Config = {};
  schedules: Schedule[] = [];
  ports: Port[] = [];
  users: User[] = [];
  // Add filter properties
  filterForm!: FormGroup;
  currentFilters: any = {};

  constructor(
    private readonly scheduleService: ScheduleService,
    private readonly userService: UserService,
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
      dateDebut: [null],
      dateFin: [null],
      userId: [null],
    });
    this.getPorts();
    this.getUsers();
    this.initializeDataTable();
  }

  private initializeDataTable(): void {
    this.dtOptions = {
      serverSide: true,
      processing: true,
      pagingType: 'full_numbers',
      ajax: (params: any, callback: any) => {
        this.scheduleService
          .getPaginatedDataHistorique(params, this.currentFilters)
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
    if (this.currentFilters.dateDebut) {
      this.currentFilters.dateDebut = this.formatDate(
        this.currentFilters.dateDebut
      );
    }
    if (this.currentFilters.dateFin) {
      this.currentFilters.dateFin = this.formatDate(
        this.currentFilters.dateFin
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
      dateDebut: null,
      dateFin: null,
      userId: null,
    };
    this.refreshDataTable();
  }
  // Format date from YYYY-MM-DD to DD/MM/YYYY
  private formatDate(dateString: string | null | undefined): string | null {
    if (
      !dateString ||
      typeof dateString !== 'string' ||
      !dateString.includes('-')
    ) {
      return null;
    }

    const [year, month, day] = dateString.split('-');
    if (!year || !month || !day) return null;

    return `${day}/${month}/${year}`;
  }
  private getTableColumns(): any[] {
    return [
      { data: 'portFromName', title: 'Port From', searchable: true },
      { data: 'portToName', title: 'Port To', searchable: true },
      { data: 'dateDepart', title: 'Date départ', searchable: true },
      { data: 'dateArrive', title: 'Date arrive', searchable: true },
      { data: 'companyName', title: 'Company', searchable: true },
      { data: 'transit', title: 'Transit time', searchable: true },
      { data: 'vessel', title: 'Vessel', searchable: true },
      { data: 'refVoyage', title: 'Réf.voyage', searchable: true },
      { data: 'serviceName', title: 'Service', searchable: true },
      {
        title: 'Actions',
        data: null,
        orderable: false,
        searchable: false,
        render: this.renderActionButtons.bind(this),
        className: 'text-center',
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

  private handleRowCallback(row: Node, data: any): Node {
    setTimeout(() => this.setupRowEventListeners(row, data));
    return row;
  }

  private setupRowEventListeners(row: Node, data: any): void {
    this.setupDeleteButtonListener(row, data);
  }

  private setupDeleteButtonListener(row: Node, data: any): void {
    const deleteBtn = (row as HTMLElement).querySelector('.delete-btn');
    if (deleteBtn) {
      this.renderer.listen(deleteBtn, 'click', () => {
        this.showDeleteConfirmation(data.id);
      });
    }
  }

  private showDeleteConfirmation(id: string): void {
    Swal.fire({
      title: 'Etes-vous sûr de vouloir supprimer ?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Oui, supprimer',
      cancelButtonText: 'Annuler',
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
    this.scheduleService.delete(id).subscribe({
      next: (response) => this.handleDeleteSuccess(response),
      error: () => this.handleDeleteError(),
    });
  }

  private handleDeleteSuccess(response: any): void {
    if (response.isSuccess) {
      this.configService.showSuccessAlert(
        'Emploi du temps supprimé avec succès.'
      );
    } else {
      this.configService.showErrorAlert("Une erreur s'est produite.");
    }
    this.refreshDataTable();
  }

  private handleDeleteError(): void {
    this.configService.showErrorAlert(
      'Une erreur est survenue lors de la suppression.'
    );
  }

  private refreshDataTable(): void {
    ($('.dataTable') as any).DataTable().ajax.reload(null, false);
  }

  // Method to get all ports
  getPorts(): void {
    this.portService.getAll().subscribe({
      next: (data: Port[] | null) => {
        this.ports = data || [];
      },
      error: () => {
        this.configService.showErrorAlert(
          'Une erreur est survenue lors de la récupération des data.'
        );
      },
    });
  }

  // Method to get all users// Method to get all users
  getUsers(): void {
    this.userService.getAll().subscribe({
      next: (data: User[] | null) => {
        this.users = (data || []).map((user) => ({
          ...user,
          fullName: `${user.firstName} ${user.lastName}`, // Add computed fullName
        }));
      },
      error: () => {
        this.configService.showErrorAlert(
          'Une erreur est survenue lors de la récupération des data.'
        );
      },
    });
  }
}
