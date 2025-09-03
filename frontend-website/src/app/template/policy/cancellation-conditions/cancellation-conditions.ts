import { ChangeDetectorRef, Component } from '@angular/core';
import { TranslateModule, TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-cancellation-conditions',
  standalone: true,
  imports: [TranslateModule],
  templateUrl: './cancellation-conditions.html',
  styleUrl: './cancellation-conditions.css'
})
export class CancellationConditions {
  constructor(private readonly cdr: ChangeDetectorRef, private readonly translate :TranslateService) {}
}
