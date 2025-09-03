import { Component, CUSTOM_ELEMENTS_SCHEMA, OnInit } from '@angular/core';
import { CompaniesService } from '../../services/companies.service';
import { SharedModule } from '../../../share/shared.module';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgxSpinnerService } from 'ngx-spinner';
import { ConfigService } from '../../../services/config/config.service';
import { ApiResponses } from '../../../modules/api-responses';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-companies',
  standalone: true,
  imports: [SharedModule],
  templateUrl: './add-companies.component.html',
  styleUrl: './add-companies.component.scss',
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class AddCompaniesComponent implements OnInit {
  form!: FormGroup;
  submitted = false;
  selectedImage: File | null = null;

  constructor(
    private readonly fb: FormBuilder,
    private readonly companiesService: CompaniesService,
    private readonly configService: ConfigService,
    private readonly spinner: NgxSpinnerService,
    private readonly router: Router
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      companyName: [
        '',
        [
          Validators.required
        ],
      ],
      companyLogo: ['', [Validators.required]],
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
     const formData = new FormData();

    // Append form data to FormData object
    formData.append('companyName', this.form.value.companyName);
    formData.append('obs', this.form.value.obs);

    if (this.selectedImage) {
      formData.append('companyLogo', this.selectedImage);
    }
    this.companiesService.create(formData).subscribe({
      next: (res: ApiResponses<void>) => {
        this.spinner.hide();
        if (res.isSuccess) {
          this.configService.showSuccessAlert('Action réussie.');
          this.router.navigateByUrl(this.configService.ENDPOINTS.companies);
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

    // Method to handle file change event
  onFileChange(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.selectedImage = input.files[0];
    }
  }
}