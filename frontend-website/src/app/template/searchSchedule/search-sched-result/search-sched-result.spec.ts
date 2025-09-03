import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchSchedResult } from './search-sched-result';

describe('SearchSchedResult', () => {
  let component: SearchSchedResult;
  let fixture: ComponentFixture<SearchSchedResult>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SearchSchedResult]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SearchSchedResult);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
