import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IndexGargoComponent } from './index-gargo.component';

describe('IndexGargoComponent', () => {
  let component: IndexGargoComponent;
  let fixture: ComponentFixture<IndexGargoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [IndexGargoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(IndexGargoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
