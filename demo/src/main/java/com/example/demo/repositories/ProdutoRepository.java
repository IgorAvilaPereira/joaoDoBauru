package com.example.demo.repositories;

import java.util.List;

import com.example.demo.entities.Produto;

public interface ProdutoRepository {

    public List<Produto> listar();
    public Produto listarUm(int id);
    public Produto inserir(Produto c);
    public void atualizar(Produto c);
    public void deletar(int id);

}
