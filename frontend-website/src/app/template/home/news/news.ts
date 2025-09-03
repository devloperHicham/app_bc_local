import { ChangeDetectorRef, Component } from '@angular/core';
import { TranslateModule, TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-news',
  standalone: true,
  imports: [TranslateModule],
  templateUrl: './news.html',
  styleUrl: './news.css'
})
export class News {
  constructor(private readonly cdr: ChangeDetectorRef, private readonly translate :TranslateService) {}

}
