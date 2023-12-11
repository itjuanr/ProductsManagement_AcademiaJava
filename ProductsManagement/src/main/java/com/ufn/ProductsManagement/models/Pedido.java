package com.ufn.ProductsManagement.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> itensPedido = new ArrayList<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<ItemPedido> getItensPedido() {
        return itensPedido;
    }

    public void adicionarItemPedido(ItemPedido itemPedido, Produto produto) {
        itensPedido.add(itemPedido);
        itemPedido.setPedido(this);
        itemPedido.setProduto(produto);
    }
    
    public void removerItemPedido(ItemPedido itemPedido) {
        itensPedido.remove(itemPedido);
        itemPedido.setPedido(null);
    }

    public int getQuantidade() {
        return itensPedido.stream().mapToInt(ItemPedido::getQuantidade).sum();
    }
}
