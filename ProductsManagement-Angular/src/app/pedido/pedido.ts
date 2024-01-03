export interface CriarPedidoRequestDTO {
    clienteId: number;
    produtoId: number;
    quantidade: number;
  }
  
  export interface PedidoDTO {
    idPedido: number;
    clienteId: number;
    clienteNome: string;
    produtoId: number;
    produtoNome: string;
    quantidade: number;
    preco: number;
  }