import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { RouterOutlet } from "@angular/router";
import { ReactiveFormsModule } from "@angular/forms";
import { NgSelectModule } from "@ng-select/ng-select";
import { NgxSpinnerModule } from "ngx-spinner";

@NgModule({
  imports: [
    ReactiveFormsModule,
    NgxSpinnerModule,
    CommonModule,
    RouterOutlet,
    NgSelectModule,
  ],
  exports: [
    ReactiveFormsModule,
    NgxSpinnerModule,
    CommonModule,
    RouterOutlet,
    NgSelectModule,
  ],
})
export class SharedModule {}
