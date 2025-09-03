import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FaqGeneral } from './faq-general';

describe('FaqGeneral', () => {
  let component: FaqGeneral;
  let fixture: ComponentFixture<FaqGeneral>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FaqGeneral]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FaqGeneral);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
