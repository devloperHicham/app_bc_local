import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IndexFaqComponent } from './index-faq.component';

describe('IndexFaqComponent', () => {
  let component: IndexFaqComponent;
  let fixture: ComponentFixture<IndexFaqComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [IndexFaqComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(IndexFaqComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
