export interface Produto {
    id: number;
    nome: string;
    preco: number;
    categoriaId?: number;
    fornecedorId?: number;
  }
  