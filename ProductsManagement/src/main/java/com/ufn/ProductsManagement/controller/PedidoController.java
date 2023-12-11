package com.ufn.ProductsManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ufn.ProductsManagement.PedidoDTO;
import com.ufn.ProductsManagement.service.PedidoService;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<List<PedidoDTO>> getAllPedidos() {
        List<PedidoDTO> pedidos = pedidoService.getAllPedidos();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> getPedidoById(@PathVariable Long id) {
        PedidoDTO pedido = pedidoService.getPedidoDtoById(id);
        return ResponseEntity.ok(pedido);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePedido(@PathVariable Long id) {
        pedidoService.deletePedido(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<PedidoDTO> createPedido(@RequestBody CriarPedidoRequest criarPedidoRequest) {
        PedidoDTO pedido = pedidoService.createPedido(criarPedidoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro na requisição: " + ex.getMessage());
    }

}
