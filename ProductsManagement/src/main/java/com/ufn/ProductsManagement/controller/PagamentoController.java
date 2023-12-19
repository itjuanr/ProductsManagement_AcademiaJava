package com.ufn.ProductsManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ufn.ProductsManagement.DTO.PagamentoDTO;
import com.ufn.ProductsManagement.DTO.PagamentoRequestDTO;
import com.ufn.ProductsManagement.service.PagamentoService;

import java.util.List;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @GetMapping
    public ResponseEntity<List<PagamentoDTO>> getAllPagamentos() {
        List<PagamentoDTO> pagamentos = pagamentoService.findAll();
        return new ResponseEntity<>(pagamentos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoDTO> getPagamentoById(@PathVariable Long id) {
        PagamentoDTO pagamento = pagamentoService.findById(id);
        return pagamento != null
                ? new ResponseEntity<>(pagamento, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<PagamentoDTO> createPagamento(@RequestBody PagamentoRequestDTO pagamentoDTO) {
        PagamentoDTO savedPagamento = pagamentoService.save(pagamentoDTO);
        return new ResponseEntity<>(savedPagamento, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePagamento(@PathVariable Long id) {
        pagamentoService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/usuario/{clienteId}")
    public ResponseEntity<List<PagamentoDTO>> getPagamentosByUsuarioId(@PathVariable Long clienteId) {
        List<PagamentoDTO> pagamentos = pagamentoService.findByClienteId(clienteId);
        return new ResponseEntity<>(pagamentos, HttpStatus.OK);
    }
}
