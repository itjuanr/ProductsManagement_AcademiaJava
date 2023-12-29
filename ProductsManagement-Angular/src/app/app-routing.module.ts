
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegistroComponent } from './registro/registro.component'; 
import { ClienteComponent } from './cliente/cliente.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { CategoriaComponent } from './categoria/categoria.component';
import { FornecedorComponent } from './fornecedor/fornecedor.component';

const routes: Routes = [
  { path: 'auth/login', component: LoginComponent }, 
  { path: 'auth/registro', component: RegistroComponent }, 
  { path: 'cliente', component: ClienteComponent },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'categoria', component: CategoriaComponent }, 
  { path: 'fornecedor', component: FornecedorComponent },
  { path: '**', redirectTo: '/auth/login', pathMatch: 'full' },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
