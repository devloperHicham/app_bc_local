import { ChangeDetectorRef, Component } from '@angular/core';
import { TranslateModule, TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-manage-script',
  standalone: true,
  imports: [TranslateModule],
  templateUrl: './manage-script.html',
  styleUrl: './manage-script.css',
})
export class ManageScript {
  constructor(
    private readonly cdr: ChangeDetectorRef,
    private readonly translate: TranslateService
  ) {}
}
