import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditComparisonComponent } from './edit-comparison.component';

describe('EditComparisonComponent', () => {
  let component: EditComparisonComponent;
  let fixture: ComponentFixture<EditComparisonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EditComparisonComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditComparisonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
