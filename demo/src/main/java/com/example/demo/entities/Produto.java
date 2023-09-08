package com.example.demo.entities;

import java.math.BigDecimal;
import java.util.Objects;

import org.springframework.stereotype.Component;

@Component
public class Produto {

    private int id;
    private String descricao;
    private BigDecimal valor;
    private int estoque;

    public Produto() {
    }

    public Produto(int id, String descricao, BigDecimal valor, int estoque) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.estoque = estoque;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return this.valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public int getEstoque() {
        return this.estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    public Produto id(int id) {
        setId(id);
        return this;
    }

    public Produto descricao(String descricao) {
        setDescricao(descricao);
        return this;
    }

    public Produto valor(BigDecimal valor) {
        setValor(valor);
        return this;
    }

    public Produto estoque(int estoque) {
        setEstoque(estoque);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Produto)) {
            return false;
        }
        Produto produto = (Produto) o;
        return id == produto.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
