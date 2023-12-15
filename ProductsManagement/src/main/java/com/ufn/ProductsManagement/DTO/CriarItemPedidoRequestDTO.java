
package com.ufn.ProductsManagement.DTO;

public class CriarItemPedidoRequestDTO {

	private Long pedidoId;
	private Long pagamentoId;

	public Long getPedidoId() {
		return pedidoId;
	}

	public void setPedidoId(Long pedidoId) {
		this.pedidoId = pedidoId;
	}

	public Long getPagamentoId() {
		return pagamentoId;
	}

	public void setPagamentoId(Long pagamentoId) {
		this.pagamentoId = pagamentoId;
	}
}
