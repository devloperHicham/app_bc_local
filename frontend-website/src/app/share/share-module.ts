import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';

@NgModule({
  imports: [
    CommonModule,
    RouterOutlet
  ],
  exports: [
    CommonModule,
    RouterOutlet
  ],
})
export class SharedModule {}
