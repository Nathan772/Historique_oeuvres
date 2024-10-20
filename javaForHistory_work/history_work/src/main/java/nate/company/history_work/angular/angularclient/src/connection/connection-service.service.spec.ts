import { TestBed } from '@angular/core/testing';

import { ConnectionServiceService } from './connection-service.service';

describe('ConnectonServiceService', () => {
  let service: ConnexionServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ConnectionServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
