import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DropdownProfile } from './dropdown-profile';

describe('DropdownProfile', () => {
  let component: DropdownProfile;
  let fixture: ComponentFixture<DropdownProfile>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DropdownProfile]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DropdownProfile);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
