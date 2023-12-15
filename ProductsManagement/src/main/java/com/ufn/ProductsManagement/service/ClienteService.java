package com.ufn.ProductsManagement.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ufn.ProductsManagement.models.Cliente;
import com.ufn.ProductsManagement.repository.ClienteRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
	private static final Logger logger = LogManager.getLogger(ClienteService.class);

	@Autowired
	private ClienteRepository clienteRepository;

	public List<Cliente> findAll() {
		logger.info("Buscando todos os clientes");
		return clienteRepository.findAll();
	}

	public Cliente findById(Long id) {
		logger.info("Buscando cliente por ID: {}", id);
		return clienteRepository.findById(id).orElse(null);
	}

	public Cliente save(Cliente cliente) {
		logger.info("Salvando novo cliente: {}", cliente);
		validarCliente(cliente);
		return clienteRepository.save(cliente);
	}

	public void deleteById(Long id) {
		logger.info("Deletando cliente por ID: {}", id);
		clienteRepository.deleteById(id);
	}

	public Cliente findByEmail(String email) {
		logger.info("Buscando cliente por email: {}", email);
		return clienteRepository.findByEmail(email);
	}

	public List<Cliente> findByNome(String nome) {
		logger.info("Buscando clientes por nome: {}", nome);
		return clienteRepository.findByNome(nome);
	}

	public boolean validarCliente(Cliente cliente) {
		logger.info("Validando cliente: {}", cliente);
		if (!isEmailUnico(cliente.getId(), cliente.getEmail())) {
			logger.error("Email duplicado para o cliente: {}", cliente.getEmail());
			throw new ClienteEmailDuplicadoException("Já existe um cliente com o mesmo email.");
		}
		if (!isCpfUnico(cliente.getId(), cliente.getCpf())) {
			logger.error("CPF duplicado para o cliente: {}", cliente.getCpf());
			throw new ClienteCpfDuplicadoException("Já existe um cliente com o mesmo CPF.");
		}
		return true;
	}

	private boolean isEmailUnico(Long clienteId, String email) {
		logger.info("Verificando se o email é único: {}", email);
		Cliente clienteExistente = clienteRepository.findByEmail(email);
		return clienteExistente == null || clienteExistente.getId().equals(clienteId);
	}

	private boolean isCpfUnico(Long clienteId, String cpf) {
		logger.info("Verificando se o CPF é único: {}", cpf);
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
