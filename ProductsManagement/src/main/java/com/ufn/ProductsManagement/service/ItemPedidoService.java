package com.ufn.ProductsManagement.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufn.ProductsManagement.DTO.CriarItemPedidoRequestDTO;
import com.ufn.ProductsManagement.DTO.ItemPedidoDTO;
import com.ufn.ProductsManagement.DTO.PedidoDTO;
import com.ufn.ProductsManagement.models.Cliente;
import com.ufn.ProductsManagement.models.ItemPedido;
import com.ufn.ProductsManagement.models.Pagamento;
import com.ufn.ProductsManagement.models.Pedido;
import com.ufn.ProductsManagement.models.Produto;
import com.ufn.ProductsManagement.repository.ItemPedidoRepository;

@Service
public class ItemPedidoService {

	private static final Logger logger = LoggerFactory.getLogger(ItemPedidoService.class);

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	@Autowired
	private PedidoService pedidoService;

	@Autowired
	private PagamentoService pagamentoService;

	public List<ItemPedidoDTO> getAllItemPedidos(Long pedidoId) {
		logger.info("Recuperando todos os itens de pedido...");
		List<ItemPedido> itemPedidos;

		if (pedidoId != null) {
			itemPedidos = itemPedidoRepository.findByPedidoId(pedidoId);
		} else {
			itemPedidos = itemPedidoRepository.findAll();
		}

		return itemPedidos.stream().map(this::toItemPedidoDTO).collect(Collectors.toList());
	}

	public ItemPedidoDTO createItemPedido(CriarItemPedidoRequestDTO requestDTO) {
	    logger.info("Criando novo item de pedido...");
	    
	    PedidoDTO pedidoDTO = pedidoService.getPedidoDtoById(requestDTO.getPedidoId());
	    
	    Pagamento pagamento = pagamentoService.getPagamentoById(requestDTO.getPagamentoId());

	    if (pedidoDTO == null) {
	        logger.error("Pedido não encontrado com o ID: {}", requestDTO.getPedidoId());
	        throw new PedidoNaoEncontradoException("Pedido não encontrado com o ID: " + requestDTO.getPedidoId());
	    }

	    if (pagamento == null) {
	        logger.error("Pagamento não encontrado com o ID: {}", requestDTO.getPagamentoId());
	        throw new PagamentoNaoEncontradoException("Pagamento não encontrado com o ID: " + requestDTO.getPagamentoId());
	    }

	    if (!"Aprovado".equalsIgnoreCase(pagamento.getStatus())) {
	        logger.error("Status do pagamento deve ser 'Aprovado'");
	        throw new PagamentoNaoAprovadoException("Status do pagamento deve ser 'Aprovado'");
	    }

	    ItemPedido itemPedido = buildItemPedidoFromDTO(requestDTO, pedidoDTO, pagamento);
	    saveItemPedido(itemPedido);

	    return toItemPedidoDTO(itemPedido);
	}

	private ItemPedido buildItemPedidoFromDTO(CriarItemPedidoRequestDTO requestDTO, PedidoDTO pedidoDTO, Pagamento pagamento) {
	    logger.info("Construindo ItemPedido a partir de DTO...");
	    ItemPedido itemPedido = new ItemPedido();

	    Pedido pedido = pedidoService.getPedidoById(requestDTO.getPedidoId());
	    
	    itemPedido.setPedido(pedido);
	    itemPedido.setPagamento(pagamento);
	    itemPedido.setQuantidade(pedidoDTO.getQuantidade());
	    itemPedido.setPreco(pedidoDTO.getPreco());

	    return itemPedido;
	}


	private void saveItemPedido(ItemPedido itemPedido) {
		logger.info("Salvando ItemPedido...");
		itemPedidoRepository.save(itemPedido);
	}

	public ItemPedidoDTO getItemPedidoById(Long id) {
		logger.info("Buscando ItemPedido por ID: {}", id);
		ItemPedido itemPedido = itemPedidoRepository.findById(id)
				.orElseThrow(() -> new ItemPedidoNaoEncontradoException("ItemPedido não encontrado com o ID: " + id));
		return toItemPedidoDTO(itemPedido);
	}

	public ItemPedidoDTO toItemPedidoDTO(ItemPedido itemPedido) {
		logger.info("Convertendo ItemPedido para DTO...");
		ItemPedidoDTO itemPedidoDTO = new ItemPedidoDTO();

		Pedido pedido = itemPedido.getPedido();
		if (pedido != null) {
			itemPedidoDTO.setPedidoId(pedido.getId());

			Cliente cliente = pedido.getCliente();
			if (cliente != null) {
				itemPedidoDTO.setClienteId(cliente.getId());
			}

			Produto produto = pedido.getProduto();
			if (produto != null) {
				itemPedidoDTO.setProdutoId(produto.getId());
			}

			itemPedidoDTO.setQuantidade(itemPedido.getQuantidade());
			itemPedidoDTO.setPreco(itemPedido.getPreco());
		}

		Pagamento pagamento = itemPedido.getPagamento();
		if (pagamento != null) {
			itemPedidoDTO.setPagamentoId(pagamento.getId());
			itemPedidoDTO.setStatusPagamento(pagamento.getStatus());
		}

		itemPedidoDTO.setId(itemPedido.getId());

		return itemPedidoDTO;
	}

	public static class PedidoNaoEncontradoException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public PedidoNaoEncontradoException(String message) {
			super(message);
		}
	}

	public static class PagamentoNaoEncontradoException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public PagamentoNaoEncontradoException(String message) {
			super(message);
		}
	}

	public static class PagamentoNaoAprovadoException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public PagamentoNaoAprovadoException(String message) {
			super(message);
		}
	}

	public static class ItemPedidoNaoEncontradoException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public ItemPedidoNaoEncontradoException(String message) {
			super(message);
		}
	}
}
