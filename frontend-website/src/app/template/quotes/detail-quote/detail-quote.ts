import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-detail-quote',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './detail-quote.html',
  styleUrl: './detail-quote.css'
})
export class DetailQuote {
 @Output() closeEvent = new EventEmitter<void>();

  close() {
    this.closeEvent.emit();
  }

}
