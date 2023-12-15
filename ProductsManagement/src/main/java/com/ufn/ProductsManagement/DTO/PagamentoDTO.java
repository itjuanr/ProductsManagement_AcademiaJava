package com.ufn.ProductsManagement.DTO;

import java.math.BigDecimal;
import java.util.Date;

public class PagamentoDTO {
	private Long id;
	private BigDecimal valor;
	private Date dataPagamento;
	private String status;
	private Long pedidoId;
	private Long clienteId;

	public PagamentoDTO(Long id, BigDecimal valor, Date dataPagamento, String status, Long pedidoId, Long clienteId) {
		this.id = id;
		this.valor = valor;
		this.dataPagamento = dataPagamento;
		this.status = status;
		this.pedidoId = pedidoId;
		this.clienteId = clienteId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getPedidoId() {
		return pedidoId;
	}

	public void setPedidoId(Long pedidoId) {
		this.pedidoId = pedidoId;
	}

	public Long getClienteId() {
		return clienteId;
	}

	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}
}