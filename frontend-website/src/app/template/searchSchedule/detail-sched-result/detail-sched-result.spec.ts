import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetailSchedResult } from './detail-sched-result';

describe('DetailSchedResult', () => {
  let component: DetailSchedResult;
  let fixture: ComponentFixture<DetailSchedResult>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DetailSchedResult]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DetailSchedResult);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
