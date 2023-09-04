package com.example.demo.entities;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;

public class Item {

    private int id;
    private BigDecimal valor_atual;
    private int quantidade;

    @Autowired
    private Pedido pedido;
    @Autowired
    private Produto produto;

    public Item() {
    }

    public Item(int id, BigDecimal valor_atual, int quantidade, Pedido pedido, Produto produto) {
        this.id = id;
        this.valor_atual = valor_atual;
        this.quantidade = quantidade;
        this.pedido = pedido;
        this.produto = produto;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getValor_atual() {
        return this.valor_atual;
    }

    public void setValor_atual(BigDecimal valor_atual) {
        this.valor_atual = valor_atual;
    }

    public int getQuantidade() {
        return this.quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Pedido getPedido() {
        return this.pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Produto getProduto() {
        return this.produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Item id(int id) {
        setId(id);
        return this;
    }

    public Item valor_atual(BigDecimal valor_atual) {
        setValor_atual(valor_atual);
        return this;
    }

    public Item quantidade(int quantidade) {
        setQuantidade(quantidade);
        return this;
    }

    public Item pedido(Pedido pedido) {
        setPedido(pedido);
        return this;
    }

    public Item produto(Produto produto) {
        setProduto(produto);
        return this;
    }

}