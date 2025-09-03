import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
  imports: [
    ReactiveFormsModule,
    CommonModule,
    RouterOutlet
  ],
  exports: [
    ReactiveFormsModule,
    CommonModule,
    RouterOutlet
  ],
})
export class SharedModule {}
