import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MapsAboutUs } from './maps-about-us';

describe('MapsAboutUs', () => {
  let component: MapsAboutUs;
  let fixture: ComponentFixture<MapsAboutUs>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MapsAboutUs]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MapsAboutUs);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
