import {
  Component,
  CUSTOM_ELEMENTS_SCHEMA,
  OnDestroy,
  OnInit,
} from "@angular/core";
import { FaqService } from "../../services/faq.service";
import { SharedModule } from "../../../share/shared.module";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { NgxSpinnerService } from "ngx-spinner";
import { ConfigService } from "../../../services/config/config.service";
import { ApiResponses } from "../../../modules/api-responses";
import { Router } from "@angular/router";
import { AuthService } from "../../../auth/services/auth.service";
import { UserService } from "../../../admin/services/user.service";
import { User } from "../../../admin/modules/user";
import { Editor, NgxEditorModule } from "ngx-editor";

@Component({
  selector: "app-add-faq",
  standalone: true,
  imports: [SharedModule, NgxEditorModule],
  templateUrl: "./add-faq.component.html",
  styleUrl: "./add-faq.component.scss",
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class AddFaqComponent implements OnInit, OnDestroy {
  users: User[] = [];
  form!: FormGroup;
  submitted = false;
  editor!: Editor;

  constructor(
    private readonly fb: FormBuilder,
    private readonly faqService: FaqService,
    private readonly userService: UserService,
    private readonly configService: ConfigService,
    private readonly spinner: NgxSpinnerService,
    private readonly authService: AuthService,
    private readonly router: Router
  ) {}

  ngOnInit(): void {
    this.editor = new Editor();
    this.getUsers();
    this.initializeForm();
  }

  ngOnDestroy(): void {
    this.editor.destroy();
  }
  private initializeForm(): void {
    this.form = this.fb.group({
      fullName: ["", [Validators.required]],
      typeFaq: ["", [Validators.required]],
      obs: ["", [Validators.required]],
    });
  }
  //@return response()
  get f() {
    return this.form.controls;
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
    this.faqService.create(this.form.value).subscribe({
      next: (res: ApiResponses<void>) => {
        this.spinner.hide();
        if (res.isSuccess) {
          this.configService.showSuccessAlert("Action réussie.");
          this.router.navigateByUrl(this.configService.ENDPOINTS.faqs);
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
          "Une erreur est survenue lors de la récupération des data."
        );
      },
    });
  }

  // Get current user observable
  get currentUser$() {
    return this.authService.currentUser$;
  }
  /**
   * Checks if the user is an admin or not.
   *
   * @returns {boolean} true if the user is an admin, false otherwise
   */
  isAdmin(): boolean {
    return this.authService.hasRole("ADMIN");
  }
}
