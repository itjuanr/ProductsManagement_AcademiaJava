import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
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
  editarFornecedor: Fornecedor | null = null;

  constructor(private fornecedorService: FornecedorService, private cdr: ChangeDetectorRef) {}

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
      const updatedFornecedor: Fornecedor = {
        id: this.editarFornecedor.id,
        nome: this.novoFornecedor.nome || '',
        cnpj: this.novoFornecedor.cnpj || '',
      };

      this.fornecedorService.updateFornecedor(this.editarFornecedor.id, updatedFornecedor)
        .subscribe(
          () => {
            const index = this.fornecedores.findIndex(f => f.id === this.editarFornecedor!.id);
            if (index !== -1) {
              this.fornecedores[index] = updatedFornecedor;
              this.resetForm();
            }
          },
          (error) => {
            console.error('Erro ao editar fornecedor:', error);
          }
        );
    } else {
      if (this.novoFornecedor.nome && this.novoFornecedor.cnpj) {
        this.fornecedorService.addFornecedor(this.novoFornecedor).subscribe(
          (fornecedor) => {
            this.fornecedores.push(fornecedor);
            this.resetForm();
          },
          (error) => {
            console.error('Erro ao adicionar fornecedor:', error);
          }
        );
      }
    }
  }

  editFornecedor(fornecedor: Fornecedor): void {
    this.editarFornecedor = fornecedor;
    this.novoFornecedor = { ...fornecedor };
  }

  deleteFornecedor(id: number): void {
    this.fornecedorService.deleteFornecedor(id).subscribe(
      () => {
        this.fornecedores = this.fornecedores.filter(f => f.id !== id);
        this.resetForm();
      },
      (error) => {
        console.error('Erro ao excluir fornecedor:', error);
      }
    );
  }

  resetForm(): void {
    this.editarFornecedor = null;
    this.novoFornecedor = { id: 0, nome: '', cnpj: '' };
    this.cdr.detectChanges();
  }
}
