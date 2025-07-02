import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditPortComponent } from './edit-port.component';

describe('EditPortComponent', () => {
  let component: EditPortComponent;
  let fixture: ComponentFixture<EditPortComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EditPortComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditPortComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
