// app.module.ts
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';

import { AppComponent } from './app.component';
import { ClienteComponent } from './cliente/cliente.component';
import { ClienteServiceService as ClienteService } from './cliente/cliente-service.service'; // Ajuste aqui

@NgModule({
  declarations: [
    AppComponent,
    ClienteComponent,
    // Outros componentes aqui
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
  ],
  providers: [
    ClienteService, // Use o nome importado aqui
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
