// cliente.service.ts

import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Cliente } from './cliente';
import { AuthService } from '../auth.service';

@Injectable({
  providedIn: 'root',
})
export class ClienteService {
  private apiUrl = 'http://localhost:8080/clientes';

  constructor(private http: HttpClient, private authService: AuthService) {}

  getAllClientes(): Observable<Cliente[]> {
    const token = this.authService.token;

    const headers = token
      ? new HttpHeaders({
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        })
      : new HttpHeaders({
          'Content-Type': 'application/json',
        });

    return this.http.get<Cliente[]>(this.apiUrl, { headers });
  }

  createCliente(cliente: Cliente): Observable<any> {
    const token = this.authService.token;

    const headers = token
      ? new HttpHeaders({
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        })
      : new HttpHeaders({
          'Content-Type': 'application/json',
        });

    return this.http.post(this.apiUrl, cliente, { headers });
  }

  updateCliente(cliente: Cliente): Observable<Cliente> {
    const token = this.authService.token;

    const headers = token
      ? new HttpHeaders({
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        })
      : new HttpHeaders({
          'Content-Type': 'application/json',
        });

    return this.http.put<Cliente>(`${this.apiUrl}/${cliente.id}`, cliente, { headers });
  }

  deleteCliente(id: number): Observable<any> {
    const token = this.authService.token;

    const headers = token
      ? new HttpHeaders({
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        })
      : new HttpHeaders({
          'Content-Type': 'application/json',
        });

    return this.http.delete(`${this.apiUrl}/${id}`, { headers });
  }
}
