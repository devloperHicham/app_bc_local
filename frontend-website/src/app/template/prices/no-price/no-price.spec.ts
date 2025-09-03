import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NoPrice } from './no-price';

describe('NoPrice', () => {
  let component: NoPrice;
  let fixture: ComponentFixture<NoPrice>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NoPrice]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NoPrice);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
