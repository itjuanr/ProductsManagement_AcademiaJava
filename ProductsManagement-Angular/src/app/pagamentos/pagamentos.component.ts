import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PagamentoService } from './pagamento.service';
import { PagamentoDTO, PagamentoRequestDTO } from './pagamento';
import { ConfirmDialogComponent } from '../confirm-dialog.component';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-pagamento',
  templateUrl: './pagamentos.component.html',
  styleUrls: ['./pagamentos.component.css'],
  providers: [DatePipe],
})
export class PagamentoComponent implements OnInit {
  pagamentos: PagamentoDTO[] = [];
  novoPagamento: PagamentoRequestDTO = {
    precoPago: 0,
    pedidoId: 0,
  };

  constructor(
    private pagamentoService: PagamentoService,
    private dialog: MatDialog,
    private datePipe: DatePipe
  ) {}

  ngOnInit(): void {
    this.carregarPagamentos();
  }

  carregarPagamentos(): void {
    this.pagamentoService.getAllPagamentos().subscribe((pagamentos) => {
      this.pagamentos = pagamentos;
    });
  }

  criarPagamento(): void {
    this.pagamentoService.createPagamento(this.novoPagamento).subscribe(() => {
      this.resetarNovoPagamento();
      this.carregarPagamentos();
    });
  }

  resetarNovoPagamento(): void {
    this.novoPagamento = { precoPago: 0, pedidoId: 0 };
  }

  excluirPagamento(id: number): void {
    this.openConfirmationDialog('Deseja excluir este pagamento?', () => {
      this.pagamentoService.deletePagamento(id).subscribe(() => {
        this.carregarPagamentos();
      });
    });
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

  formatarData(data: Date): string {
    return this.datePipe.transform(data, 'dd/MM/yyyy HH:mm:ss') || '';
  }
}
