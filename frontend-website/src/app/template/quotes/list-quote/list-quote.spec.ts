import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListQuote } from './list-quote';

describe('ListQuote', () => {
  let component: ListQuote;
  let fixture: ComponentFixture<ListQuote>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListQuote]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListQuote);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
