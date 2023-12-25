// app-routing.module.ts

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegistroComponent } from './registro/registro.component'; 

const routes: Routes = [
  { path: '', redirectTo: '/auth/login', pathMatch: 'full' }, 
  { path: 'auth/login', component: LoginComponent }, 
  { path: 'auth/registro', component: RegistroComponent }, 
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
