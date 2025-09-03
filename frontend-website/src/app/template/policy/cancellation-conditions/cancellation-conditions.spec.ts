import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CancellationConditions } from './cancellation-conditions';

describe('CancellationConditions', () => {
  let component: CancellationConditions;
  let fixture: ComponentFixture<CancellationConditions>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CancellationConditions]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CancellationConditions);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
