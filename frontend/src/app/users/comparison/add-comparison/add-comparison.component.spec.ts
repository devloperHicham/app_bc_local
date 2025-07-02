import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddComparisonComponent } from './add-comparison.component';

describe('AddComparisonComponent', () => {
  let component: AddComparisonComponent;
  let fixture: ComponentFixture<AddComparisonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddComparisonComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddComparisonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
