export interface ItemPedidoDTO {
    id: number;
    clienteId: number;
    pedidoId: number;
    produtoId: number;
    pagamentoId: number;
    quantidade: number;
    preco: number;
    statusPagamento: string;
  }
  
  export interface CriarItemPedidoRequestDTO {
    pedidoId?: number | null;
    pagamentoId?: number | null;
  }
  
