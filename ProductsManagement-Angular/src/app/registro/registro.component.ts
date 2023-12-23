import { Component } from '@angular/core';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms'; // Importe os módulos necessários

@Component({
  selector: 'app-registro',
  templateUrl: './registro.component.html',
  styleUrls: ['./registro.component.css']
})
export class RegistroComponent {
  registroForm: FormGroup; // Declare um FormGroup

  registroError: string | null = null;

  constructor(private authService: AuthService, private router: Router, private fb: FormBuilder) {
    // Inicialize o FormGroup no construtor
    this.registroForm = this.fb.group({
      username: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
    });
  }

  registrar() {
    // Utilize this.registroForm.value para obter os dados do formulário
    this.authService.registrar(this.registroForm.value).subscribe(
      () => {
        // Redirecionar para a página desejada após o registro
        this.router.navigate(['/dashboard']);
      },
      (error) => {
        // Exibir mensagem de erro
        this.registroError = 'Erro ao registrar. Verifique suas informações.';
        console.error(error);
      }
    );
  }
}
