import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { FornecedorService } from './fornecedor.service';
import { Fornecedor } from './fornecedor';
import { ConfirmDialogComponent } from '../confirm-dialog.component';

@Component({
  selector: 'app-fornecedor',
  templateUrl: './fornecedor.component.html',
  styleUrls: ['./fornecedor.component.css'],
})
export class FornecedorComponent implements OnInit {
  fornecedores: Fornecedor[] = [];
  newFornecedor: Fornecedor = { id: 0, nome: '', cnpj: '' };
  isNewFornecedor: boolean = true;
  isEditing: boolean = false;

  constructor(
    private fornecedorService: FornecedorService,
    private cdr: ChangeDetectorRef,
    private dialog: MatDialog
  ) {}

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
    if (this.isNewFornecedor) {
      this.createFornecedor();
    } else {
      this.openConfirmationDialog('Deseja salvar as alterações no fornecedor?', this.updateFornecedor.bind(this));
    }
  }

  openConfirmationDialog(message: string, callback: () => void): void {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      data: { title: 'Confirmação', message: message },
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        callback();
      }
    });
  }

  createFornecedor(): void {
    this.fornecedorService.addFornecedor(this.newFornecedor).subscribe(
      (fornecedor) => {
        this.fornecedores.push(fornecedor);
        this.resetForm();
      },
      (error) => {
        console.error('Erro ao adicionar fornecedor:', error);
      }
    );
  }

  updateFornecedor(): void {
    this.fornecedorService.updateFornecedor(this.newFornecedor.id, this.newFornecedor).subscribe(
      () => {
        const index = this.fornecedores.findIndex((f) => f.id === this.newFornecedor!.id);
        if (index !== -1) {
          this.fornecedores[index] = this.newFornecedor;
          this.resetForm();
        }
      },
      (error) => {
        console.error('Erro ao editar fornecedor:', error);
      }
    );
  }

  editFornecedor(fornecedor: Fornecedor): void {
    if (this.isEditing) {
      this.openConfirmationDialog('Deseja descartar as alterações no fornecedor?', () => {
        this.newFornecedor = { ...fornecedor };
        this.isNewFornecedor = false;
        this.isEditing = false;
      });
    } else {
      this.newFornecedor = { ...fornecedor };
      this.isNewFornecedor = false;
      this.isEditing = true;
    }
  }

  discardChanges(): void {
    this.openConfirmationDialog('Deseja descartar as alterações no fornecedor?', () => {
      this.resetForm();
    });
  }

  deleteFornecedor(id: number): void {
    this.openConfirmationDialog('Deseja excluir este fornecedor?', () => {
      this.fornecedorService.deleteFornecedor(id).subscribe(
        () => {
          this.fornecedores = this.fornecedores.filter((f) => f.id !== id);
          this.resetForm();
        },
        (error) => {
          console.error('Erro ao excluir fornecedor:', error);
        }
      );
    });
  }

  resetForm(): void {
    this.newFornecedor = { id: 0, nome: '', cnpj: '' };
    this.isNewFornecedor = true;
    this.isEditing = false;
  }
}
