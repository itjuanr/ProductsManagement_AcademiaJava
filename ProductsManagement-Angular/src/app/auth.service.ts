import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable, BehaviorSubject, throwError } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';
import { LoginResponse } from './login-response.model';
import { LoginErrorResponse } from './login-error-response.model';

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

  login(credentials: { login: string; password: string }): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, credentials, {
      withCredentials: true,
    }).pipe(
      tap(response => {
        const token = response?.token;
        console.log('Token recebido:', token);
        if (token) {
          this.saveToken(token);
        }
      }),
      catchError((error: HttpErrorResponse) => {
        if (error.status === 404) {
          const loginError: LoginErrorResponse = error.error;
          console.error('Erro ao fazer login:', loginError.message);
          return throwError(loginError);
        } else {
          console.error('Erro na requisição:', error);
          return throwError(error);
        }
      })
    );
  }
  
  registrar(registrationData: { login: string; email: string; password: string }): Observable<any> {
    return this.http.post(`${this.apiUrl}/register`, registrationData);
  }

  logout(): void {
    this.removeToken();
  }

  private saveToken(token: string): void {
    localStorage.setItem('token', token);
    console.log('Token salvo:', token);
    this.tokenSubject.next(token);
  }

  private removeToken(): void {
    localStorage.removeItem('token');
    this.tokenSubject.next(null);
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    console.error('Erro na requisição:', error);
    return throwError(error);
  }

  handleLoginError(error: HttpErrorResponse): string {
    if (error.status === 401) {
      return 'Credenciais inválidas. Verifique suas informações.';
    } else if (error.status === 403) {
      return 'Acesso proibido. Verifique suas permissões.';
    } else {
      return 'Erro ao fazer login. Verifique suas credenciais.';
    }
  }
}
