import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WhishlistBooking } from './whishlist-booking';

describe('WhishlistBooking', () => {
  let component: WhishlistBooking;
  let fixture: ComponentFixture<WhishlistBooking>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [WhishlistBooking]
    })
    .compileComponents();

    fixture = TestBed.createComponent(WhishlistBooking);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
