export interface PagamentoRequestDTO {
    precoPago: number;
    pedidoId: number;
  }
  
  export interface PagamentoDTO {
    id: number;
    valor: number;
    dataPagamento: Date;
    status: string;
    pedidoId: number;
    clienteId: number;
  }
  