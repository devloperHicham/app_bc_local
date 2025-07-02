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
import { ApiResponses } from '../../../modules/api-responses';
import { SharedModule } from '../../../share/shared.module';
import { PortService } from '../../services/port.service';
import { Port } from '../../modules/port';

@Component({
  selector: 'app-edit-port',
  standalone: true,
  imports: [SharedModule],
  templateUrl: './edit-port.component.html',
  styleUrl: './edit-port.component.scss',
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class EditPortComponent implements OnInit {
  form!: FormGroup;
  submitted = false;
  selectedLogo: File | null = null;

  constructor(
    private readonly fb: FormBuilder,
    private readonly configService: ConfigService,
    private readonly portService: PortService,
    private readonly actRoute: ActivatedRoute,
    private readonly router: Router,
    private readonly spinner: NgxSpinnerService
  ) {}

  ngOnInit(): void {
    this.getData(); //this for display data
    this.form = this.fb.group({
      portName: new FormControl('', [
        Validators.required
      ]),
      countryName: new FormControl('', [
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

  getData() {
    const id = this.actRoute.snapshot.paramMap.get('id');
    if (!id) {
      this.configService.showErrorAlert('ID non fourni');
      this.router.navigateByUrl(this.configService.ENDPOINTS.ports);
      return;
    }
    this.portService.show(id).subscribe({
      next: (data: Port | null) => {
        if (!data) {
          this.router.navigateByUrl(this.configService.ENDPOINTS.ports);
          return;
        }
        this.form.patchValue({
          portName: data.portName,
          countryName: data.countryName,
          portCode: data.portCode,
          portLongitude: data.portLongitude,
          portLatitude: data.portLatitude,
          portLogo: data.portLogo,
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

    // Append form data to FormData object
    const formData = new FormData();
    formData.append('portName', this.form.value.portName);
    formData.append('countryName', this.form.value.countryName);
    formData.append('portCode', this.form.value.portCode);
    formData.append('portLongitude', this.form.value.portLongitude);
    formData.append('portLatitude', this.form.value.portLatitude);
    formData.append('obs', this.form.value.obs);
    if (this.selectedLogo) {
      // ✅ Vérification avant d'ajouter le fichier
      formData.append('portLogo', this.selectedLogo);
    }

    const id = this.actRoute.snapshot.paramMap.get('id');
    if (!id) {
      this.configService.showErrorAlert('ID non fourni');
      this.router.navigateByUrl(this.configService.ENDPOINTS.ports);
      return;
    }
    this.portService.update(id, formData).subscribe({
      next: (res: ApiResponses<void>) => {
        this.spinner.hide();
        if (res.isSuccess) {
          this.configService.showSuccessAlert('Action réussie.');
          this.router.navigateByUrl(this.configService.ENDPOINTS.ports);
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
      this.selectedLogo = input.files[0];
    }
  }
}
