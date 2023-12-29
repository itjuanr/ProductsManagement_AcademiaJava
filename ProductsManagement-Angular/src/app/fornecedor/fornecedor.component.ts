// fornecedor.component.ts
import { Component, OnInit } from '@angular/core';
import { FornecedorService } from './fornecedor.service';
import { Fornecedor } from './fornecedor';

@Component({
  selector: 'app-fornecedor',
  templateUrl: './fornecedor.component.html',
  styleUrls: ['./fornecedor.component.css'],
})
export class FornecedorComponent implements OnInit {
  fornecedores: Fornecedor[] = [];
  novoFornecedor: Fornecedor = { id: 0, nome: '', cnpj: '' };
  editarFornecedor: Fornecedor | null = null

  constructor(private fornecedorService: FornecedorService) {}

  ngOnInit(): void {
    this.getAllFornecedores();
  }

  getAllFornecedores(): void {
    this.fornecedorService.fornecedores$.subscribe(
      (fornecedores) => {
        this.fornecedores = fornecedores;
      },
      (error) => {
        console.error('Erro ao obter fornecedores:', error);
      }
    );
    this.fornecedorService.getAllFornecedores();
  }

  createOrUpdateFornecedor(): void {
    if (this.editarFornecedor) {
      // Atualizar fornecedor existente
      const updatedFornecedor: Fornecedor = {
        ...this.editarFornecedor,
        nome: this.novoFornecedor?.nome || '',
        cnpj: this.novoFornecedor?.cnpj || '',
      };
  
      this.fornecedorService.updateFornecedor(this.editarFornecedor.id, updatedFornecedor)
        .subscribe(
          (fornecedor) => {
            const index = this.fornecedores.findIndex(f => f.id === fornecedor.id);
            this.fornecedores[index] = fornecedor;
            this.resetForm();
          },
          (error) => {
            console.error('Erro ao editar fornecedor:', error);
          }
        );
    } else if (this.novoFornecedor) {
      // Adicionar novo fornecedor
      this.fornecedorService.addFornecedor(this.novoFornecedor).subscribe(
        (fornecedor) => {
          this.resetForm();
        },
        (error) => {
          console.error('Erro ao adicionar fornecedor:', error);
        }
      );
    }
  }

  editFornecedor(fornecedor: Fornecedor): void {
    this.editarFornecedor = fornecedor;
    this.novoFornecedor = { ...fornecedor };
  }

  deleteFornecedor(id: number): void {
    this.fornecedorService.deleteFornecedor(id).subscribe(
      () => {
        this.resetForm();
      },
      (error) => {
        console.error('Erro ao excluir fornecedor:', error);
      }
    );
  }

  resetForm(): void {
    this.editarFornecedor = null;
  }
}
