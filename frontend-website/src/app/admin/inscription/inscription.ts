import { ChangeDetectorRef, Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { TranslateModule, TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-inscription',
  standalone: true,
  imports: [RouterLink, TranslateModule],
  templateUrl: './inscription.html',
  styleUrl: './inscription.css'
})
export class Inscription {
  constructor(private readonly cdr: ChangeDetectorRef, private readonly translate :TranslateService) {}
}
