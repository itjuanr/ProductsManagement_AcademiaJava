import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CriarPedidoRequestDTO, PedidoDTO } from './pedido';
import { AuthService } from '../auth.service';

@Injectable({
  providedIn: 'root',
})
export class PedidoService {
  private apiUrl = 'http://localhost:8080/pedidos';

  constructor(private http: HttpClient, private authService: AuthService) {}

  private getHeaders(): HttpHeaders {
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${this.authService.token}`
    });
  }

  private getRequestOptions(params?: HttpParams): { headers: HttpHeaders; params?: HttpParams } {
    return {
      headers: this.getHeaders(),
      params,
    };
  }

  getAllPedidos(): Observable<PedidoDTO[]> {
    return this.http.get<PedidoDTO[]>(this.apiUrl, this.getRequestOptions());
  }

  getPedidoById(id: number): Observable<PedidoDTO> {
    return this.http.get<PedidoDTO>(`${this.apiUrl}/${id}`, this.getRequestOptions());
  }

  deletePedido(id: number): Observable<void> {
    console.log('PedidoService - Excluindo pedido com ID:', id);
    return this.http.delete<void>(`${this.apiUrl}/${id}`, this.getRequestOptions());
  }

updatePedido(id: number, atualizacao: any): Observable<PedidoDTO> {
  return this.http.put<PedidoDTO>(`${this.apiUrl}/${id}`, atualizacao, this.getRequestOptions());
}

  criarPedido(criarPedidoRequest: CriarPedidoRequestDTO): Observable<PedidoDTO> {
    return this.http.post<PedidoDTO>(this.apiUrl, criarPedidoRequest, this.getRequestOptions());
  }
}
