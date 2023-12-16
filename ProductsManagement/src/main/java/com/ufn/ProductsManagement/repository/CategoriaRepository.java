package com.ufn.ProductsManagement.repository;

import com.ufn.ProductsManagement.models.Categoria;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    List<Categoria> findByNome(String nome);

    Optional<Categoria> findByDescricao(String descricao);

    List<Categoria> findByOrderByNome();

}

