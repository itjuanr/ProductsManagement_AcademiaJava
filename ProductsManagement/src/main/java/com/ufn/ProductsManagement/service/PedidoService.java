package com.ufn.ProductsManagement.service;

import com.ufn.ProductsManagement.PedidoDTO;
import com.ufn.ProductsManagement.controller.CriarPedidoRequest;
import com.ufn.ProductsManagement.models.Cliente;
import com.ufn.ProductsManagement.models.ItemPedido;
import com.ufn.ProductsManagement.models.Pedido;
import com.ufn.ProductsManagement.models.Produto;

import com.ufn.ProductsManagement.repository.PedidoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private ProdutoService produtoService;

	public PedidoDTO createPedido(CriarPedidoRequest criarPedidoRequest) {
		Cliente cliente = clienteService.findById(criarPedidoRequest.getClienteId());

		if (cliente == null) {
			throw new ClienteNaoEncontradoException(
					"Cliente não encontrado com o ID: " + criarPedidoRequest.getClienteId());
		}

		Produto produto = produtoService.findById(criarPedidoRequest.getProdutoId());

		if (produto == null) {
			throw new ProdutoNaoEncontradoException(
					"Produto não encontrado com o ID: " + criarPedidoRequest.getProdutoId());
		}

		validarPedido(cliente, produto);

		Pedido pedido = new Pedido();
		pedido.setCliente(cliente);
		pedido.setProduto(produto);

		ItemPedido itemPedido = new ItemPedido();
		itemPedido.setQuantidade(criarPedidoRequest.getQuantidade());
		itemPedido.setCliente(cliente);
		itemPedido.setPreco(criarPedidoRequest.getPreco());

		pedido.adicionarItemPedido(itemPedido, produto);

		Pedido pedidoSalvo = pedidoRepository.save(pedido);

		return toPedidoDTO(pedidoSalvo);
	}

	public List<PedidoDTO> getAllPedidos() {
		List<Pedido> pedidos = pedidoRepository.findAll();
		return pedidos.stream().map(this::toPedidoDTO).collect(Collectors.toList());
	}

	public PedidoDTO getPedidoDtoById(Long id) {
		Pedido pedido = pedidoRepository.findById(id)
				.orElseThrow(() -> new PedidoNaoEncontradoException("Pedido não encontrado com o ID: " + id));
		return toPedidoDTO(pedido);
	}

	public void deletePedido(Long id) {
		pedidoRepository.deleteById(id);
	}

	private PedidoDTO toPedidoDTO(Pedido pedido) {
		PedidoDTO pedidoDto = new PedidoDTO();
		pedidoDto.setIdPedido(pedido.getId());

		Cliente cliente = pedido.getCliente();
		if (cliente != null) {
			pedidoDto.setClienteId(cliente.getId());
			pedidoDto.setClienteNome(cliente.getNome());
		}

		Produto produto = pedido.getProduto();
		if (produto != null) {
			pedidoDto.setProdutoId(produto.getId());
			pedidoDto.setProdutoNome(produto.getNome());
		}

		pedidoDto.setQuantidade(pedido.getQuantidade());
		pedidoDto.setPreco(calcularPrecoPedido(pedido));

		return pedidoDto;
	}


	private BigDecimal calcularPrecoPedido(Pedido pedido) {
		BigDecimal precoTotal = BigDecimal.ZERO;

		for (ItemPedido itemPedido : pedido.getItensPedido()) {
			BigDecimal precoItem = itemPedido.getPreco().multiply(BigDecimal.valueOf(itemPedido.getQuantidade()));
			precoTotal = precoTotal.add(precoItem);
		}

		return precoTotal;
	}

	private void validarPedido(Cliente cliente, Produto produto) {
		if (cliente == null) {
			throw new IllegalArgumentException("Cliente é obrigatório para criar um pedido.");
		}

		if (produto == null) {
			throw new IllegalArgumentException("Produto é obrigatório para criar um pedido.");
		}
	}

	public class ClienteNaoEncontradoException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public ClienteNaoEncontradoException(String message) {
			super(message);
		}
	}

	public class ProdutoNaoEncontradoException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public ProdutoNaoEncontradoException(String message) {
			super(message);
		}
	}

	public class PedidoNaoEncontradoException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public PedidoNaoEncontradoException(String message) {
			super(message);
		}
	}
}
