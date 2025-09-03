import { ChangeDetectorRef, Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { TranslateModule, TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-no-price',
  standalone: true,
  imports: [RouterLink, TranslateModule],
  templateUrl: './no-price.html',
  styleUrl: './no-price.css'
})
export class NoPrice {
  constructor(private readonly cdr: ChangeDetectorRef, private readonly translate :TranslateService) {}
}
