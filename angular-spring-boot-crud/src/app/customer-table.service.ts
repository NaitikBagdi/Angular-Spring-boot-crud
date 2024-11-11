import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Customer } from './customer';
import { Observable, catchError, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CustomerTableService {

  private getApiUrl = 'http://localhost:8080/api';
  constructor(private http: HttpClient) { }

  getAllCustomer(): Observable<Customer[]> {
    return this.http.get<Customer[]>(`${this.getApiUrl}`);
  }
  
  saveCustomer(customer?: Customer): Observable<any> {
    return this.http.post(`${this.getApiUrl}`, customer).pipe(
      catchError(error => throwError(error))
    );
  }

  getCustomerById(id: number): Observable<any> {
    return this.http.get<Customer>(`${this.getApiUrl}/${id}`);
  }

  updateCustomer(customer: Customer): Observable<any> {
    return this.http.put(`${this.getApiUrl}`, customer);
  }

  deleteCustomer(customerId: number): Observable<any> {
    return this.http.delete<object>(`${this.getApiUrl}/${customerId}`);
  }

}
