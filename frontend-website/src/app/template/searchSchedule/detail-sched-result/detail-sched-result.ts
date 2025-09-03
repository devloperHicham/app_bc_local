import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-detail-sched-result',
  imports: [],
  templateUrl: './detail-sched-result.html',
  styleUrl: './detail-sched-result.css'
})
export class DetailSchedResult {
 @Output() closeDetail = new EventEmitter<void>();

  hideDetail() {
    this.closeDetail.emit();
  }
}
