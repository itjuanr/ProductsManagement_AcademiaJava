package com.ufn.ProductsManagement.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ufn.ProductsManagement.models.Cliente;
import com.ufn.ProductsManagement.service.ClienteService;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public List<Cliente> getAllClientes() {
        return clienteService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable Long id) {
        Cliente cliente = clienteService.findById(id);
        return ResponseEntity.of(Optional.ofNullable(cliente));
    }

    @PostMapping
    public ResponseEntity<Cliente> createCliente(@RequestBody Cliente cliente) {
        try {
            Cliente savedCliente = clienteService.save(cliente);
            return new ResponseEntity<>(savedCliente, HttpStatus.CREATED);
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
    public ResponseEntity<Cliente> searchClientes(@RequestParam(name = "email", required = false) String email,
                                                  @RequestParam(name = "nome", required = false) String nome) {
        Cliente cliente = null; 

        if (email != null) {
            cliente = clienteService.findByEmail(email);
        } else if (nome != null) {
        } else {
            return ResponseEntity.badRequest().build();
        }

        return cliente != null ? ResponseEntity.ok(cliente) : ResponseEntity.notFound().build();
    }

}