import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HistoriqueScheduleComponent } from './historique-schedule.component';

describe('HistoriqueScheduleComponent', () => {
  let component: HistoriqueScheduleComponent;
  let fixture: ComponentFixture<HistoriqueScheduleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HistoriqueScheduleComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HistoriqueScheduleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
