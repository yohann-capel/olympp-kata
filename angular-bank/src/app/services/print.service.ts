import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class PrintService {
  private api: String = environment.apiUrl + environment.apiVersion + '/print';
  constructor(private httpClient: HttpClient) {}

  printAccountData(accountId: number): Observable<string> {
    const requestOptions: Object = {
      responseType: 'text',
    };
    return this.httpClient.get<string>(
      `${this.api}/${accountId}`,
      requestOptions
    );
  }
}
