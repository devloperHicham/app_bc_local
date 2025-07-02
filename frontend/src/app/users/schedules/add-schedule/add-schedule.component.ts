import { Component, CUSTOM_ELEMENTS_SCHEMA, OnInit } from '@angular/core';
import { ScheduleService } from '../../services/schedule.service';
import { SharedModule } from '../../../share/shared.module';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgxSpinnerService } from 'ngx-spinner';
import { ConfigService } from '../../../services/config/config.service';
import { ApiResponses } from '../../../modules/api-responses';
import { Router } from '@angular/router';
import { PastSchedule } from '../../modules/past-schedule';
import { CompaniesService } from '../../../admin/services/companies.service';
import { PortService } from '../../../admin/services/port.service';
import { Port } from '../../../admin/modules/port';
import { Company } from '../../../admin/modules/company';

@Component({
  selector: 'app-add-schedule',
  standalone: true,
  imports: [SharedModule],
  templateUrl: './add-schedule.component.html',
  styleUrl: './add-schedule.component.scss',
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class AddScheduleComponent implements OnInit {
  form!: FormGroup;
  results: PastSchedule[] = [];
  ports: Port[] = [];
  companies: Company[] = [];
  submitted = false;

  constructor(
    private readonly fb: FormBuilder,
    private readonly scheduleService: ScheduleService,
    private readonly companiesService: CompaniesService,
    private readonly portService: PortService,
    private readonly configService: ConfigService,
    private readonly spinner: NgxSpinnerService,
    private readonly router: Router
  ) {}

  ngOnInit(): void {
    this.getCompanies();
    this.getPorts();
    this.form = this.fb.group({
      portFromId: ['', [Validators.required]],
      portToId: ['', [Validators.required]],
      companyId: ['', [Validators.required]],
      dateDepart: this.fb.array([]),
      dateArrive: this.fb.array([]),
      transit: this.fb.array([]),
      vessel: this.fb.array([]),
      refVoyage: this.fb.array([]),
      serviceName: this.fb.array([]),
    });
  }

  // Helper methods to access FormArrays
  get dateDepartArray(): FormArray {
    return this.form.get('dateDepart') as FormArray;
  }

  get dateArriveArray(): FormArray {
    return this.form.get('dateArrive') as FormArray;
  }
  get transitArray(): FormArray {
    return this.form.get('transit') as FormArray;
  }

  get vesselArray(): FormArray {
    return this.form.get('vessel') as FormArray;
  }

  get refVoyageArray(): FormArray {
    return this.form.get('refVoyage') as FormArray;
  }

  get serviceNameArray(): FormArray {
    return this.form.get('serviceName') as FormArray;
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
    console.log(this.form.value);
    this.scheduleService.create(this.form.value).subscribe({
      next: (res: ApiResponses<void>) => {
        this.spinner.hide();
        if (res.isSuccess) {
          this.configService.showSuccessAlert('Action réussie.');
          this.router.navigateByUrl(this.configService.ENDPOINTS.schedules);
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
  // Method to get all ports
  getPorts(): void {
    this.portService.getAll().subscribe({
      next: (data: Port[] | null) => {
        this.ports = data || [];
      },
      error: () => {
        this.configService.showErrorAlert(
          'Une erreur est survenue lors de la récupération des data.'
        );
      },
    });
  }

  // Method to get all companies
  getCompanies(): void {
    this.companiesService.getAll().subscribe({
      next: (data: Company[] | null) => {
        this.companies = data || [];
      },
      error: () => {
        this.configService.showErrorAlert(
          'Une erreur est survenue lors de la récupération des data.'
        );
      },
    });
  }
  /**
   * Pastes the contents of the clipboard into the table, expecting a tab-separated
   * text. The format should be:
   * <dateDepart>\t<dateArrive>\t<transit>\t<vessel>\t<refVoyage>\t<serviceName>
   * If the clipboard is empty, unsupported, or access is denied, an error is logged
   * to the console.
   */
  async pasteFromClipboard() {
    try {
      const text = await navigator.clipboard.readText();
      const rows = text.trim().split('\n');

      this.results = rows.map((line) => {
        const columns = line.split('\t');
        const dateDepart = columns[0]?.trim() || '';
        const dateArrive = columns[1]?.trim() || '';
        const transit = parseInt(columns[2]?.trim() || '0', 10);
        const vessel = columns[3]?.trim() || '';
        const refVoyage = columns[4]?.trim() || '';
        const serviceName = columns[5]?.trim() || '';

        // Push into form arrays
        this.dateDepartArray.push(
          this.fb.control(dateDepart, Validators.required)
        );
        this.dateArriveArray.push(
          this.fb.control(dateArrive, Validators.required)
        );
        this.transitArray.push(this.fb.control(transit, Validators.required));
        this.vesselArray.push(this.fb.control(vessel, Validators.required));
        this.refVoyageArray.push(
          this.fb.control(refVoyage, Validators.required)
        );
        this.serviceNameArray.push(
          this.fb.control(serviceName, Validators.required)
        );

        return {
          dateDepart,
          dateArrive,
          transit,
          vessel,
          refVoyage,
          serviceName,
        } as PastSchedule;
      });
    } catch (err) {
      console.error('Clipboard access denied or not supported.', err);
    }
  }

  clearTable() {
    this.results = [];
    this.dateDepartArray.clear();
    this.dateArriveArray.clear();
    this.transitArray.clear();
    this.vesselArray.clear();
    this.refVoyageArray.clear();
    this.serviceNameArray.clear();
  }
}
