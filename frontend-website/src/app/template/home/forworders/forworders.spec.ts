import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Forworders } from './forworders';

describe('Forworders', () => {
  let component: Forworders;
  let fixture: ComponentFixture<Forworders>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Forworders]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Forworders);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
