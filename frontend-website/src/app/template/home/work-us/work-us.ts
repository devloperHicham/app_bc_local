import { ChangeDetectorRef, Component } from '@angular/core';
import { TranslateModule, TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-work-us',
  standalone: true,
  imports: [TranslateModule],
  templateUrl: './work-us.html',
  styleUrl: './work-us.css',
})
export class WorkUs {
  constructor(
    private readonly cdr: ChangeDetectorRef,
    private readonly translate: TranslateService
  ) {}
}
