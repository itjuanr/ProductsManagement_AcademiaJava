import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Categoria } from './categoria';
import { AuthService } from '../auth.service';

@Injectable({
  providedIn: 'root',
})
export class CategoriaService {
  private apiUrl = 'http://localhost:8080/categorias';

  constructor(private http: HttpClient, private authService: AuthService) {}

  private getHeaders(): HttpHeaders {
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${this.authService.token}`
    });
  }

  getAllCategorias(): Observable<Categoria[]> {
    const headers = this.getHeaders();
    return this.http.get<Categoria[]>(this.apiUrl, { headers });
  }

  getCategoriaById(id: number): Observable<Categoria> {
    const headers = this.getHeaders();
    return this.http.get<Categoria>(`${this.apiUrl}/${id}`, { headers });
  }

  addCategoria(categoria: Categoria): Observable<Categoria> {
    const headers = this.getHeaders();
    return this.http.post<Categoria>(this.apiUrl, categoria, { headers });
  }

  updateCategoria(id: number, categoria: Categoria): Observable<Categoria> {
    const headers = this.getHeaders();
    return this.http.put<Categoria>(`${this.apiUrl}/${id}`, categoria, { headers });
  }

  deleteCategoria(id: number): Observable<any> {
    const headers = this.getHeaders();
    return this.http.delete(`${this.apiUrl}/${id}`, { headers });
  }
}
