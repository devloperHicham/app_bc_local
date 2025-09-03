import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetailPriceBusniss } from './detail-price-busniss';

describe('DetailPriceBusniss', () => {
  let component: DetailPriceBusniss;
  let fixture: ComponentFixture<DetailPriceBusniss>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DetailPriceBusniss]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DetailPriceBusniss);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
