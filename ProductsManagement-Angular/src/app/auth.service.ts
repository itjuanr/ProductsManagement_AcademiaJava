// auth.service.ts

import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, BehaviorSubject, throwError } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/auth';
  private tokenSubject = new BehaviorSubject<string | null>(localStorage.getItem('token'));

  constructor(private http: HttpClient) {}

  get token(): string | null {
    return this.tokenSubject.value;
  }

  login(credentials: { username: string; password: string }): Observable<any> {
    return this.http.post(`${this.apiUrl}/login`, credentials).pipe(
      tap((response: any) => {
        const token = response.token;
        if (token) {
          this.saveToken(token);
        }
      }),
      catchError(this.handleError)
    );
  }

  registrar(registrationData: { username: string; email: string; password: string }): Observable<any> {
    return this.http.post(`${this.apiUrl}/register`, registrationData);
  }

  logout(): void {
    this.removeToken();
  }

  private saveToken(token: string): void {
    localStorage.setItem('token', token);
    this.tokenSubject.next(token);
  }

  private removeToken(): void {
    localStorage.removeItem('token');
    this.tokenSubject.next(null);
  }

  handleLoginError(error: HttpErrorResponse): string {
    if (error.status === 401) {
      return 'Credenciais inválidas. Verifique suas informações.';
    } else {
      return 'Erro ao fazer login. Verifique suas credenciais.';
    }
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    return throwError(error);
  }
}
