import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IndexComparisonComponent } from './index-comparison.component';

describe('IndexComparisonComponent', () => {
  let component: IndexComparisonComponent;
  let fixture: ComponentFixture<IndexComparisonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [IndexComparisonComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(IndexComparisonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
