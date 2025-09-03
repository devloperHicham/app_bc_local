import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditContainersComponent } from './edit-containers.component';

describe('EditContainersComponent', () => {
  let component: EditContainersComponent;
  let fixture: ComponentFixture<EditContainersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EditContainersComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditContainersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
