import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TripCompDetail } from './trip-comp-detail';

describe('TripCompDetail', () => {
  let component: TripCompDetail;
  let fixture: ComponentFixture<TripCompDetail>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TripCompDetail]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TripCompDetail);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
