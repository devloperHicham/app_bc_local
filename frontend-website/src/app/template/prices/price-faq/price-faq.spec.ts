import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PriceFaq } from './price-faq';

describe('PriceFaq', () => {
  let component: PriceFaq;
  let fixture: ComponentFixture<PriceFaq>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PriceFaq]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PriceFaq);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
