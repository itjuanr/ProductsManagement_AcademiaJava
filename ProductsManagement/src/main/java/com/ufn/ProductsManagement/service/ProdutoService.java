package com.ufn.ProductsManagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufn.ProductsManagement.DTO.ProdutoDTO;
import com.ufn.ProductsManagement.models.Categoria;
import com.ufn.ProductsManagement.models.Fornecedor;
import com.ufn.ProductsManagement.models.Produto;
import com.ufn.ProductsManagement.repository.ProdutoRepository;

import jakarta.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class ProdutoService {
	private static final Logger logger = LogManager.getLogger(ProdutoService.class);

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private CategoriaService categoriaService;

	@Autowired
	private FornecedorService fornecedorService;

	public List<Produto> findAll() {
		logger.info("Buscando todos os produtos");
		return produtoRepository.findAll();
	}

	public Produto findById(Long id) {
		logger.info("Buscando produto por ID: {}", id);
		return produtoRepository.findById(id).orElse(null);
	}

	public void deleteById(Long id) {
		logger.info("Deletando produto por ID: {}", id);
		produtoRepository.deleteById(id);
	}

	public List<Produto> findByCategoria(Long categoriaId) {
		logger.info("Buscando produtos por ID de categoria: {}", categoriaId);
		return produtoRepository.findByCategoriaId(categoriaId);
	}

	public List<Produto> buscarProdutosComPrecoMaiorQue(double preco) {
		logger.info("Buscando produtos com preço maior que: {}", preco);
		return produtoRepository.findByPrecoGreaterThan(preco);
	}

	@Transactional
	public ProdutoDTO save(ProdutoDTO produtoDTO, Long categoriaId, Long fornecedorId) {
		logger.info("Salvando produto com DTO: {}, Categoria ID: {}, Fornecedor ID: {}", produtoDTO, categoriaId,
				fornecedorId);

		Categoria categoria = categoriaService.findById(categoriaId);
		Fornecedor fornecedor = fornecedorService.findById(fornecedorId);

		if (categoria != null && fornecedor != null) {
			Produto produto = new Produto();
			produto.setId(produtoDTO.getId());
			produto.setNome(produtoDTO.getNome());
			produto.setPreco(produtoDTO.getPreco());
			produto.setCategoria(categoria);
			produto.setFornecedor(fornecedor);

			logger.info("Produto antes de salvar: {}", produto);
			Produto savedProduto = produtoRepository.save(produto);
			logger.info("Produto após salvar: {}", savedProduto);

			return toDTO(savedProduto);

		} else {
			logger.error("Categoria ou fornecedor não encontrados para os IDs informados.");
			throw new CategoriaOuFornecedorNaoEncontradoException(
					"Categoria ou fornecedor não encontrados para os IDs informados.");
		}
	}

	public ProdutoDTO toDTO(Produto produto) {
		logger.info("Convertendo Produto para ProdutoDTO: {}", produto);
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

	public List<Produto> findByFornecedorId(Long fornecedorId) {
		logger.info("Buscando produtos por ID de fornecedor: {}", fornecedorId);
		return produtoRepository.findByFornecedorId(fornecedorId);
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
}
