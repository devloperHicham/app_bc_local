import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IndexPortComponent } from './index-port.component';

describe('IndexPortComponent', () => {
  let component: IndexPortComponent;
  let fixture: ComponentFixture<IndexPortComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [IndexPortComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(IndexPortComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
