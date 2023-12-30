package com.ufn.ProductsManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ufn.ProductsManagement.DTO.ProdutoDTO;
import com.ufn.ProductsManagement.models.Produto;
import com.ufn.ProductsManagement.service.ProdutoService;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> getAllProdutos() {
        List<Produto> produtos = produtoService.findAll();
        List<ProdutoDTO> produtosDTO = produtos.stream().map(produtoService::toDTO).collect(Collectors.toList());
        return new ResponseEntity<>(produtosDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> getProdutoById(@PathVariable Long id) {
        Produto produto = produtoService.findById(id);
        if (produto != null) {
            ProdutoDTO produtoDTO = produtoService.toDTO(produto);
            return new ResponseEntity<>(produtoDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/categoria/{categoriaId}/fornecedor/{fornecedorId}")
    public ResponseEntity<ProdutoDTO> createProduto(@RequestBody ProdutoDTO produtoDTO, 
                                                    @PathVariable Long categoriaId, 
                                                    @PathVariable Long fornecedorId) {
        ProdutoDTO savedProdutoDTO = produtoService.save(produtoDTO, categoriaId, fornecedorId);
        return new ResponseEntity<>(savedProdutoDTO, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDTO> updateProduto(@PathVariable Long id,
                                                    @RequestBody ProdutoDTO produtoDTO) {
        ProdutoDTO updatedProdutoDTO = produtoService.update(id, produtoDTO);
        return new ResponseEntity<>(updatedProdutoDTO, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable Long id) {
        produtoService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<ProdutoDTO>> getProdutosByCategoria(@PathVariable Long categoriaId) {
        List<Produto> produtos = produtoService.findByCategoria(categoriaId);
        List<ProdutoDTO> produtosDTO = produtos.stream().map(produtoService::toDTO).collect(Collectors.toList());
        return new ResponseEntity<>(produtosDTO, HttpStatus.OK);
    }

    @GetMapping("/preco-maior-que/{preco}")
    public ResponseEntity<List<ProdutoDTO>> getProdutosComPrecoMaiorQue(@PathVariable double preco) {
        List<Produto> produtos = produtoService.buscarProdutosComPrecoMaiorQue(preco);
        List<ProdutoDTO> produtosDTO = produtos.stream().map(produtoService::toDTO).collect(Collectors.toList());
        return new ResponseEntity<>(produtosDTO, HttpStatus.OK);
    }

    @GetMapping("/por-fornecedor/{fornecedorId}")
    public ResponseEntity<List<ProdutoDTO>> getProdutosPorFornecedor(@PathVariable Long fornecedorId) {
        List<Produto> produtos = produtoService.findByFornecedorId(fornecedorId);
        List<ProdutoDTO> produtosDTO = produtos.stream().map(produtoService::toDTO).collect(Collectors.toList());
        return new ResponseEntity<>(produtosDTO, HttpStatus.OK);
    }
}
