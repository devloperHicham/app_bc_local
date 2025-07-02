import { TestBed } from '@angular/core/testing';

import { GargoService } from './gargo.service';

describe('GargoService', () => {
  let service: GargoService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GargoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
