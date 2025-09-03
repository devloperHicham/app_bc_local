import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WhishlistSchedule } from './whishlist-schedule';

describe('WhishlistSchedule', () => {
  let component: WhishlistSchedule;
  let fixture: ComponentFixture<WhishlistSchedule>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [WhishlistSchedule]
    })
    .compileComponents();

    fixture = TestBed.createComponent(WhishlistSchedule);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
