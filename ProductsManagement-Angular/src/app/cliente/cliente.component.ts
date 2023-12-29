import { Component, OnInit } from '@angular/core';
import { ClienteService } from './cliente.service';
import { Cliente } from './cliente';

@Component({
  selector: 'app-cliente',
  templateUrl: './cliente.component.html',
  styleUrls: ['./cliente.component.css'],
})
export class ClienteComponent implements OnInit {
  clientes: Cliente[] = [];
  newCliente: Cliente = { id: 0, nome: '', cpf: '', email: '' };
  isNewCliente: boolean = true;

  constructor(private clienteService: ClienteService) {}

  ngOnInit(): void {
    this.loadClientes();
  }

  loadClientes(): void {
    this.clienteService.getAllClientes().subscribe((clientes) => {
      this.clientes = clientes;
    });
  }

  createOrUpdateCliente(): void {
    if (this.isNewCliente) {
      this.createCliente();
    } else {
      this.updateCliente();
    }
  }

  createCliente(): void {
    this.clienteService.createCliente(this.newCliente).subscribe(() => {
      this.loadClientes();
      this.resetForm();
    });
  }

  updateCliente(): void {
    this.clienteService.updateCliente(this.newCliente).subscribe(() => {
      this.loadClientes();
      this.resetForm();
    });
  }

  editCliente(cliente: Cliente): void {
    this.newCliente = { ...cliente };
    this.isNewCliente = false;
  }

  resetForm(): void {
    this.newCliente = { id: 0, nome: '', cpf: '', email: '' };
    this.isNewCliente = true;
  }

  deleteCliente(id: number): void {
    this.clienteService.deleteCliente(id).subscribe(() => {
      this.loadClientes();
    });
  }
}
