import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PaymentConditions } from './payment-conditions';

describe('PaymentConditions', () => {
  let component: PaymentConditions;
  let fixture: ComponentFixture<PaymentConditions>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PaymentConditions]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PaymentConditions);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
