import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ClienteServiceService {
  private apiUrl = 'http://localhost:8080/api/clientes';

  constructor(private http: HttpClient) {}

  getAllClientes(): Observable<Cliente[]> {
    return this.http.get<Cliente[]>(this.apiUrl).pipe(
      catchError(error => throwError(error))
    );
  }

  getClienteById(id: number): Observable<Cliente> {
    return this.http.get<Cliente>(`${this.apiUrl}/${id}`).pipe(
      catchError(error => throwError(error))
    );
  }

  createCliente(cliente: Cliente): Observable<Cliente> {
    return this.http.post<Cliente>(this.apiUrl, cliente).pipe(
      catchError(error => throwError(error))
    );
  }

  deleteCliente(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`).pipe(
      catchError(error => throwError(error))
    );
  }

  searchClientes(email?: string, nome?: string): Observable<Cliente[]> {
    let params = new HttpParams();

    if (email) {
      params = params.set('email', email);
    } else if (nome) {
      params = params.set('nome', nome);
    } else {
      return throwError('Parâmetros inválidos para a busca.');
    }

    return this.http.get<Cliente[]>(`${this.apiUrl}/buscar`, { params }).pipe(
      catchError(error => throwError(error))
    );
  }
}

export interface Cliente {
  id?: number;
  nome: string;
  email: string;
  cpf: string;
}