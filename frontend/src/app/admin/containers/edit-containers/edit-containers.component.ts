import { Component, CUSTOM_ELEMENTS_SCHEMA, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { ConfigService } from '../../../services/config/config.service';
import { ActivatedRoute, Router} from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ContainerService } from '../../services/container.service';
import { Container } from '../../modules/container';
import { ApiResponses } from '../../../modules/api-responses';
import { SharedModule } from '../../../share/shared.module';

@Component({
  selector: 'app-edit-containers',
  standalone: true,
  imports: [SharedModule],
  templateUrl: './edit-containers.component.html',
  styleUrl: './edit-containers.component.scss',
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class EditContainersComponent implements OnInit {
  form!: FormGroup;
  submitted = false;

  constructor(
    private readonly fb: FormBuilder,
    private readonly configService: ConfigService,
    private readonly containerService: ContainerService,
    private readonly actRoute: ActivatedRoute,
    private readonly router: Router,
    private readonly spinner: NgxSpinnerService
  ) {}

  ngOnInit(): void {
    this.getData(); //this for display data
    this.form = this.fb.group({
      containerName: new FormControl('', [
        Validators.required
      ]),
      containerWeight: [
        '',
        [Validators.required],
      ],
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
      this.router.navigateByUrl(this.configService.ENDPOINTS.containers);
      return;
    }
    this.containerService.show(id).subscribe({
      next: (data: Container | null) => {
        if (!data) {
          this.router.navigateByUrl(this.configService.ENDPOINTS.containers);
          return;
        }
        this.form.patchValue({
          containerName: data.containerName,
          containerWeight: data.containerWeight,
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
      this.router.navigateByUrl(this.configService.ENDPOINTS.containers);
      return;
    }
    this.containerService.update(id, this.form.value).subscribe({
      next: (res: ApiResponses<void>) => {
        this.spinner.hide();
        if (res.isSuccess) {
          this.configService.showSuccessAlert('Action réussie.');
          this.router.navigateByUrl(this.configService.ENDPOINTS.containers);
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
