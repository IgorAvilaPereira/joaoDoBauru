package com.example.demo.repositories;

import java.util.List;

import com.example.demo.entities.Item;

public interface ItemRepository {

    public List<Item> listarPorPedidoId(int pedido_id);
    public Item listarUm(int id);
    public boolean inserir(Item i, int pedidoId);
    public List<Item> listarPorIds(String ids);
    public void deletar(int id);
    public Item atualizar(Item c) throws Exception;
}
