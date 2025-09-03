import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WhishlistQuote } from './whishlist-quote';

describe('WhishlistQuote', () => {
  let component: WhishlistQuote;
  let fixture: ComponentFixture<WhishlistQuote>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [WhishlistQuote]
    })
    .compileComponents();

    fixture = TestBed.createComponent(WhishlistQuote);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
