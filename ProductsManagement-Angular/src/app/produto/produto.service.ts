import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Produto } from './produto';
import { AuthService } from '../auth.service';

@Injectable({
  providedIn: 'root',
})
export class ProdutoService {
  private baseUrl = 'http://localhost:8080/produtos';

  constructor(private httpClient: HttpClient, private authService: AuthService) {}

  private getHeaders(): HttpHeaders {
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${this.authService.token}`
    });
  }

  getAllProdutos(): Observable<Produto[]> {
    const headers = this.getHeaders();
    return this.httpClient.get<Produto[]>(`${this.baseUrl}`, { headers });
  }

  getProdutoById(id: number): Observable<Produto> {
    const headers = this.getHeaders();
    return this.httpClient.get<Produto>(`${this.baseUrl}/${id}`, { headers });
  }

  saveProduto(produto: Produto, categoriaId: number, fornecedorId: number): Observable<Produto> {
    const headers = this.getHeaders();
    return this.httpClient.post<Produto>(
      `${this.baseUrl}/categoria/${categoriaId}/fornecedor/${fornecedorId}`,
      produto,
      { headers }
    );
  }

  updateProduto(id: number, produto: Produto): Observable<Produto> {
    const headers = this.getHeaders();
    return this.httpClient.put<Produto>(`${this.baseUrl}/${id}`, produto, { headers })
      .pipe(
        catchError((error) => {
          console.error('Erro na atualização do produto:', error);
          throw error;
        })
      );
  }

  deleteProduto(id: number): Observable<void> {
    const headers = this.getHeaders();
    return this.httpClient.delete<void>(`${this.baseUrl}/${id}`, { headers });
  }
}
