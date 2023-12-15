package com.ufn.ProductsManagement.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufn.ProductsManagement.DTO.PagamentoDTO;
import com.ufn.ProductsManagement.DTO.PagamentoRequestDTO;
import com.ufn.ProductsManagement.DTO.PedidoDTO;
import com.ufn.ProductsManagement.models.Pagamento;
import com.ufn.ProductsManagement.models.Pedido;
import com.ufn.ProductsManagement.repository.PagamentoRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class PagamentoService {
	private static final Logger logger = LogManager.getLogger(PagamentoService.class);

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private PedidoService pedidoService;

	public List<PagamentoDTO> findAll() {
		logger.info("Buscando todos os pagamentos");
		List<Pagamento> pagamentos = pagamentoRepository.findAll();
		return pagamentos.stream().map(this::toPagamentoDTO).collect(Collectors.toList());
	}

	public PagamentoDTO findById(Long id) {
		logger.info("Buscando pagamento por ID: {}", id);
		Pagamento pagamento = pagamentoRepository.findById(id).orElse(null);
		return pagamento != null ? toPagamentoDTO(pagamento) : null;
	}

	public Pagamento getPagamentoById(Long id) {
		logger.info("Buscando pagamento por ID: {}", id);
		return pagamentoRepository.findById(id).orElse(null);
	}

	public PagamentoDTO save(PagamentoRequestDTO pagamentoDTO) {
		logger.info("Salvando novo pagamento: {}", pagamentoDTO);
		if (pagamentoDTO.getPrecoPago().compareTo(BigDecimal.ZERO) <= 0) {
			logger.error("Valor inválido para pagamento: {}", pagamentoDTO.getPrecoPago());
			throw new PagamentoInvalidoException("O valor do pagamento deve ser maior que zero.");
		}

		PedidoDTO pedidoDTO = pedidoService.getPedidoDtoById(pagamentoDTO.getPedidoId());

		if (pedidoDTO == null || pedidoDTO.getPreco().compareTo(pagamentoDTO.getPrecoPago()) != 0) {
			logger.error(
					"Pedido inválido ou valor do pagamento não corresponde ao valor total do pedido. Pedido: {}, Pagamento: {}",
					pedidoDTO, pagamentoDTO);
			throw new PagamentoInvalidoException(
					"Pedido inválido ou valor do pagamento não corresponde ao valor total do pedido.");
		}

		Pagamento pagamento = convertToPagamento(pagamentoDTO);

		Pagamento savedPagamento = pagamentoRepository.save(pagamento);

		logger.info("Pagamento salvo com sucesso: {}", savedPagamento);
		return toPagamentoDTO(savedPagamento);
	}

	public PagamentoDTO toPagamentoDTO(Pagamento pagamento) {
		Long clienteId = null;
		Long pedidoId = null;

		if (pagamento.getPedido() != null) {
			Pedido pedido = pagamento.getPedido();
			pedidoId = pedido.getId();

			if (pedido.getCliente() != null) {
				clienteId = pedido.getCliente().getId();
			}
		}

		return new PagamentoDTO(pagamento.getId(), pagamento.getValor(), pagamento.getDataPagamento(),
				pagamento.getStatus(), pedidoId, clienteId);
	}

	public void deleteById(Long id) {
		logger.info("Deletando pagamento por ID: {}", id);
		pagamentoRepository.deleteById(id);
	}

	public List<PagamentoDTO> findByStatus(String status) {
		logger.info("Buscando pagamentos por status: {}", status);
		List<Pagamento> pagamentos = pagamentoRepository.findByStatus(status);
		return pagamentos.stream().map(this::toPagamentoDTO).collect(Collectors.toList());
	}

	public List<PagamentoDTO> findByClienteId(Long clienteId) {
		logger.info("Buscando pagamentos por ID do cliente: {}", clienteId);
		List<Pagamento> pagamentos = pagamentoRepository.findByClienteId(clienteId);
		return pagamentos.stream().map(this::toPagamentoDTO).collect(Collectors.toList());
	}

	private Pagamento convertToPagamento(PagamentoRequestDTO pagamentoDTO) {
		Pagamento pagamento = new Pagamento();
		pagamento.setValor(pagamentoDTO.getPrecoPago());
		pagamento.setStatus("Aprovado");
		pagamento.setDataPagamento(new Date());

		Pedido pedido = new Pedido();
		pedido.setId(pagamentoDTO.getPedidoId());
		pagamento.setPedido(pedido);

		return pagamento;
	}

	public static class PagamentoInvalidoException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public PagamentoInvalidoException(String message) {
			super(message);
		}
	}
}
