package com.ufn.ProductsManagement.repository;

import com.ufn.ProductsManagement.models.Fornecedor;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {
    List<Fornecedor> findByNome(String nome);
    Fornecedor findByCnpj(String cnpj);
}
