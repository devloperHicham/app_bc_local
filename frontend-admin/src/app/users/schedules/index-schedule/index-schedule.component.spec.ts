import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IndexScheduleComponent } from './index-schedule.component';

describe('IndexScheduleComponent', () => {
  let component: IndexScheduleComponent;
  let fixture: ComponentFixture<IndexScheduleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [IndexScheduleComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(IndexScheduleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
