package com.ufn.ProductsManagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufn.ProductsManagement.models.Fornecedor;
import com.ufn.ProductsManagement.repository.FornecedorRepository;

@Service
public class FornecedorService {
    @Autowired
    private FornecedorRepository fornecedorRepository;

    public List<Fornecedor> findAll() {
        return fornecedorRepository.findAll();
    }

    public Fornecedor findById(Long id) {
        return fornecedorRepository.findById(id).orElse(null);
    }

    public Fornecedor save(Fornecedor fornecedor) {
        validarFornecedor(fornecedor);
        return fornecedorRepository.save(fornecedor);
    }

    public void deleteById(Long id) {
        fornecedorRepository.deleteById(id);
    }
    
    public List<Fornecedor> findByNome(String nome) {
        return fornecedorRepository.findByNome(nome);
    }

    public Fornecedor findByCnpj(String cnpj) {
        return fornecedorRepository.findByCnpj(cnpj);
    }
    
    public boolean validarFornecedor(Fornecedor fornecedor) {
        if (!isCnpjUnico(fornecedor.getId(), fornecedor.getCnpj())) {
            throw new FornecedorCnpjDuplicadoException("Já existe um fornecedor com o mesmo CNPJ.");
        }
        return true;
    }

    private boolean isCnpjUnico(Long fornecedorId, String cnpj) {
        Fornecedor fornecedorExistente = fornecedorRepository.findByCnpj(cnpj);
        return fornecedorExistente == null || fornecedorExistente.getId().equals(fornecedorId);
    }

    public static class FornecedorCnpjDuplicadoException extends RuntimeException {
		private static final long serialVersionUID = 1L;
		public FornecedorCnpjDuplicadoException(String message) {
            super(message);
        }
    }

}

