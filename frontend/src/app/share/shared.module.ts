import { NgModule } from '@angular/core';
import { NgxSpinnerModule } from 'ngx-spinner';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterOutlet } from '@angular/router';
import { DataTablesModule } from "angular-datatables";
import { NgSelectModule } from '@ng-select/ng-select';

@NgModule({
  imports: [
    ReactiveFormsModule,
    NgxSpinnerModule,
    CommonModule,
    RouterOutlet,
    DataTablesModule,
    NgSelectModule,
  ],
  exports: [
    ReactiveFormsModule,
    NgxSpinnerModule,
    CommonModule,
    RouterOutlet,
    DataTablesModule,
    NgSelectModule,
  ],
})
export class SharedModule {}
