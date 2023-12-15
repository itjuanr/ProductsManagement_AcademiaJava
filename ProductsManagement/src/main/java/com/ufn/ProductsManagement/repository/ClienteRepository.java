package com.ufn.ProductsManagement.repository;

import com.ufn.ProductsManagement.models.Cliente;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Cliente findByEmail(String email);
    Optional<Cliente> findByCpf(String cpf);
    List<Cliente> findByNome(String nome);
}