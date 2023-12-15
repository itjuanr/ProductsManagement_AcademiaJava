package com.ufn.ProductsManagement.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ufn.ProductsManagement.models.Fornecedor;
import com.ufn.ProductsManagement.repository.FornecedorRepository;

import java.util.List;

@Service
public class FornecedorService {
    private static final Logger logger = LogManager.getLogger(FornecedorService.class);

    @Autowired
    private FornecedorRepository fornecedorRepository;

    public List<Fornecedor> findAll() {
        logger.info("Buscando todos os fornecedores");
        return fornecedorRepository.findAll();
    }

    public Fornecedor findById(Long id) {
        logger.info("Buscando fornecedor por ID: {}", id);
        return fornecedorRepository.findById(id).orElse(null);
    }

    public Fornecedor save(Fornecedor fornecedor) {
        logger.info("Salvando novo fornecedor: {}", fornecedor);
        validarFornecedor(fornecedor);
        return fornecedorRepository.save(fornecedor);
    }

    public void deleteById(Long id) {
        logger.info("Deletando fornecedor por ID: {}", id);
        fornecedorRepository.deleteById(id);
    }
    
    public List<Fornecedor> findByNome(String nome) {
        logger.info("Buscando fornecedores por nome: {}", nome);
        return fornecedorRepository.findByNome(nome);
    }

    public Fornecedor findByCnpj(String cnpj) {
        logger.info("Buscando fornecedor por CNPJ: {}", cnpj);
        return fornecedorRepository.findByCnpj(cnpj);
    }
    
    public boolean validarFornecedor(Fornecedor fornecedor) {
        logger.info("Validando fornecedor: {}", fornecedor);
        if (!isCnpjUnico(fornecedor.getId(), fornecedor.getCnpj())) {
            logger.error("CNPJ duplicado para o fornecedor: {}", fornecedor.getCnpj());
            throw new FornecedorCnpjDuplicadoException("Já existe um fornecedor com o mesmo CNPJ.");
        }
        return true;
    }

    private boolean isCnpjUnico(Long fornecedorId, String cnpj) {
        logger.info("Verificando se o CNPJ é único: {}", cnpj);
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
