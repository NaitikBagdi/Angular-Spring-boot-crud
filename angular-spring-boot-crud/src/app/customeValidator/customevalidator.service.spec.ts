import { TestBed } from '@angular/core/testing';
import { customeValidator } from './customevalidator.service';


describe('CustomerService', () => {
  let service: customeValidator;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(customeValidator);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
