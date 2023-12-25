import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) {}

  canActivate(): boolean {
    if (this.authService.token) {
      return true; // O usuário está autenticado, permita a navegação
    } else {
      this.router.navigate(['/login']); // O usuário não está autenticado, redirecione para o login
      return false;
    }
  }
}
