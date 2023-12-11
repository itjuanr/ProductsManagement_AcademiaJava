package com.ufn.ProductsManagement.repository;

import com.ufn.ProductsManagement.models.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
}
