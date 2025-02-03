import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { Observable } from 'rxjs';
import { Statement } from '../models/statement';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class StatementService {
  private api: String =
    environment.apiUrl + environment.apiVersion + '/statement';
  constructor(private httpClient: HttpClient) {}

  getAllByAccountId(accountId: number): Observable<Statement[]> {
    return this.httpClient.get<Statement[]>(`${this.api}/${accountId}`);
  }
}
