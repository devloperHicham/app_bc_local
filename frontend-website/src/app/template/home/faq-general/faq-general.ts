import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { TranslateModule, TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-faq-general',
  standalone: true,
  imports: [CommonModule, FormsModule, TranslateModule],
  templateUrl: './faq-general.html',
  styleUrl: './faq-general.css',
})
export class FaqGeneral {
  private opened: number | null = null;

  constructor(
    private readonly cdr: ChangeDetectorRef,
    private readonly translate: TranslateService
  ) {}

  toggle(id: number) {
    this.opened = this.opened === id ? null : id;
  }

  isOpen(id: number): boolean {
    return this.opened === id;
  }

  onKeyMenuItem(event: KeyboardEvent): void {
    if (event.key === 'Enter' || event.key === ' ') {
      event.preventDefault();
    }
  }
}
