package com.ufn.ProductsManagement.repository;

import com.ufn.ProductsManagement.models.ItemPedido;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
    List<ItemPedido> findByPedidoId(Long pedidoId);
    List<ItemPedido> findByProdutoId(Long produtoId);
}
