import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchCompResult } from './search-comp-result';

describe('SearchCompResult', () => {
  let component: SearchCompResult;
  let fixture: ComponentFixture<SearchCompResult>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SearchCompResult]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SearchCompResult);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
