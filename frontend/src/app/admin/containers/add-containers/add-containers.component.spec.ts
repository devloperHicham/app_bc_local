import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddContainersComponent } from './add-containers.component';

describe('AddContainersComponent', () => {
  let component: AddContainersComponent;
  let fixture: ComponentFixture<AddContainersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddContainersComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddContainersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
