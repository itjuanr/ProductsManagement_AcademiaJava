// app.module.ts

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms'; // Importe o ReactiveFormsModule
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { RegistroComponent } from './registro/registro.component'; // Certifique-se de importar o RegistroComponent

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegistroComponent // Certifique-se de declarar o RegistroComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule, // Certifique-se de incluir o ReactiveFormsModule aqui
    HttpClientModule,
    AppRoutingModule
  ],
  bootstrap: [AppComponent],
})
export class AppModule { }
