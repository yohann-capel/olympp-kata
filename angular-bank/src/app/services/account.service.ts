import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { BankAccount } from '../models/bank-account';
import { AmountData } from '../models/amount-data';

@Injectable({
  providedIn: 'root',
})
export class AccountService {
  private api: String = environment.apiUrl + environment.apiVersion + '/bank';

  constructor(private httpClient: HttpClient) {}

  initAccount(): Observable<BankAccount> {
    return this.httpClient.post<BankAccount>(`${this.api}/init`, null);
  }

  getAll(): Observable<BankAccount[]> {
    return this.httpClient.get<BankAccount[]>(`${this.api}`);
  }

  getById(id: number): Observable<BankAccount> {
    return this.httpClient.get<BankAccount>(`${this.api}/${id}`);
  }

  deposit(amountData: AmountData): Observable<BankAccount> {
    return this.httpClient.put<BankAccount>(`${this.api}/deposit`, amountData);
  }

  withdraw(amountData: AmountData): Observable<BankAccount> {
    return this.httpClient.put<BankAccount>(`${this.api}/withdraw`, amountData);
  }
}
