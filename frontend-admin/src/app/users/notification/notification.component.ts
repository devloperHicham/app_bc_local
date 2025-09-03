import {
  Component,
  CUSTOM_ELEMENTS_SCHEMA,
  OnInit,
} from '@angular/core';
import { Config } from 'datatables.net';
import { SharedModule } from '../../share/shared.module';
import { Faq } from '../modules/faq';
import { NotificationService } from '../services/notification.service';

@Component({
  selector: 'app-notification',
  standalone: true,
  imports: [SharedModule],
  templateUrl: './notification.component.html',
  styleUrl: './notification.component.scss',
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class NotificationComponent implements OnInit {
  dtOptions: Config = {};
  faqs: Faq[] = [];

  constructor(
    private readonly notificationService: NotificationService
  ) {}

  ngOnInit(): void {
    this.initializeDataTable();
  }

  private initializeDataTable(): void {
    this.dtOptions = {
      serverSide: true,
      processing: true,
      pagingType: 'full_numbers',
      ajax: this.handleAjaxRequest.bind(this),
      lengthMenu: [25, 50, 100, 150],
      columns: this.getTableColumns(),
    };
  }

  private handleAjaxRequest(dataTablesParameters: any, callback: any): void {
    this.notificationService
      .getPaginatedData(dataTablesParameters)
      .subscribe(callback);
  }

  private getTableColumns(): any[] {
    return [
      { data: 'fullName', title: 'Nom et Prénom', searchable: true },
      { data: 'typeFaq', title: 'Type FAQ', searchable: true },
      { data: 'obs', title: 'Observations', searchable: true },
    ];
  }
}
