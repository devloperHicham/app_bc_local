import { ChangeDetectorRef, Component } from '@angular/core';
import { TranslateModule, TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-conditions-dispute',
  standalone: true,
  imports: [TranslateModule],
  templateUrl: './conditions-dispute.html',
  styleUrl: './conditions-dispute.css'
})
export class ConditionsDispute {
  constructor(private readonly cdr: ChangeDetectorRef, private readonly translate :TranslateService) {}
}
