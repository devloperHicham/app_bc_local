import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { TranslateModule, TranslateService } from '@ngx-translate/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { ConfigService } from '../../services/config/config';
import { ApiResponses } from '../../modules/api-responses';
import { UserService } from '../service/user-service';
import { SharedModule } from '../../share/share-module';

@Component({
  selector: 'app-inscription',
  standalone: true,
  imports: [SharedModule, RouterLink, TranslateModule],
  templateUrl: './inscription.html',
  styleUrl: './inscription.css',
})
export class Inscription implements OnInit {
  form!: FormGroup;
  submitted = false;
  showPassword: boolean = false;

  constructor(
    private readonly fb: FormBuilder,
    private readonly userService: UserService,
    private readonly configService: ConfigService,
    private readonly spinner: NgxSpinnerService,
    private readonly router: Router,
    private readonly cdr: ChangeDetectorRef,
    private readonly translate: TranslateService
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      firstName: ['', [Validators.required]],
      lastName: ['', [Validators.required]],
      phoneNumber: ['', [Validators.required]],
      email: [
        '',
        [
          Validators.required,
          Validators.pattern(
            '^[a-zA-ZêéèëàöÉÈÊÀÖË0-9\\-_.]+@[a-zA-ZêéèëàöÉÈÊÀÖË0-9\\-_.]+\\.[a-z]{2,3}$'
          ),
        ],
      ],
      role: ['CLIENT', [Validators.required]],
      password: ['', [Validators.required]],
      confirmePassword: ['', [Validators.required]],
    });
  }

  //@return response()
  get f() {
    return this.form.controls;
  }

  togglePasswordVisibility() {
    this.showPassword = !this.showPassword;
  }

  onKeyMenuItem(event: KeyboardEvent): void {
    if (event.key === 'Enter' || event.key === ' ') {
      event.preventDefault();
    }
  }
  //set data to insert in database
  submit(): void {
    this.submitted = true;
    console.log(this.form.value);
    if (this.form.invalid) {
      this.configService.showErrorAlert(
        'Veuillez corriger les erreurs dans le formulaire.'
      );
      return;
    }

    if (this.form.value.password != this.form.value.confirmePassword) {
      this.configService.showErrorAlert(
        'Veuillez corriger les erreurs de password.'
      );
      return;
    }
    this.spinner.show();
    this.userService.create(this.form.value).subscribe({
      next: (res: ApiResponses<void>) => {
        this.spinner.hide();
        if (res.isSuccess) {
          this.configService.showSuccessAlert('Action réussie.');
          this.router.navigateByUrl(this.configService.ENDPOINTS.login);
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
