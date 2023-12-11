package com.ufn.ProductsManagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ufn.ProductsManagement.models.Categoria;
import com.ufn.ProductsManagement.repository.CategoriaRepository;

@Service
public class CategoriaService {
	@Autowired
	private CategoriaRepository categoriaRepository;

	public List<Categoria> findAll() {
		return categoriaRepository.findAll();
	}

	public Categoria findById(Long id) {
		return categoriaRepository.findById(id).orElse(null);
	}

	public Categoria save(Categoria categoria) {
		return categoriaRepository.save(categoria);
	}

	public void deleteById(Long id) {
		categoriaRepository.deleteById(id);
	}

	public Optional<Categoria> findByDescricao(String descricao) {
		return categoriaRepository.findByDescricao(descricao);
	}

	public List<Categoria> buscarCategoriasComQuantidadeProdutosMaiorQue(int quantidade) {
		return categoriaRepository.findByQuantidadeProdutosGreaterThan(quantidade);
	}

	public List<Categoria> buscarCategoriasOrdenadasPorNome() {
		return categoriaRepository.findByOrderByNome();
	}

	public Categoria updateCategoria(Long id, Categoria novaCategoria) {
		Categoria categoriaExistente = findById(id);

		if (categoriaExistente != null) {
			categoriaExistente.setNome(novaCategoria.getNome());
			categoriaExistente.setDescricao(novaCategoria.getDescricao());

			return save(categoriaExistente);
		} else {
			throw new CategoriaNaoEncontradaException("Categoria não encontrada para o ID: " + id);
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
