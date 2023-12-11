package com.ufn.ProductsManagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufn.ProductsManagement.models.Pagamento;
import com.ufn.ProductsManagement.repository.PagamentoRepository;

@Service
public class PagamentoService {
    @Autowired
    private PagamentoRepository pagamentoRepository;

    public List<Pagamento> findAll() {
        return pagamentoRepository.findAll();
    }

    public Pagamento findById(Long id) {
        return pagamentoRepository.findById(id).orElse(null);
    }

    public Pagamento save(Pagamento pagamento) {
        if (pagamento.getValor() <= 0) {
            throw new PagamentoInvalidoException("O valor do pagamento deve ser maior que zero.");
        }

        pagamento.setStatus("Aprovado");
        return pagamentoRepository.save(pagamento);
    }


    public void deleteById(Long id) {
        pagamentoRepository.deleteById(id);
    }

    public List<Pagamento> findByStatus(String status) {
        return pagamentoRepository.findByStatus(status);
    }

    public List<Pagamento> findByClienteId(Long ClienteId) {
        return pagamentoRepository.findByClienteId(ClienteId);
    }

    public static class PagamentoInvalidoException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public PagamentoInvalidoException(String message) {
            super(message);
        }
    }
}


