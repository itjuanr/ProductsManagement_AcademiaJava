
package com.ufn.ProductsManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ufn.ProductsManagement.DTO.CriarItemPedidoRequestDTO;
import com.ufn.ProductsManagement.DTO.ItemPedidoDTO;
import com.ufn.ProductsManagement.service.ItemPedidoService;

@RestController
@RequestMapping("/api/item-pedidos")
public class ItemPedidoController {

	@Autowired
	private ItemPedidoService itemPedidoService;

	@GetMapping
	public ResponseEntity<List<ItemPedidoDTO>> getAllItemPedidos() {
		List<ItemPedidoDTO> itemPedidos = itemPedidoService.getAllItemPedidos(null);
		return new ResponseEntity<>(itemPedidos, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ItemPedidoDTO> getItemPedidoById(@PathVariable Long id) {
		ItemPedidoDTO itemPedido = itemPedidoService.getItemPedidoById(id);
		return itemPedido != null ? new ResponseEntity<>(itemPedido, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping
	public ResponseEntity<ItemPedidoDTO> createItemPedido(
			@RequestBody CriarItemPedidoRequestDTO criarItemPedidoRequest) {
		try {
			ItemPedidoDTO newItemPedido = itemPedidoService.createItemPedido(criarItemPedidoRequest);
			return new ResponseEntity<>(newItemPedido, HttpStatus.CREATED);
		} catch (ItemPedidoService.PedidoNaoEncontradoException | ItemPedidoService.PagamentoNaoEncontradoException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

}
