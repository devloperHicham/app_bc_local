import { ChangeDetectorRef, Component } from '@angular/core';
import { TranslateModule, TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-terms-service',
  standalone: true,
  imports: [TranslateModule],
  templateUrl: './terms-service.html',
  styleUrl: './terms-service.css'
})
export class TermsService {
  constructor(private readonly cdr: ChangeDetectorRef, private readonly translate :TranslateService) {}
}
