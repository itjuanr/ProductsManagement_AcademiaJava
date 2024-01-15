import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { ItemPedidoDTO, CriarItemPedidoRequestDTO } from './itempedido';
import { AuthService } from '../auth.service';

@Injectable({
  providedIn: 'root',
})
export class ItemPedidoService {
  private baseUrl = 'http://localhost:8080/item-pedidos';

  constructor(private httpClient: HttpClient, private authService: AuthService) {}

  private getHeaders(): HttpHeaders {
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${this.authService.token}`
    });
  }

  getAllItemPedidos(): Observable<ItemPedidoDTO[]> {
    const headers = this.getHeaders();
    return this.httpClient.get<ItemPedidoDTO[]>(`${this.baseUrl}`, { headers });
  }

  getItemPedidoById(id: number): Observable<ItemPedidoDTO> {
    const headers = this.getHeaders();
    return this.httpClient.get<ItemPedidoDTO>(`${this.baseUrl}/${id}`, { headers });
  }

  createItemPedido(criarItemPedidoRequest: CriarItemPedidoRequestDTO): Observable<ItemPedidoDTO> {
    const headers = this.getHeaders();
    return this.httpClient.post<ItemPedidoDTO>(`${this.baseUrl}`, criarItemPedidoRequest, { headers })
      .pipe(
        catchError((error) => {
          console.error('Erro na criação do item de pedido:', error);
          throw error;
        })
      );
  }
}
