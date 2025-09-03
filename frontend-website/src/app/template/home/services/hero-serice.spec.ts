import { TestBed } from '@angular/core/testing';

import { HeroSerice } from './hero-serice';

describe('HeroSerice', () => {
  let service: HeroSerice;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HeroSerice);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
