package com.example.demo.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;

@Component
public class Pedido {
    private int id;
    private Timestamp data_hora;

    private Cliente cliente;
    private Funcionario funcionario;
    private BigDecimal total;

    private List<Item> items;

    public Pedido() {
    }

    public Pedido(int id, Timestamp data_hora, Cliente cliente, Funcionario funcionario, BigDecimal total) {
        this.id = id;
        this.data_hora = data_hora;
        this.cliente = cliente;
        this.funcionario = funcionario;
        this.total = total;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getData_hora() {
        return this.data_hora;
    }

    public void setData_hora(Timestamp data_hora) {
        this.data_hora = data_hora;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Funcionario getFuncionario() {
        return this.funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Pedido id(int id) {
        setId(id);
        return this;
    }

    public Pedido data_hora(Timestamp data_hora) {
        setData_hora(data_hora);
        return this;
    }

    public Pedido cliente(Cliente cliente) {
        setCliente(cliente);
        return this;
    }

    public Pedido funcionario(Funcionario funcionario) {
        setFuncionario(funcionario);
        return this;
    }

    public BigDecimal getTotal() {
        return this.total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public List<Item> getItems() {
        return this.items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Pedido)) {
            return false;
        }
        Pedido pedido = (Pedido) o;
        return id == pedido.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
