import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IndexContainersComponent } from './index-containers.component';

describe('IndexContainersComponent', () => {
  let component: IndexContainersComponent;
  let fixture: ComponentFixture<IndexContainersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [IndexContainersComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(IndexContainersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
