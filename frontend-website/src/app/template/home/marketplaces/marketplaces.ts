import { ChangeDetectorRef, Component } from '@angular/core';
import { TranslateModule, TranslateService } from '@ngx-translate/core';
@Component({
  selector: 'app-marketplaces',
  standalone: true,
  imports: [TranslateModule],
  templateUrl: './marketplaces.html',
  styleUrl: './marketplaces.css',
})
export class Marketplaces {
  
    constructor(private readonly cdr: ChangeDetectorRef, private readonly translate :TranslateService) {}
}
