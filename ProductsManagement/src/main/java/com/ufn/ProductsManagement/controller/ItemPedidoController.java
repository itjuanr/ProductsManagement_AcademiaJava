package com.ufn.ProductsManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ufn.ProductsManagement.models.ItemPedido;
import com.ufn.ProductsManagement.models.Pagamento;
import com.ufn.ProductsManagement.service.ItemPedidoService;

@RestController
@RequestMapping("/api/item-pedidos")
public class ItemPedidoController {

    @Autowired
    private ItemPedidoService itemPedidoService;

    @GetMapping
    public ResponseEntity<List<ItemPedido>> getAllItemPedidos() {
        List<ItemPedido> itemPedidos = itemPedidoService.findAll();
        return new ResponseEntity<>(itemPedidos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemPedido> getItemPedidoById(@PathVariable Long id) {
        ItemPedido itemPedido = itemPedidoService.findById(id);
        return itemPedido != null ?
                new ResponseEntity<>(itemPedido, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<ItemPedido> createItemPedido(@RequestBody ItemPedido itemPedido) {
        ItemPedido newItemPedido = itemPedidoService.save(itemPedido);
        return new ResponseEntity<>(newItemPedido, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/atualizar-quantidade/{novaQuantidade}")
    public ResponseEntity<ItemPedido> atualizarQuantidade(
            @PathVariable Long id,
            @PathVariable int novaQuantidade) {
        ItemPedido updatedItemPedido = itemPedidoService.atualizarQuantidade(id, novaQuantidade);
        return new ResponseEntity<>(updatedItemPedido, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItemPedido(@PathVariable Long id) {
        itemPedidoService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/excluir-itens-do-pedido/{pedidoId}")
    public ResponseEntity<Void> excluirItensDoPedido(@PathVariable Long pedidoId) {
        itemPedidoService.excluirItensDoPedido(pedidoId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{itemPedidoId}/adicionar-pagamento")
    public ResponseEntity<ItemPedido> adicionarPagamento(
            @PathVariable Long itemPedidoId,
            @RequestBody Pagamento pagamento) {
        ItemPedido updatedItemPedido = itemPedidoService.adicionarPagamento(itemPedidoId, pagamento);
        return new ResponseEntity<>(updatedItemPedido, HttpStatus.OK);
    }
    
    
}
