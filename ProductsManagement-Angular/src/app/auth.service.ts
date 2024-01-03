import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable, BehaviorSubject, throwError } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';
import { LoginResponse } from './login-response.model';
import { LoginErrorResponse } from './login-error-response.model';
import { MessageService } from './message.service';
import { Router } from '@angular/router'; 
import { MatDialog } from '@angular/material/dialog';
import { ConfirmDialogComponent } from './confirm-dialog.component';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/auth';
  private tokenSubject = new BehaviorSubject<string | null>(localStorage.getItem('token'));

  constructor(
    private http: HttpClient,
    private messageService: MessageService,
    private router: Router,
    private dialog: MatDialog 
  ) {}

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
          this.messageService.showMessage({ text: 'Login bem-sucedido!', type: 'success' });
        }
      }),
      catchError((error: HttpErrorResponse) => {
        if (error.status === 404) {
          const loginError: LoginErrorResponse = error.error;
          console.error('Erro ao fazer login:', loginError.message);
          this.messageService.showMessage({ text: loginError.message, type: 'error' });
          return throwError(loginError);
        } else {
          console.error('Erro na requisição:', error);
          this.messageService.showMessage({ text: 'Erro ao fazer login. Verifique suas credenciais.', type: 'error' });
          return throwError(error);
        }
      })
    );
  }
  
  registrar(registrationData: { login: string; email: string; password: string }): Observable<any> {
    const payload = { ...registrationData, role: 'USER' };
    return this.http.post(`${this.apiUrl}/register`, payload).pipe(
      tap(() => {
        this.messageService.showMessage({ text: 'Registro bem-sucedido!', type: 'success' });
      }),
      catchError((error: HttpErrorResponse) => {
        console.error('Erro no registro:', error);
        this.messageService.showMessage({ text: 'Erro ao registrar. Verifique suas informações.', type: 'error' });
        return throwError(error);
      })
    );
  }

  logout(): void {
    this.openConfirmationDialog('Deseja realmente sair?').subscribe((result) => {
      if (result) {
        this.removeToken();
        this.router.navigate(['/auth/login']);
      }
    });
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

  private openConfirmationDialog(message: string): Observable<boolean> {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      data: { title: 'Confirmação', message: message },
    });

    return dialogRef.afterClosed();
  }
}
