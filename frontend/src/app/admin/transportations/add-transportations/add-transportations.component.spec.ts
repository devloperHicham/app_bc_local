import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddTransportationsComponent } from './add-transportations.component';

describe('AddTransportationsComponent', () => {
  let component: AddTransportationsComponent;
  let fixture: ComponentFixture<AddTransportationsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddTransportationsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddTransportationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
