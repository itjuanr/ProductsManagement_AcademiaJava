package com.ufn.ProductsManagement.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ufn.ProductsManagement.DTO.ClienteDTO;
import com.ufn.ProductsManagement.models.Cliente;
import com.ufn.ProductsManagement.repository.ClienteRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService {
    private static final Logger logger = LogManager.getLogger(ClienteService.class);

    @Autowired
    private ClienteRepository clienteRepository;

    public List<ClienteDTO> findAll() {
        logger.info("Buscando todos os clientes");
        List<Cliente> clientes = clienteRepository.findAll();
        return clientes.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public ClienteDTO findById(Long id) {
        logger.info("Buscando cliente por ID: {}", id);
        Cliente cliente = clienteRepository.findById(id).orElse(null);
        return (cliente != null) ? convertToDTO(cliente) : null;
    }

    public ClienteDTO save(ClienteDTO clienteDTO) {
        logger.info("Salvando novo cliente: {}", clienteDTO);
        validarClienteDTO(clienteDTO);
        Cliente cliente = convertToEntity(clienteDTO);
        Cliente savedCliente = clienteRepository.save(cliente);
        return convertToDTO(savedCliente);
    }

    public void deleteById(Long id) {
        logger.info("Deletando cliente por ID: {}", id);
        clienteRepository.deleteById(id);
    }

    public ClienteDTO findByEmail(String email) {
        logger.info("Buscando cliente por email: {}", email);
        Cliente cliente = clienteRepository.findByEmail(email);
        return (cliente != null) ? convertToDTO(cliente) : null;
    }

    public List<ClienteDTO> findByNome(String nome) {
        logger.info("Buscando clientes por nome: {}", nome);
        List<Cliente> clientes = clienteRepository.findByNome(nome);
        return clientes.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private void validarClienteDTO(ClienteDTO clienteDTO) {
        logger.info("Validando clienteDTO: {}", clienteDTO);
        if (!isEmailUnico(clienteDTO.getId(), clienteDTO.getEmail())) {
            logger.error("Email duplicado para o cliente: {}", clienteDTO.getEmail());
            throw new ClienteEmailDuplicadoException("Já existe um cliente com o mesmo email.");
        }
        if (!isCpfUnico(clienteDTO.getId(), clienteDTO.getCpf())) {
            logger.error("CPF duplicado para o cliente: {}", clienteDTO.getCpf());
            throw new ClienteCpfDuplicadoException("Já existe um cliente com o mesmo CPF.");
        }
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

    private ClienteDTO convertToDTO(Cliente cliente) {
        ClienteDTO clienteDTO = new ClienteDTO();
        BeanUtils.copyProperties(cliente, clienteDTO);
        return clienteDTO;
    }

    private Cliente convertToEntity(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente();
        BeanUtils.copyProperties(clienteDTO, cliente);
        return cliente;
    }
}
