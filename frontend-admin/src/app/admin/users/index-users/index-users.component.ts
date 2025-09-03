import {
  Component,
  CUSTOM_ELEMENTS_SCHEMA,
  OnInit,
  Renderer2,
} from "@angular/core";
import { SharedModule } from "../../../share/shared.module";
import { Router, RouterLink } from "@angular/router";
import { Config } from "datatables.net";
import { User } from "../../modules/user";
import { ConfigService } from "../../../services/config/config.service";
import Swal, { SweetAlertResult } from "sweetalert2";
import { UserService } from "../../services/user.service";

@Component({
  selector: "app-index-users",
  standalone: true,
  imports: [SharedModule, RouterLink],
  templateUrl: "./index-users.component.html",
  styleUrl: "./index-users.component.scss",
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class IndexUsersComponent implements OnInit {
  dtOptions: Config = {};
  users: User[] = [];

  constructor(
    private readonly userService: UserService,
    private readonly renderer: Renderer2,
    private readonly router: Router,
    private readonly configService: ConfigService
  ) {}

  ngOnInit(): void {
    this.initializeDataTable();
  }

  private initializeDataTable(): void {
    this.dtOptions = {
      serverSide: true,
      processing: true,
      pagingType: "full_numbers",
      ajax: this.handleAjaxRequest.bind(this),
      lengthMenu: [25, 50, 100, 150],
      columns: this.getTableColumns(),
      rowCallback: this.handleRowCallback.bind(this),
    };
  }

  private handleAjaxRequest(dataTablesParameters: any, callback: any): void {
    this.userService.getPaginatedData(dataTablesParameters).subscribe(callback);
  }

  private getTableColumns(): any[] {
    return [
      { data: "lastName", title: "Nom", searchable: true },
      { data: "firstName", title: "Prénom", searchable: true },
      { data: "phoneNumber", title: "Téléphone", searchable: true },
      { data: "email", title: "Email", searchable: true },
      { data: "userType", title: "Rôle", searchable: true },
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
      <button class="btn btn-success btn-sm mr-2 edit-btn" data-id="${row.id}">
        <ion-icon name="eye-sharp"></ion-icon>
      </button>
      <button class="btn btn-danger btn-sm mr-2 delete-btn" data-id="${row.id}">
        <ion-icon name="trash-sharp"></ion-icon>
      </button>
      <button class="btn btn-warning btn-sm mr-2 reset-password-btn" data-id="${row.id}">
        <ion-icon name="key-sharp"></ion-icon>
      </button>
    `;
  }

  private handleRowCallback(row: Node, data: any): Node {
    setTimeout(() => this.setupRowEventListeners(row, data));
    return row;
  }

  private setupRowEventListeners(row: Node, data: any): void {
    this.setupEditButtonListener(row, data);
    this.setupDeleteButtonListener(row, data);
    this.setupResetPasswordButtonListener(row, data);
  }

  private setupEditButtonListener(row: Node, data: any): void {
    const editBtn = (row as HTMLElement).querySelector(".edit-btn");
    if (editBtn) {
      this.renderer.listen(editBtn, "click", () => {
        this.router.navigate(["/edit-users", data.id]);
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

  private setupResetPasswordButtonListener(row: Node, data: any): void {
    const deleteBtn = (row as HTMLElement).querySelector(".reset-password-btn");
    if (deleteBtn) {
      this.renderer.listen(deleteBtn, "click", () => {
        this.showResetPasswordConfirmation(data.id);
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

  private showResetPasswordConfirmation(id: string): void {
    Swal.fire({
      title: "Etes-vous sûr de vouloir initialiser le mot de passe ?",
      text: 'Le mot de passe sera réinitialisé à "bc12345@"',
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
      confirmButtonText: "Oui, initialiser",
      cancelButtonText: "Annuler",
    }).then((result) => {
      if (result.isConfirmed) {
        this.resetPassword(id);
      }
    });
  }

  private resetPassword(id: string): void {
    const defaultPasswordPayload = {
      newPassword: "bc12345@",
    };

    this.userService.resetPassword(id, defaultPasswordPayload).subscribe({
      next: (response) => {
        if (response.isSuccess) {
          this.configService.showSuccessAlert(
            "Mot de passe réinitialisé avec succès."
          );
        } else {
          this.configService.showErrorAlert("Une erreur s'est produite.");
        }
        this.refreshDataTable();
      },
      error: () => {
        this.configService.showErrorAlert(
          "Erreur lors de la réinitialisation du mot de passe."
        );
      },
    });
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
    this.userService.delete(id).subscribe({
      next: (response) => this.handleDeleteSuccess(response),
      error: () => this.handleDeleteError(),
    });
  }

  private handleDeleteSuccess(response: any): void {
    if (response.isSuccess) {
      this.configService.showSuccessAlert("Utilisateur supprimé avec succès.");
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
}
