// app-routing.module.ts

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegistroComponent } from './registro/registro.component'; // Importe o componente de registro
import { AuthGuard } from './auth.guard';

const routes: Routes = [
  { path: '', component: LoginComponent }, // LoginComponent como a tela inicial
  { path: 'login', component: LoginComponent },
  { path: 'registro', component: RegistroComponent }, // Adicione esta linha para a rota de registro
  // ... outras rotas
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
