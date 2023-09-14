package com.example.demo.repositories;

import java.util.List;

import com.example.demo.entities.Pedido;

public interface PedidoRepository {

    public List<Pedido> listar();
    public Pedido listarUm(int id);
    public void deletar(int id);
    public Pedido inserir(Pedido p) throws IllegalArgumentException;
    public void atualizar(Pedido p);

}
