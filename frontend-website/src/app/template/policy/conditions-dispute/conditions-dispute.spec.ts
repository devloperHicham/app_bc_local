import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConditionsDispute } from './conditions-dispute';

describe('ConditionsDispute', () => {
  let component: ConditionsDispute;
  let fixture: ComponentFixture<ConditionsDispute>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ConditionsDispute]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ConditionsDispute);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
