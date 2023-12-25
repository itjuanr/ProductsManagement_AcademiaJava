import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  loginError: string | null = null;

  constructor(
    private authService: AuthService,
    private router: Router,
    private formBuilder: FormBuilder
  ) {}

  ngOnInit() {
    this.initializeForm();
  }

  initializeForm() {
    this.loginForm = this.formBuilder.group({
      login: ['', Validators.required],  
      password: ['', Validators.required]
    });
  }

  login() {
    this.authService.login(this.loginForm.value).subscribe(
      () => {
        this.router.navigate(['/dashboard']);
        console.log('Login bem-sucedido!');
      },
      (error) => {
        this.loginError = error.message || 'Erro ao fazer login. Verifique suas credenciais.';
      }
    );
  }

  redirectToRegistration() {
    this.router.navigate(['/auth/registro']);
  }
}
