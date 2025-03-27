import { TestBed } from '@angular/core/testing';

import { VideoDLServiceService } from './video-dlservice.service';

describe('VideoDLServiceService', () => {
  let service: VideoDLServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VideoDLServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
