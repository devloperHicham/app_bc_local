import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageScript } from './manage-script';

describe('ManageScript', () => {
  let component: ManageScript;
  let fixture: ComponentFixture<ManageScript>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ManageScript]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ManageScript);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
