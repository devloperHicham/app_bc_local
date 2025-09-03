import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Marketplaces } from './marketplaces';

describe('Marketplaces', () => {
  let component: Marketplaces;
  let fixture: ComponentFixture<Marketplaces>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Marketplaces]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Marketplaces);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
