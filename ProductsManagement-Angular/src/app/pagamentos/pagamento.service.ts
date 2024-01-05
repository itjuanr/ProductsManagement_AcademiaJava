import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PagamentoDTO, PagamentoRequestDTO } from './pagamento';
import { AuthService } from '../auth.service'; 

@Injectable({
  providedIn: 'root',
})
export class PagamentoService {
  private apiUrl = 'http://localhost:8080/pagamentos';

  constructor(private http: HttpClient, private authService: AuthService) {}

  getAllPagamentos(): Observable<PagamentoDTO[]> {
    const token = this.authService.token;
    const headers = this.createHeaders(token);

    return this.http.get<PagamentoDTO[]>(this.apiUrl, { headers });
  }

  createPagamento(pagamento: PagamentoRequestDTO): Observable<PagamentoDTO> {
    const token = this.authService.token;
    const headers = this.createHeaders(token);

    return this.http.post<PagamentoDTO>(this.apiUrl, pagamento, { headers });
  }

  deletePagamento(id: number): Observable<void> {
    const token = this.authService.token;
    const headers = this.createHeaders(token);

    const url = `${this.apiUrl}/${id}`;
    return this.http.delete<void>(url, { headers });
  }

  private createHeaders(token: string | null): HttpHeaders {
    return token
      ? new HttpHeaders({
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        })
      : new HttpHeaders({
          'Content-Type': 'application/json',
        });
  }

  getPagamentoPorId(id: number): Observable<PagamentoDTO> {
    const token = this.authService.token;
    const headers = this.createHeaders(token);
  
    const url = `${this.apiUrl}/${id}`;
    return this.http.get<PagamentoDTO>(url, { headers });
  }
  
}
