package com.ufn.ProductsManagement.service;

import com.ufn.ProductsManagement.DTO.CriarPedidoRequestDTO;
import com.ufn.ProductsManagement.DTO.PedidoDTO;
import com.ufn.ProductsManagement.models.Cliente;
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

	public PedidoDTO createPedido(CriarPedidoRequestDTO criarPedidoRequest) {
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
		pedido.setQuantidade(criarPedidoRequest.getQuantidade());

		BigDecimal precoProduto = BigDecimal.valueOf(produto.getPreco());
		BigDecimal precoTotal = precoProduto.multiply(BigDecimal.valueOf(criarPedidoRequest.getQuantidade()));
		pedido.setPreco(precoTotal);

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
	
    public Pedido getPedidoById(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNaoEncontradoException("Pedido não encontrado com o ID: " + id));
    }

	public void deletePedido(Long id) {
		pedidoRepository.deleteById(id);
	}

	public PedidoDTO updatePedidoQuantidade(Long id, Integer novaQuantidade) {
		Pedido pedido = pedidoRepository.findById(id)
				.orElseThrow(() -> new PedidoNaoEncontradoException("Pedido não encontrado com o ID: " + id));

		if (novaQuantidade != null && novaQuantidade > 0) {
			pedido.setQuantidade(novaQuantidade);
			BigDecimal precoProduto = BigDecimal.valueOf(pedido.getProduto().getPreco());
			BigDecimal precoTotal = precoProduto.multiply(BigDecimal.valueOf(novaQuantidade));
			pedido.setPreco(precoTotal);
		}

		Pedido pedidoAtualizado = pedidoRepository.save(pedido);
		return toPedidoDTO(pedidoAtualizado);
	}

	public PedidoDTO updatePedidoCliente(Long id, Long novoIdCliente) {
		Pedido pedido = pedidoRepository.findById(id)
				.orElseThrow(() -> new PedidoNaoEncontradoException("Pedido não encontrado com o ID: " + id));

		Cliente novoCliente = clienteService.findById(novoIdCliente);

		if (novoCliente != null) {
			pedido.setCliente(novoCliente);
		}

		Pedido pedidoAtualizado = pedidoRepository.save(pedido);
		return toPedidoDTO(pedidoAtualizado);
	}

	public PedidoDTO updatePedidoProduto(Long id, Long novoIdProduto) {
		Pedido pedido = pedidoRepository.findById(id)
				.orElseThrow(() -> new PedidoNaoEncontradoException("Pedido não encontrado com o ID: " + id));

		Produto novoProduto = produtoService.findById(novoIdProduto);

		if (novoProduto != null) {
			pedido.setProduto(novoProduto);
			BigDecimal precoProduto = BigDecimal.valueOf(novoProduto.getPreco());
			BigDecimal precoTotal = precoProduto.multiply(BigDecimal.valueOf(pedido.getQuantidade()));
			pedido.setPreco(precoTotal);
		}

		Pedido pedidoAtualizado = pedidoRepository.save(pedido);
		return toPedidoDTO(pedidoAtualizado);
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

		BigDecimal precoProduto = BigDecimal.valueOf(produto.getPreco());
		BigDecimal precoTotal = precoProduto.multiply(BigDecimal.valueOf(pedido.getQuantidade()));
		pedidoDto.setPreco(precoTotal);

		return pedidoDto;
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
