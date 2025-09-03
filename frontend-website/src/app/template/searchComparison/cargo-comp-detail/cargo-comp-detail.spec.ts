import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CargoCompDetail } from './cargo-comp-detail';

describe('CargoCompDetail', () => {
  let component: CargoCompDetail;
  let fixture: ComponentFixture<CargoCompDetail>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CargoCompDetail]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CargoCompDetail);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
