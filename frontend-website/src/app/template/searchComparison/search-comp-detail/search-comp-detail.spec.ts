import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchCompDetail } from './search-comp-detail';

describe('SearchCompDetail', () => {
  let component: SearchCompDetail;
  let fixture: ComponentFixture<SearchCompDetail>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SearchCompDetail]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SearchCompDetail);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
