import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { PedidoService } from './pedido.service';
import { PedidoDTO, CriarPedidoRequestDTO } from './pedido';

@Component({
  selector: 'app-pedido',
  templateUrl: './pedido.component.html',
  styleUrls: ['./pedido.component.css'],
})
export class PedidoComponent implements OnInit {
  pedidos: PedidoDTO[] = [];
  novoPedido: CriarPedidoRequestDTO = {
    clienteId: 0,
    produtoId: 0,
    quantidade: 0,
  };
  pedidoSelecionado: PedidoDTO | null = null;

  constructor(private pedidoService: PedidoService, private cdr: ChangeDetectorRef) {}

  ngOnInit(): void {
    this.carregarPedidos();
  }

  carregarPedidos(): void {
    this.pedidoService.getAllPedidos().subscribe((pedidos) => {
      this.pedidos = pedidos;
    });
  }

  criarPedido(): void {
    this.pedidoService.criarPedido(this.novoPedido).subscribe(() => this.resetarNovoPedido());
  }

  resetarNovoPedido(): void {
    this.carregarPedidos();
    this.novoPedido = { clienteId: 0, produtoId: 0, quantidade: 0 };
  }

  selecionarPedido(pedido: PedidoDTO): void {
    this.pedidoSelecionado = pedido;
  }

  atualizarQuantidade(novaQuantidade: number): void {
    this.atualizarPedido({ novaQuantidade });
  }

  atualizarCliente(novoIdCliente: number): void {
    this.atualizarPedido({ novoIdCliente });
  }

  atualizarProduto(novoIdProduto: number): void {
    this.atualizarPedido({ novoIdProduto });
  }

  atualizarPedido(atualizacao: any): void {
    if (this.pedidoSelecionado) {
      const { novaQuantidade, novoIdCliente, novoIdProduto } = atualizacao;
      const atualizacaoPedido = {
        novaQuantidade: novaQuantidade !== undefined ? novaQuantidade : this.pedidoSelecionado.quantidade,
        novoIdCliente: novoIdCliente !== undefined ? novoIdCliente : this.pedidoSelecionado.clienteId,
        novoIdProduto: novoIdProduto !== undefined ? novoIdProduto : this.pedidoSelecionado.produtoId,
      };
  
      this.pedidoService.updatePedido(this.pedidoSelecionado.idPedido, atualizacaoPedido).subscribe(() => {
        this.carregarPedidos();
        this.pedidoSelecionado = null;
      });
    }
  }

  excluirPedido(): void {
    console.log('Excluir pedido chamado!');
    
    if (this.pedidoSelecionado) {
      this.pedidoService.deletePedido(this.pedidoSelecionado.idPedido)
        .subscribe(
          () => {
            console.log('Pedido excluído com sucesso!');
            this.carregarPedidos();
            this.pedidoSelecionado = null;
          },
          error => console.error('Erro na exclusão do pedido:', error)
        );
    } else {
      console.log('Nenhum pedido selecionado. Tentando excluir o último pedido da lista.');
  
      const ultimoPedido = this.pedidos[this.pedidos.length - 1];
      if (ultimoPedido) {
        this.pedidoService.deletePedido(ultimoPedido.idPedido)
          .subscribe(
            () => {
              console.log('Último pedido excluído com sucesso!');
              this.carregarPedidos();
            },
            error => console.error('Erro na exclusão do último pedido:', error)
          );
      } else {
        console.log('Nenhum pedido disponível para exclusão.');
      }
    }
  }
}
