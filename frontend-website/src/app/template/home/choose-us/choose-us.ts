import { ChangeDetectorRef, Component } from '@angular/core';
import { TranslateModule, TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-choose-us',
  standalone: true,
  imports: [TranslateModule],
  templateUrl: './choose-us.html',
  styleUrl: './choose-us.css'
})
export class ChooseUs {
  constructor(private readonly cdr: ChangeDetectorRef, private readonly translate :TranslateService) {}

}
