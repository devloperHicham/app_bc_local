import { ChangeDetectorRef, Component } from '@angular/core';
import { TranslateModule, TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-maps-about-us',
  standalone: true,
  imports: [TranslateModule],
  templateUrl: './maps-about-us.html',
  styleUrl: './maps-about-us.css'
})
export class MapsAboutUs {
    constructor(private readonly cdr: ChangeDetectorRef, private readonly translate :TranslateService) {}
}
