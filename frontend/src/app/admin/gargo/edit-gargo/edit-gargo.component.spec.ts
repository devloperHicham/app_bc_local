import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditGargoComponent } from './edit-gargo.component';

describe('EditGargoComponent', () => {
  let component: EditGargoComponent;
  let fixture: ComponentFixture<EditGargoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EditGargoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditGargoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
