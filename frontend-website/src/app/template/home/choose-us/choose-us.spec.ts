import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChooseUs } from './choose-us';

describe('ChooseUs', () => {
  let component: ChooseUs;
  let fixture: ComponentFixture<ChooseUs>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ChooseUs]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ChooseUs);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
