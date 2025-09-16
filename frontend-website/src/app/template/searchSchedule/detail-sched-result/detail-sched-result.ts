import { Component, EventEmitter, inject, Input, Output } from '@angular/core';
import { Schedule } from '../../home/modules/schedule';
import { ConfigService } from '../../../services/config/config';
import { HeroService } from '../../home/services/hero-service';

@Component({
  selector: 'app-detail-sched-result',
  imports: [],
  templateUrl: './detail-sched-result.html',
  styleUrl: './detail-sched-result.css',
})
export class DetailSchedResult {
  @Input() schedules!: Schedule; // this for pass data from parent
  @Output() closeDetail = new EventEmitter<void>();
  private readonly configService = inject(ConfigService);
  private readonly heroService = inject(HeroService);

  hideDetail() {
    this.closeDetail.emit();
  }

  togglePast(id: string): void {
    this.heroService.togglePastSchedule(id).subscribe({
      next: (response) =>
        this.configService.showSuccessAlert('Successfully registered.'),
      error: () => console.log('error past schedule'),
    });
  }

  toggleFavorite(id: string): void {
    this.heroService.toggleFavoriteSchedule(id).subscribe({
      next: (response) =>
        this.configService.showSuccessAlert('Successfully registered.'),
      error: () => console.log('error favorite schedule'),
    });
  }
}
