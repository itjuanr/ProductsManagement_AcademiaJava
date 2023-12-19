package com.ufn.ProductsManagement.controller;

import com.ufn.ProductsManagement.DTO.ClienteDTO;
import com.ufn.ProductsManagement.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public List<ClienteDTO> getAllClientes() {
        return clienteService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> getClienteById(@PathVariable Long id) {
        ClienteDTO clienteDTO = clienteService.findById(id);
        return ResponseEntity.of(Optional.ofNullable(clienteDTO));
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> createCliente(@RequestBody ClienteDTO clienteDTO) {
        try {
            ClienteDTO savedClienteDTO = clienteService.save(clienteDTO);
            return new ResponseEntity<>(savedClienteDTO, HttpStatus.CREATED);
        } catch (ClienteService.ClienteEmailDuplicadoException | ClienteService.ClienteCpfDuplicadoException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        clienteService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/buscar")
    public ResponseEntity<ClienteDTO> searchClientes(@RequestParam(name = "email", required = false) String email,
                                                     @RequestParam(name = "nome", required = false) String nome) {
        if (email != null) {
            ClienteDTO clienteDTO = clienteService.findByEmail(email);
            return clienteDTO != null ? ResponseEntity.ok(clienteDTO) : ResponseEntity.notFound().build();
        } else if (nome != null) {
        }

        return ResponseEntity.badRequest().build();
    }
}
