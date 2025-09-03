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
import { PortService } from '../../services/port.service';

@Component({
  selector: 'app-add-port',
  standalone: true,
  imports: [SharedModule],
  templateUrl: './add-port.component.html',
  styleUrl: './add-port.component.scss',
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class AddPortComponent implements OnInit {
  form!: FormGroup;
  submitted = false;
  selectedLogo: File | null = null;

  constructor(
    private readonly fb: FormBuilder,
    private readonly portService: PortService,
    private readonly configService: ConfigService,
    private readonly spinner: NgxSpinnerService,
    private readonly router: Router
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      portName: new FormControl('', [
        Validators.required
      ]),
      countryName: new FormControl('', [
        Validators.required
      ]),
      countryNameAbbreviation: new FormControl('', [
        Validators.required
      ]),
      portLogo: new FormControl('', [Validators.required]),
      portCode: new FormControl('', [
        Validators.required
      ]),
      portLongitude: new FormControl('', [
        Validators.required
      ]),
      portLatitude: new FormControl('', [
        Validators.required
      ]),
      obs: new FormControl('', [
        Validators.pattern(
          "^([a-zA-ZêéèëàöÉÈÊÀÖËç0-9]{1}[a-zA-ZêéèëàöÉÈÊÀÖËç0-9.'°_:,()+; ]{1,255})$"
        ),
      ]),
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

    // Append form data to FormData object
    const formData = new FormData();
    formData.append('portName', this.form.value.portName);
    formData.append('countryName', this.form.value.countryName);
    formData.append('countryNameAbbreviation', this.form.value.countryNameAbbreviation);
    formData.append('portCode', this.form.value.portCode);
    formData.append('portLongitude', this.form.value.portLongitude);
    formData.append('portLatitude', this.form.value.portLatitude);
    formData.append('obs', this.form.value.obs);
    if (this.selectedLogo) {
      // ✅ Vérification avant d'ajouter le fichier
      formData.append('portLogo', this.selectedLogo);
    }

    this.portService.create(formData).subscribe({
      next: (res: ApiResponses<void>) => {
        this.spinner.hide();
        if (res.isSuccess) {
          this.configService.showSuccessAlert('Action réussie.');
          this.router.navigateByUrl(this.configService.ENDPOINTS.ports);
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
      this.selectedLogo = input.files[0];
    }
  }
}
