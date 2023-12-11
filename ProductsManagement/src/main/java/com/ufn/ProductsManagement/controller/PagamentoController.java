package com.ufn.ProductsManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ufn.ProductsManagement.models.Pagamento;
import com.ufn.ProductsManagement.service.PagamentoService;

import java.util.List;

@RestController
@RequestMapping("/api/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @GetMapping
    public ResponseEntity<List<Pagamento>> getAllPagamentos() {
        List<Pagamento> pagamentos = pagamentoService.findAll();
        return new ResponseEntity<>(pagamentos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pagamento> getPagamentoById(@PathVariable Long id) {
        Pagamento pagamento = pagamentoService.findById(id);
        return pagamento != null
                ? new ResponseEntity<>(pagamento, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Pagamento> createPagamento(@RequestBody Pagamento pagamento) {
        Pagamento savedPagamento = pagamentoService.save(pagamento);
        return new ResponseEntity<>(savedPagamento, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePagamento(@PathVariable Long id) {
        pagamentoService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Pagamento>> getPagamentosByStatus(@PathVariable String status) {
        List<Pagamento> pagamentos = pagamentoService.findByStatus(status);
        return new ResponseEntity<>(pagamentos, HttpStatus.OK);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Pagamento>> getPagamentosByUsuarioId(@PathVariable Long ClienteId) {
        List<Pagamento> pagamentos = pagamentoService.findByClienteId(ClienteId);
        return new ResponseEntity<>(pagamentos, HttpStatus.OK);
    }
}
