import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditTransportationsComponent } from './edit-transportations.component';

describe('EditTransportationsComponent', () => {
  let component: EditTransportationsComponent;
  let fixture: ComponentFixture<EditTransportationsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EditTransportationsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditTransportationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
