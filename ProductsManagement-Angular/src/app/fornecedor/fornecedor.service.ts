import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { catchError, switchMap, tap } from 'rxjs/operators';
import { Fornecedor } from './fornecedor';
import { AuthService } from '../auth.service';

@Injectable({
  providedIn: 'root',
})
export class FornecedorService {
  private apiUrl = 'http://localhost:8080/fornecedores';

  private fornecedoresSubject = new BehaviorSubject<Fornecedor[]>([]);
  fornecedores$ = this.fornecedoresSubject.asObservable();

  constructor(private http: HttpClient, private authService: AuthService) {}

  private getHeaders(): HttpHeaders {
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${this.authService.token}`
    });
  }

  private getAllFornecedoresFromServer(): Observable<Fornecedor[]> {
    const headers = this.getHeaders();
    return this.http.get<Fornecedor[]>(this.apiUrl, { headers }).pipe(
      catchError((error) => {
        console.error('Erro ao obter fornecedores:', error);
        throw error;
      })
    );
  }

  private emitChanges(fornecedores: Fornecedor[]): void {
    this.fornecedoresSubject.next([...fornecedores]);
  }

  getAllFornecedores(): void {
    this.getAllFornecedoresFromServer().subscribe(
      (fornecedores) => {
        this.emitChanges(fornecedores);
      }
    );
  }

  getFornecedorById(id: number): Observable<Fornecedor> {
    const headers = this.getHeaders();
    return this.http.get<Fornecedor>(`${this.apiUrl}/${id}`, { headers });
  }

  addFornecedor(fornecedor: Fornecedor): Observable<Fornecedor> {
    const headers = this.getHeaders();
    return this.http.post<Fornecedor>(this.apiUrl, fornecedor, { headers }).pipe(
      catchError((error) => {
        console.error('Erro ao adicionar fornecedor:', error);
        throw error;
      })
    );
  }

  updateFornecedor(id: number, fornecedor: Fornecedor | null): Observable<Fornecedor[]> {
    const headers = this.getHeaders();
    console.log('Dados a serem enviados para atualização:', fornecedor);
    return this.http.put<Fornecedor>(`${this.apiUrl}/${id}`, fornecedor, { headers }).pipe(
      switchMap(() => this.getAllFornecedoresFromServer()),
      catchError((error) => {
        console.error('Erro na atualização do fornecedor:', error);
        throw error;
      })
    );
  }

  deleteFornecedor(id: number): Observable<Fornecedor[]> {
    const headers = this.getHeaders();
    return this.http.delete<void>(`${this.apiUrl}/${id}`, { headers }).pipe(
      switchMap(() => this.getAllFornecedoresFromServer()),
      catchError((error) => {
        console.error('Erro ao excluir fornecedor:', error);
        throw error;
      })
    );
  }
}
