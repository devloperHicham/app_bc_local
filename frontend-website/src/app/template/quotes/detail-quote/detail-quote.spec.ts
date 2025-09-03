import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetailQuote } from './detail-quote';

describe('DetailQuote', () => {
  let component: DetailQuote;
  let fixture: ComponentFixture<DetailQuote>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DetailQuote]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DetailQuote);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
