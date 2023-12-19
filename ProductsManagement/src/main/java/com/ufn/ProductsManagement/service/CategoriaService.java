package com.ufn.ProductsManagement.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ufn.ProductsManagement.models.Categoria;
import com.ufn.ProductsManagement.repository.CategoriaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {
    private static final Logger logger = LogManager.getLogger(CategoriaService.class);

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> findAll() {
        logger.info("Buscando todas as categorias");
        return categoriaRepository.findAll();
    }

    public Categoria findById(Long id) {
        logger.info("Buscando categoria por ID: {}", id);
        return categoriaRepository.findById(id).orElse(null);
    }

    public Categoria save(Categoria categoria) {
        logger.info("Salvando nova categoria: {}", categoria);

        Optional<Categoria> categoriaExistente = findByDescricao(categoria.getDescricao());
        if (categoriaExistente.isPresent()) {
            logger.error("Já existe uma categoria com a descrição: {}", categoria.getDescricao());
            throw new CategoriaExistenteException("Já existe uma categoria com a descrição: " + categoria.getDescricao());
        }

        return categoriaRepository.save(categoria);
    }

    public void deleteById(Long id) {
        logger.info("Deletando categoria por ID: {}", id);
        categoriaRepository.deleteById(id);
    }

    public Optional<Categoria> findByDescricao(String descricao) {
        logger.info("Buscando categoria por descrição: {}", descricao);
        return categoriaRepository.findByDescricao(descricao);
    }

    public Categoria updateCategoria(Long id, Categoria novaCategoria) {
        logger.info("Atualizando categoria com ID: {}. Nova categoria: {}", id, novaCategoria);
        Categoria categoriaExistente = findById(id);

        if (categoriaExistente != null) {
            categoriaExistente.setNome(novaCategoria.getNome());
            categoriaExistente.setDescricao(novaCategoria.getDescricao());

            return save(categoriaExistente);
        } else {
            logger.error("Categoria não encontrada para o ID: {}", id);
            throw new CategoriaNaoEncontradaException("Categoria não encontrada para o ID: " + id);
        }
    }

    public class CategoriaExistenteException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public CategoriaExistenteException() {
            super();
        }

        public CategoriaExistenteException(String message) {
            super(message);
        }

        public CategoriaExistenteException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public class CategoriaNaoEncontradaException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public CategoriaNaoEncontradaException() {
            super();
        }

        public CategoriaNaoEncontradaException(String message) {
            super(message);
        }

        public CategoriaNaoEncontradaException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
