package com.ufn.ProductsManagement.controller;

import com.ufn.ProductsManagement.DTO.CriarPedidoRequestDTO;
import com.ufn.ProductsManagement.DTO.PedidoDTO;
import com.ufn.ProductsManagement.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
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

    @PutMapping("/{id}/quantidade")
    public ResponseEntity<PedidoDTO> updatePedidoQuantidade(@PathVariable Long id,
                                                            @RequestParam(value = "novaQuantidade", required = false) Integer novaQuantidade) {
        PedidoDTO pedido = pedidoService.updatePedidoQuantidade(id, novaQuantidade);
        return ResponseEntity.ok(pedido);
    }

    @PutMapping("/{id}/cliente")
    public ResponseEntity<PedidoDTO> updatePedidoCliente(@PathVariable Long id,
                                                        @RequestParam(value = "novoIdCliente", required = false) Long novoIdCliente) {
        PedidoDTO pedido = pedidoService.updatePedidoCliente(id, novoIdCliente);
        return ResponseEntity.ok(pedido);
    }

    @PutMapping("/{id}/produto")
    public ResponseEntity<PedidoDTO> updatePedidoProduto(@PathVariable Long id,
                                                        @RequestParam(value = "novoIdProduto", required = false) Long novoIdProduto) {
        PedidoDTO pedido = pedidoService.updatePedidoProduto(id, novoIdProduto);
        return ResponseEntity.ok(pedido);
    }

    @PostMapping
    public ResponseEntity<PedidoDTO> createPedido(@RequestBody CriarPedidoRequestDTO criarPedidoRequest) {
        PedidoDTO pedido = pedidoService.createPedido(criarPedidoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro na requisição: " + ex.getMessage());
    }
}
