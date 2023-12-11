package com.ufn.ProductsManagement.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ufn.ProductsManagement.models.Cliente;
import com.ufn.ProductsManagement.models.ItemPedido;
import com.ufn.ProductsManagement.models.Pagamento;
import com.ufn.ProductsManagement.models.Pedido;
import com.ufn.ProductsManagement.models.Produto;
import com.ufn.ProductsManagement.repository.ItemPedidoRepository;

@Service
public class ItemPedidoService {
    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    public List<ItemPedido> findAll() {
        return itemPedidoRepository.findAll();
    }

    public ItemPedido findById(Long id) {
        return itemPedidoRepository.findById(id).orElse(null);
    }

    public ItemPedido save(ItemPedido itemPedido) {
        validarItemPedido(itemPedido);
        return itemPedidoRepository.save(itemPedido);
    }

    public void deleteById(Long id) {
        itemPedidoRepository.deleteById(id);
    }

    public List<ItemPedido> findByPedidoId(Long pedidoId) {
        return itemPedidoRepository.findByPedidoId(pedidoId);
    }

    public List<ItemPedido> findByProdutoId(Long produtoId) {
        return itemPedidoRepository.findByProdutoId(produtoId);
    }

    public ItemPedido atualizarQuantidade(Long itemPedidoId, int novaQuantidade) {
        ItemPedido itemPedido = findById(itemPedidoId);
        if (itemPedido != null) {
            itemPedido.setQuantidade(novaQuantidade);
            return save(itemPedido);
        }
        throw new ItemPedidoNaoEncontradoException("Item de Pedido não encontrado para o ID: " + itemPedidoId);
    }

    public void excluirItensDoPedido(Long pedidoId) {
        List<ItemPedido> itensPedido = findByPedidoId(pedidoId);
        for (ItemPedido itemPedido : itensPedido) {
            deleteById(itemPedido.getId());
        }
    }

    public ItemPedido adicionarPagamento(Long itemPedidoId, Pagamento pagamento) {
        ItemPedido itemPedido = findById(itemPedidoId);
        if (itemPedido == null) {
            throw new ItemPedidoNaoEncontradoException("Item de Pedido não encontrado para o ID: " + itemPedidoId);
        }
        pagamento.setItemPedido(itemPedido);
        if (itemPedido.getPagamentos().contains(pagamento)) {
            throw new PagamentoExistenteException("Este pagamento já foi adicionado ao item de pedido.");
        }
        itemPedido.getPagamentos().add(pagamento);
        return save(itemPedido);
    }

    private void validarItemPedido(ItemPedido itemPedido) {
        if (itemPedido.getQuantidade() <= 0) {
            throw new IllegalArgumentException("A quantidade do item deve ser um número positivo.");
        }

        Pedido pedido = itemPedido.getPedido();
        if (pedido == null || pedido.getId() == null) {
            throw new IllegalArgumentException("O item de pedido deve estar associado a um pedido existente.");
        }

        Produto produto = itemPedido.getProduto();
        if (produto == null || produto.getId() == null) {
            throw new IllegalArgumentException("O item de pedido deve estar associado a um produto existente.");
        }

        Cliente cliente = itemPedido.getPedido().getCliente();
        if (cliente == null || cliente.getId() == null) {
            throw new IllegalArgumentException("O item de pedido deve estar associado a um cliente existente.");
        }
    }

    public static class ItemPedidoNaoEncontradoException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public ItemPedidoNaoEncontradoException(String message) {
            super(message);
        }
    }

    public static class PagamentoExistenteException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public PagamentoExistenteException(String message) {
            super(message);
        }
    }
}
