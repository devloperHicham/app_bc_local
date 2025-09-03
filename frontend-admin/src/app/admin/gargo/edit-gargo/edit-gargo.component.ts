import { Component, CUSTOM_ELEMENTS_SCHEMA, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { ConfigService } from '../../../services/config/config.service';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { GargoService } from '../../services/gargo.service';
import { ApiResponses } from '../../../modules/api-responses';
import { SharedModule } from '../../../share/shared.module';
import { Gargo } from '../../modules/gargo';

@Component({
  selector: 'app-edit-gargo',
  standalone: true,
  imports: [SharedModule],
  templateUrl: './edit-gargo.component.html',
  styleUrl: './edit-gargo.component.scss',
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class EditGargoComponent implements OnInit {
  form!: FormGroup;
  submitted = false;

  constructor(
    private readonly fb: FormBuilder,
    private readonly configService: ConfigService,
    private readonly gargoService: GargoService,
    private readonly actRoute: ActivatedRoute,
    private readonly  router: Router,
    private readonly spinner: NgxSpinnerService
  ) {}

  ngOnInit(): void {
    this.getData(); //this for display data
    this.form = this.fb.group({
      gargoName: new FormControl('', [
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

  getData() {
    const id = this.actRoute.snapshot.paramMap.get('id');
    if (!id) {
      this.configService.showErrorAlert('ID non fourni');
      this.router.navigateByUrl(this.configService.ENDPOINTS.gargos);
      return;
    }
    this.gargoService.show(id).subscribe({
      next: (data: Gargo | null) => {
        if (!data) {
          this.router.navigateByUrl(this.configService.ENDPOINTS.gargos);
          return;
        }
        this.form.patchValue({
          gargoName: data.gargoName,
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

    this.spinner.show(); /** spinner starts on init */
    const id = this.actRoute.snapshot.paramMap.get('id');
    if (!id) {
      this.configService.showErrorAlert('ID non fourni');
      this.router.navigateByUrl(this.configService.ENDPOINTS.gargos);
      return;
    }
    this.gargoService.update(id, this.form.value).subscribe({
      next: (res: ApiResponses<void>) => {
        this.spinner.hide();
        if (res.isSuccess) {
          this.configService.showSuccessAlert('Action réussie.');
          this.router.navigateByUrl(this.configService.ENDPOINTS.gargos);
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
