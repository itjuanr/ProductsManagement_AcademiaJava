package com.ufn.ProductsManagement.DTO;

import java.math.BigDecimal;

public class PagamentoRequestDTO {

    private BigDecimal precoPago;
    private Long pedidoId;

    public BigDecimal getPrecoPago() {
        return precoPago;
    }

    public void setPrecoPago(BigDecimal precoPago) {
        this.precoPago = precoPago;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }
}
