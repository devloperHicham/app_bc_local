import { Component, CUSTOM_ELEMENTS_SCHEMA, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  Validators,
} from '@angular/forms';
import { ConfigService } from '../../../services/config/config.service';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { CompaniesService } from '../../services/companies.service';
import { Company } from '../../modules/company';
import { ApiResponses } from '../../../modules/api-responses';
import { SharedModule } from '../../../share/shared.module';

@Component({
  selector: 'app-edit-companies',
  standalone: true,
  imports: [SharedModule],
  templateUrl: './edit-companies.component.html',
  styleUrl: './edit-companies.component.scss',
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class EditCompaniesComponent implements OnInit {
  form!: FormGroup;
  submitted = false;
  selectedImage: File | null = null;

  constructor(
    private readonly fb: FormBuilder,
    private readonly configService: ConfigService,
    private readonly companiesService: CompaniesService,
    private readonly actRoute: ActivatedRoute,
    private readonly router: Router,
    private readonly spinner: NgxSpinnerService
  ) {}

  ngOnInit(): void {
    this.getData(); //this for display data
    this.form = this.fb.group({
      companyName: [
        '',
        [
          Validators.required
        ],
      ],
      companyLogo: [''],
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
      this.configService.showErrorAlert('ID company non fourni');
      this.router.navigateByUrl(this.configService.ENDPOINTS.companies);
      return;
    }
    this.companiesService.show(id).subscribe({
      next: (data: Company | null) => {
        if (!data) {
          this.router.navigateByUrl(this.configService.ENDPOINTS.companies);
          return;
        }
        this.form.patchValue({
          companyName: data.companyName,
          companyLogo: data.companyLogo,
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
      this.configService.showErrorAlert('ID company non fourni');
      this.router.navigateByUrl(this.configService.ENDPOINTS.companies);
      return;
    }
    // Append form data to FormData object
     const formData = new FormData();
    formData.append('companyName', this.form.value.companyName);
    formData.append('obs', this.form.value.obs);

    if (this.selectedImage) {
      formData.append('companyLogo', this.selectedImage);
    }
    this.companiesService.update(id, formData).subscribe({
      next: (res: ApiResponses<void>) => {
        this.spinner.hide();
        if (res.isSuccess) {
          this.configService.showSuccessAlert('Action réussie.');
          this.router.navigateByUrl(this.configService.ENDPOINTS.companies);
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

  // Method to handle file change event
  onFileChange(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.selectedImage = input.files[0];
    }
  }
}
