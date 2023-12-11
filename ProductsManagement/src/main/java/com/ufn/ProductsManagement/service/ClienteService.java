package com.ufn.ProductsManagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufn.ProductsManagement.models.Cliente;
import com.ufn.ProductsManagement.repository.ClienteRepository;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Cliente findById(Long id) {
        return clienteRepository.findById(id).orElse(null);
    }

    public Cliente save(Cliente cliente) {
        validarCliente(cliente);
        return clienteRepository.save(cliente);
    }

    public void deleteById(Long id) {
        clienteRepository.deleteById(id);
    }

    public Cliente findByEmail(String email) {
        return clienteRepository.findByEmail(email);
    }

    public List<Cliente> findByNome(String nome) {
        return clienteRepository.findByNome(nome);
    }

    public boolean validarCliente(Cliente cliente) {
        if (!isEmailUnico(cliente.getId(), cliente.getEmail())) {
            throw new ClienteEmailDuplicadoException("Já existe um cliente com o mesmo email.");
        }
        if (!isCpfUnico(cliente.getId(), cliente.getCpf())) {
            throw new ClienteCpfDuplicadoException("Já existe um cliente com o mesmo CPF.");
        }
        return true;
    }
    
    private boolean isEmailUnico(Long clienteId, String email) {
        Cliente clienteExistente = clienteRepository.findByEmail(email);
        return clienteExistente == null || clienteExistente.getId().equals(clienteId);
    }
    
    private boolean isCpfUnico(Long clienteId, String cpf) {
        Optional<Cliente> clienteExistente = clienteRepository.findByCpf(cpf);
        return clienteExistente.isEmpty() || clienteExistente.get().getId().equals(clienteId);
    }

    public static class ClienteEmailDuplicadoException extends RuntimeException {
		private static final long serialVersionUID = 1L;
		public ClienteEmailDuplicadoException(String message) {
            super(message);
        }
    }

    public static class ClienteCpfDuplicadoException extends RuntimeException {
		private static final long serialVersionUID = 1L;
		public ClienteCpfDuplicadoException(String message) {
            super(message);
        }
    }
}
