import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetailPriceFree } from './detail-price-free';

describe('DetailPriceFree', () => {
  let component: DetailPriceFree;
  let fixture: ComponentFixture<DetailPriceFree>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DetailPriceFree]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DetailPriceFree);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
