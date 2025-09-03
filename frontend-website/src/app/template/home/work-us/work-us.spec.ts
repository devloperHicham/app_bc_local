import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WorkUs } from './work-us';

describe('WorkUs', () => {
  let component: WorkUs;
  let fixture: ComponentFixture<WorkUs>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [WorkUs]
    })
    .compileComponents();

    fixture = TestBed.createComponent(WorkUs);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
