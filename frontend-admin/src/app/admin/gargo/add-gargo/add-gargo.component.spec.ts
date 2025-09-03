import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddGargoComponent } from './add-gargo.component';

describe('AddGargoComponent', () => {
  let component: AddGargoComponent;
  let fixture: ComponentFixture<AddGargoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddGargoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddGargoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
