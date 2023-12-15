// cliente.component.ts

import { Component, OnInit } from '@angular/core';
import { ClienteServiceService as ClienteService, Cliente } from './cliente-service.service';

@Component({
  selector: 'app-cliente',
  templateUrl: './cliente.component.html',
  styleUrls: ['./cliente.component.css']
})
export class ClienteComponent implements OnInit {
  clientes: Cliente[] = [];
  errorMessage: string = '';

  constructor(private clienteService: ClienteService) {}

  ngOnInit() {
    // Carrega os clientes automaticamente ao iniciar o componente
    this.loadClientes();
  }

  loadClientes() {
    this.clienteService.getAllClientes().subscribe(
      (data) => {
        this.clientes = data;
        this.errorMessage = '';
      },
      (error) => {
        console.error('Erro ao buscar clientes:', error);
        this.errorMessage = 'Erro ao carregar clientes. Tente novamente mais tarde.';
      }
    );
  }

  getClienteById(id: number | undefined) {
    // Verifica se id não é indefinido antes de chamar a função do serviço
    if (id !== undefined) {
      this.clienteService.getClienteById(id).subscribe(
        (cliente) => {
          console.log('Cliente encontrado:', cliente);
          // Aqui você pode implementar a navegação para uma página de detalhes ou mostrar um modal, por exemplo.
        },
        (error) => {
          console.error('Erro ao buscar cliente por ID:', error);
        }
      );
    } else {
      console.error('ID do cliente é indefinido.');
    }
  }

  createCliente() {
    // Simplesmente chama a função createCliente no serviço para criar um novo cliente
    const newCliente: Cliente = {
      nome: 'Novo Cliente', // Adapte conforme necessário
      email: 'novo@cliente.com',
      cpf: '123.456.789-00'
    };

    this.clienteService.createCliente(newCliente).subscribe(
      (createdCliente) => {
        console.log('Cliente criado:', createdCliente);
        // Atualiza a lista de clientes após criar um novo cliente
        this.loadClientes();
      },
      (error) => {
        console.error('Erro ao criar cliente:', error);
      }
    );
  }

  deleteCliente(id: number | undefined) {
    // Verifica se id não é indefinido antes de chamar a função do serviço
    if (id !== undefined) {
      this.clienteService.deleteCliente(id).subscribe(
        () => {
          console.log('Cliente excluído com sucesso.');
          // Atualiza a lista de clientes após excluir um cliente
          this.loadClientes();
        },
        (error) => {
          console.error('Erro ao excluir cliente:', error);
        }
      );
    } else {
      console.error('ID do cliente é indefinido.');
    }
  }

  searchClientes() {
    // Exemplo: Busca por clientes com o nome 'Exemplo'
    this.clienteService.searchClientes(undefined, 'Exemplo').subscribe(
      (result) => {
        this.clientes = result;
        this.errorMessage = '';
      },
      (error) => {
        console.error('Erro ao buscar clientes:', error);
        this.errorMessage = 'Erro ao realizar a busca de clientes. Tente novamente mais tarde.';
      }
    );
  }
}
