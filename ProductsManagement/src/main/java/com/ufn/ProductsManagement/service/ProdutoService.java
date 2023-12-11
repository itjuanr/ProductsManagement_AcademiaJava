package com.ufn.ProductsManagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufn.ProductsManagement.ProdutoDTO;
import com.ufn.ProductsManagement.models.Categoria;
import com.ufn.ProductsManagement.models.Fornecedor;
import com.ufn.ProductsManagement.models.Produto;
import com.ufn.ProductsManagement.repository.ProdutoRepository;

import jakarta.transaction.Transactional;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;
    
    @Autowired
    private CategoriaService categoriaService;
    
    @Autowired
    private FornecedorService fornecedorService;

    public List<Produto> findAll() {
        return produtoRepository.findAll();
    }

    public Produto findById(Long id) {
        return produtoRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        produtoRepository.deleteById(id);
    }
    
    public List<Produto> findByCategoria(Long categoriaId) {
        return produtoRepository.findByCategoriaId(categoriaId);
    }
    
    public List<Produto> buscarProdutosComPrecoMaiorQue(double preco) {
        return produtoRepository.findByPrecoGreaterThan(preco);
    }
    
    public static class ProdutoNaoEncontradoException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public ProdutoNaoEncontradoException() {
            super();
        }

        public ProdutoNaoEncontradoException(String message) {
            super(message);
        }

        public ProdutoNaoEncontradoException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    
    @Transactional
    public ProdutoDTO save(ProdutoDTO produtoDTO, Long categoriaId, Long fornecedorId) {
    	
        Categoria categoria = categoriaService.findById(categoriaId);
        Fornecedor fornecedor = fornecedorService.findById(fornecedorId);

        if (categoria != null && fornecedor != null) {
            Produto produto = new Produto();
            produto.setId(produtoDTO.getId());
            produto.setNome(produtoDTO.getNome());
            produto.setPreco(produtoDTO.getPreco());
            produto.setCategoria(categoria);
            produto.setFornecedor(fornecedor);
            
            System.out.println("Produto antes de salvar: " + produto);
            Produto savedProduto = produtoRepository.save(produto);
            System.out.println("Produto após salvar: " + savedProduto);
            
            return toDTO(savedProduto);
            
        } else {
            throw new CategoriaOuFornecedorNaoEncontradoException("Categoria ou fornecedor não encontrados para os IDs informados.");
        }
    }
    
    public ProdutoDTO toDTO(Produto produto) {
        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setId(produto.getId());
        produtoDTO.setNome(produto.getNome());
        produtoDTO.setPreco(produto.getPreco());

        if (produto.getCategoria() != null) {
            produtoDTO.setCategoriaId(produto.getCategoria().getId());
        } else {
            produtoDTO.setCategoriaId(null); 
        }

        if (produto.getFornecedor() != null) {
            produtoDTO.setFornecedorId(produto.getFornecedor().getId());
        } else {
            produtoDTO.setFornecedorId(null);
        }

        return produtoDTO;
    }


    
    public class CategoriaOuFornecedorNaoEncontradoException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public CategoriaOuFornecedorNaoEncontradoException() {
            super();
        }

        public CategoriaOuFornecedorNaoEncontradoException(String message) {
            super(message);
        }

        public CategoriaOuFornecedorNaoEncontradoException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public List<Produto> findByFornecedorId(Long fornecedorId) {
        return produtoRepository.findByFornecedorId(fornecedorId);
    }
}
