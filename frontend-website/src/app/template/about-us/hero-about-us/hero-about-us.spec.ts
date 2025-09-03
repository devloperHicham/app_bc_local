import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HeroAboutUs } from './hero-about-us';

describe('HeroAboutUs', () => {
  let component: HeroAboutUs;
  let fixture: ComponentFixture<HeroAboutUs>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HeroAboutUs]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HeroAboutUs);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
