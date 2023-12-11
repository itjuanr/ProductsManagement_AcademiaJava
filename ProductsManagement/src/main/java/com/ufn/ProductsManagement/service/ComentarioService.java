package com.ufn.ProductsManagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ufn.ProductsManagement.models.Comentario;
import com.ufn.ProductsManagement.models.Produto;
import com.ufn.ProductsManagement.repository.ComentarioRepository;
import com.ufn.ProductsManagement.repository.ProdutoRepository;

@Service
public class ComentarioService {
    @Autowired
    private ComentarioRepository comentarioRepository;
    
    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Comentario> findAll() {
        return comentarioRepository.findAll();
    }

    public Comentario findById(Long id) {
        return comentarioRepository.findById(id).orElse(null);
    }

    public Comentario save(Comentario comentario, Long produtoId) {
        Optional<Produto> produtoOptional = produtoRepository.findById(produtoId);
        if (produtoOptional.isEmpty()) {
            throw new ProdutoNaoEncontradoException("Produto não encontrado para o ID: " + produtoId);
        }
        Produto produto = produtoOptional.get();
        comentario.setProduto(produto);

        return comentarioRepository.save(comentario);
    }

    public static class ProdutoNaoEncontradoException extends RuntimeException {
		private static final long serialVersionUID = 1L;
		public ProdutoNaoEncontradoException(String message) {
            super(message);
        }
    }

    public void deleteById(Long id) {
        try {
            comentarioRepository.deleteById(id);
        } catch (Exception e) {
            throw new ComentarioNaoEncontradoException("Comentário não encontrado para o ID: " + id);
        }
    }
    
    public static class ComentarioNaoEncontradoException extends RuntimeException {
		private static final long serialVersionUID = 1L;
		public ComentarioNaoEncontradoException(String message) {
            super(message);
        }
    }
    
    public Comentario updateComentario(Long comentarioId, String novoTexto) {
        Optional<Comentario> comentarioOptional = comentarioRepository.findById(comentarioId);
        if (comentarioOptional.isEmpty()) {
            throw new ComentarioNaoEncontradoException("Comentário não encontrado para o ID: " + comentarioId);
        }

        Comentario comentario = comentarioOptional.get();
        comentario.setTexto(novoTexto);

        return comentarioRepository.save(comentario);
    }
    
}

