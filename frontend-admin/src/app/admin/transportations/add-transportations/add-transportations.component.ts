import { Component, CUSTOM_ELEMENTS_SCHEMA, OnInit } from '@angular/core';
import { SharedModule } from '../../../share/shared.module';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { NgxSpinnerService } from 'ngx-spinner';
import { ConfigService } from '../../../services/config/config.service';
import { ApiResponses } from '../../../modules/api-responses';
import { Router } from '@angular/router';
import { TransportationService } from '../../services/transportation.service';

@Component({
  selector: 'app-add-transportations',
  standalone: true,
  imports: [SharedModule],
  templateUrl: './add-transportations.component.html',
  styleUrl: './add-transportations.component.scss',
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class AddTransportationsComponent implements OnInit {
  form!: FormGroup;
  submitted = false;

  constructor(
    private readonly fb: FormBuilder,
    private readonly transportationService: TransportationService,
    private readonly configService:ConfigService,
    private readonly spinner: NgxSpinnerService,
    private readonly router: Router,
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      transportationName: new FormControl('', [
        Validators.required
      ]),
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
    this.transportationService.create(this.form.value).subscribe({
      next: (res: ApiResponses<void>) => {
        this.spinner.hide();
        if (res.isSuccess) {
          this.configService.showSuccessAlert('Action réussie.');
          this.router.navigateByUrl(this.configService.ENDPOINTS.transportations);
        } else {
          this.configService.showErrorAlert('Une erreur s’est produite.'
          );
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
