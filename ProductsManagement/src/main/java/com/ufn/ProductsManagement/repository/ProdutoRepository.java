package com.ufn.ProductsManagement.repository;

import com.ufn.ProductsManagement.models.Produto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	List<Produto> findByCategoriaId(Long categoriaId);
    List<Produto> findByPrecoGreaterThan(double preco);
    List<Produto> findByFornecedorId(Long fornecedorId);
}
