import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HistoriqueComparisonComponent } from './historique-comparison.component';

describe('HistoriqueComparisonComponent', () => {
  let component: HistoriqueComparisonComponent;
  let fixture: ComponentFixture<HistoriqueComparisonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HistoriqueComparisonComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HistoriqueComparisonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
