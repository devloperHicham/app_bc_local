import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IndexCompaniesComponent } from './index-companies.component';

describe('IndexCompaniesComponent', () => {
  let component: IndexCompaniesComponent;
  let fixture: ComponentFixture<IndexCompaniesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [IndexCompaniesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(IndexCompaniesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
