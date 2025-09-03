import { ChangeDetectorRef, Component } from '@angular/core';
import { TranslateModule, TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-profil',
  standalone: true,
  imports: [TranslateModule],
  templateUrl: './profil.html',
  styleUrl: './profil.css'
})
export class Profil {
  constructor(private readonly cdr: ChangeDetectorRef, private readonly translate :TranslateService) {}
}
