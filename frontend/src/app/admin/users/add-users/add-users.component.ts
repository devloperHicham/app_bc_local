import { Component, CUSTOM_ELEMENTS_SCHEMA, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import { SharedModule } from '../../../share/shared.module';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgxSpinnerService } from 'ngx-spinner';
import { ConfigService } from '../../../services/config/config.service';
import { Router } from '@angular/router';
import { ApiResponses } from '../../../modules/api-responses';

@Component({
  selector: 'app-add-users',
  standalone: true,
  imports: [SharedModule],
  templateUrl: './add-users.component.html',
  styleUrl: './add-users.component.scss',
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class AddUsersComponent implements OnInit {
  form!: FormGroup;
  submitted = false;

  constructor(
    private readonly fb: FormBuilder,
    private readonly userService: UserService,
    private readonly configService: ConfigService,
    private readonly spinner: NgxSpinnerService,
    private readonly router: Router
  ) {}

  ngOnInit(): void {
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
        [Validators.required, Validators.pattern('^(0{1}[56789]{1}[0-9]{8})$')],
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
      password: ['bc12345@', [Validators.required]],
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
  //set data to insert in database
  submit(): void {
    this.submitted = true;
    if (this.form.invalid) {
      this.configService.showErrorAlert(
        'Veuillez corriger les erreurs dans le formulaire.'
      );
      return;
    }
    this.spinner.show();
    this.userService.create(this.form.value).subscribe({
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
