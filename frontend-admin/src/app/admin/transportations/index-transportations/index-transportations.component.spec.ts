import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IndexTransportationsComponent } from './index-transportations.component';

describe('IndexTransportationsComponent', () => {
  let component: IndexTransportationsComponent;
  let fixture: ComponentFixture<IndexTransportationsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [IndexTransportationsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(IndexTransportationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
