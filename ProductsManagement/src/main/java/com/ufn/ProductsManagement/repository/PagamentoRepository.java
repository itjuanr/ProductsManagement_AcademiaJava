package com.ufn.ProductsManagement.repository;

import com.ufn.ProductsManagement.models.Pagamento;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
    List<Pagamento> findByStatus(String status);
    List<Pagamento> findByClienteId(Long clienteId);
}
