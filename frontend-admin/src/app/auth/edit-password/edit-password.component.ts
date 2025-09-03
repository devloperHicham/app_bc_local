import { Component, CUSTOM_ELEMENTS_SCHEMA, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { NgxSpinnerService } from 'ngx-spinner';
import { ActivatedRoute, Router } from '@angular/router';
import { SharedModule } from '../../share/shared.module';
import { ConfigService } from '../../services/config/config.service';
import { EditPasswordService } from '../services/edit-password.service';
import { ApiResponses } from '../../modules/api-responses';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-edit-password',
  standalone: true,
  imports: [SharedModule],
  templateUrl: './edit-password.component.html',
  styleUrl: './edit-password.component.scss',
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class EditPasswordComponent implements OnInit {
  form!: FormGroup;
  submitted = false;

  constructor(
    private readonly fb: FormBuilder,
    private readonly editPasswordService: EditPasswordService,
    private readonly configService: ConfigService,
    private readonly spinner: NgxSpinnerService,
    private readonly actRoute: ActivatedRoute,
    private readonly authService: AuthService,
    private readonly router: Router
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group(
      {
        oldPassword: new FormControl('', {
          nonNullable: true,
          validators: Validators.required,
        }),
        newPassword: new FormControl('', {
          nonNullable: true,
          validators: Validators.required,
        }),
        confirmPassword: new FormControl('', {
          nonNullable: true,
          validators: Validators.required,
        }),
      },
      { validators: this.passwordMatchValidator }
    );
  }

  passwordMatchValidator(formGroup: FormGroup) {
    const newPassword = formGroup.get('newPassword')?.value;
    const confirmPassword = formGroup.get('confirmPassword')?.value;

    return newPassword === confirmPassword ? null : { mismatch: true };
  }
  //@return response()
  get f() {
    return this.form.controls;
  }
  //set data to insert in database
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
    if (!id) {
      this.configService.showErrorAlert('ID non fourni');
      this.router.navigateByUrl(this.configService.ENDPOINTS.editPassword);
      return;
    }
    this.editPasswordService.update(id, this.form.value).subscribe({
      next: (res: ApiResponses<void>) => {
        this.spinner.hide();
        if (res.isSuccess) {
          this.configService.showSuccessAlert('Action réussie.');
          this.authService.logout();
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

  /**
   * Checks if the user is an admin or not.
   *
   * @returns {boolean} true if the user is an admin, false otherwise
   */
  isAdmin(): boolean {
    return this.authService.hasRole('ADMIN');
  }
}
