import { Component, CUSTOM_ELEMENTS_SCHEMA, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ConfigService } from '../../../services/config/config.service';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { UserService } from '../../services/user.service';
import { ApiResponses } from '../../../modules/api-responses';
import { SharedModule } from '../../../share/shared.module';
import { User } from '../../modules/user';

@Component({
  selector: 'app-edit-users',
  standalone: true,
  imports: [SharedModule],
  templateUrl: './edit-users.component.html',
  styleUrl: './edit-users.component.scss',
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class EditUsersComponent implements OnInit {
  form!: FormGroup;
  submitted = false;

  constructor(
    private readonly fb: FormBuilder,
    private readonly configService: ConfigService,
    private readonly userService: UserService,
    private readonly actRoute: ActivatedRoute,
    private readonly router: Router,
    private readonly spinner: NgxSpinnerService
  ) {}

  ngOnInit(): void {
    this.getData(); //this for display data
    this.form = this.fb.group({
      firstName: [
        '',
        [
          Validators.required
        ],
      ],
      lastName: [
        '',
        [
          Validators.required
        ],
      ],
      phoneNumber: [
        '',
        [Validators.required, Validators.pattern('^(0{1}[5678]{1}[0-9]{8})$')],
      ],
      email: [
        '',
        [
          Validators.required,
          Validators.pattern(
            '^[a-zA-ZêéèëàöÉÈÊÀÖË0-9\\-_.]+@[a-zA-ZêéèëàöÉÈÊÀÖË0-9\\-_.]+\\.[a-z]{2,3}$'
          ),
        ],
      ],
      role: ['', [Validators.required]],
      obs: [
        '',
        [
          Validators.pattern(
            "^([a-zA-ZêéèëàöÉÈÊÀÖËç0-9]{1}[a-zA-ZêéèëàöÉÈÊÀÖËç0-9.'°_:,()+; ]{1,255})$"
          ),
        ],
      ],
    });
  }

  //@return response()
  get f() {
    return this.form.controls;
  }

  getData(): void {
    const id = this.actRoute.snapshot.paramMap.get('id');
    // Handle null case first
    if (!id) {
      this.configService.showErrorAlert('ID utilisateur non fourni');
      this.router.navigateByUrl(this.configService.ENDPOINTS.users);
      return;
    }

    this.userService.show(id).subscribe({
      next: (data: User | null) => {
        if (!data) {
          this.configService.showErrorAlert('Utilisateur non trouvé');
          this.router.navigateByUrl(this.configService.ENDPOINTS.users);
          return;
        }

        this.form.patchValue({
          firstName: data.firstName,
          lastName: data.lastName,
          email: data.email,
          phoneNumber: data.phoneNumber,
          role: data.userType,
          obs: data.obs,
        });
      },
      error: () => {
        this.configService.showErrorAlert(
          'Une erreur est survenue lors de la récupération des données.'
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

    this.spinner.show(); /** spinner starts on init */
    const id = this.actRoute.snapshot.paramMap.get('id');
    // Handle null case first
    if (!id) {
      this.configService.showErrorAlert('ID utilisateur non fourni');
      this.router.navigateByUrl(this.configService.ENDPOINTS.users);
      return;
    }
    this.userService.update(id, this.form.value).subscribe({
      next: (res: ApiResponses<void>) => {
        this.spinner.hide();
        if (res.isSuccess) {
          this.configService.showSuccessAlert('Action réussie.');
          this.router.navigateByUrl(this.configService.ENDPOINTS.users);
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
}
