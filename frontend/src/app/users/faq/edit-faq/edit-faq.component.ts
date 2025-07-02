import { Component, CUSTOM_ELEMENTS_SCHEMA, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ConfigService } from '../../../services/config/config.service';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { FaqService } from '../../services/faq.service';
import { Faq } from '../../modules/faq';
import { ApiResponses } from '../../../modules/api-responses';
import { SharedModule } from '../../../share/shared.module';
import { AuthService } from '../../../auth/services/auth.service';
import { UserService } from '../../../admin/services/user.service';
import { User } from '../../../admin/modules/user';

@Component({
  selector: 'app-edit-faq',
  standalone: true,
  imports: [SharedModule],
  templateUrl: './edit-faq.component.html',
  styleUrl: './edit-faq.component.scss',
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class EditFaqComponent implements OnInit {
  users: User[] = [];
  form!: FormGroup;
  submitted = false;

  constructor(
    private readonly fb: FormBuilder,
    private readonly configService: ConfigService,
    private readonly userService: UserService,
    private readonly faqService: FaqService,
    private readonly actRoute: ActivatedRoute,
    private readonly router: Router,
    private readonly authService: AuthService,
    private readonly spinner: NgxSpinnerService
  ) {}

  ngOnInit(): void {
    this.getUsers();
    this.initializeForm();
    this.getData();
  }

  private initializeForm(): void {
    if (this.isAdmin()) {
      this.form = this.fb.group({
        userId: ['', Validators.required], // Add userId for admin
        typeFaq: ['', [Validators.required]],
        obs: [
          '',
          [
            Validators.required,
            Validators.pattern(
              "^([a-zA-ZêéèëàöÉÈÊÀÖËç0-9]{1}[a-zA-ZêéèëàöÉÈÊÀÖËç0-9.'°_:,()+; ]{10,255})$"
            ),
          ],
        ],
      });
    } else {
      this.form = this.fb.group({
        fullName: [
          '',
          [
            Validators.required,
            Validators.pattern('^([a-zA-ZêéèëàöÉÈÊÀÖËç\\ ]{8,45})$'),
          ],
        ],
        typeFaq: ['', [Validators.required]],
        obs: [
          '',
          [
            Validators.required,
            Validators.pattern(
              "^([a-zA-ZêéèëàöÉÈÊÀÖËç0-9]{1}[a-zA-ZêéèëàöÉÈÊÀÖËç0-9.'°_:,()+; ]{10,255})$"
            ),
          ],
        ],
      });
    }
  }

  //@return response()
  get f() {
    return this.form.controls;
  }

  getData() {
    const id = this.actRoute.snapshot.paramMap.get('id');
    if (!id) {
      this.configService.showErrorAlert('ID FAQ non fourni');
      this.router.navigateByUrl(this.configService.ENDPOINTS.faqs);
      return;
    }
    this.faqService.show(id).subscribe({
      next: (data: Faq | null) => {
        if (!data) {
          this.router.navigateByUrl(this.configService.ENDPOINTS.faqs);
          return;
        }
        this.form.patchValue({
          fullName: data.fullName,
          typeFaq: data.typeFaq,
          obs: data.obs,
        });
      },
      error: () => {
        this.configService.showErrorAlert(
          'Une erreur est survenue lors de la récupération des donnes.'
        );
      },
    });
  }

  //set data to insert in database
  submit(): void {
    this.submitted = true;
    if (this.form.invalid) {
      this.configService.showErrorAlert(
        'Veuillez corriger les erreurs dans le formulaire.'
      );
      return;
    }
    // Copier userId dans fullName si admin
    if (this.isAdmin()) {
      this.form.get('fullName')?.setValue(this.form.get('userId')?.value);
    }
    this.spinner.show(); /** spinner starts on init */
    const id = this.actRoute.snapshot.paramMap.get('id');
    if (!id) {
      this.configService.showErrorAlert('ID FAQ non fourni');
      this.router.navigateByUrl(this.configService.ENDPOINTS.faqs);
      return;
    }
    this.faqService.update(id, this.form.value).subscribe({
      next: (res: ApiResponses<void>) => {
        this.spinner.hide();
        if (res.isSuccess) {
          this.configService.showSuccessAlert('Action réussie.');
          this.router.navigateByUrl(this.configService.ENDPOINTS.faqs);
        } else {
          this.configService.showErrorAlert('Une erreur s’est produite.');
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

  // Method to get all users
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
    return this.authService.hasRole('ADMIN');
  }
}
