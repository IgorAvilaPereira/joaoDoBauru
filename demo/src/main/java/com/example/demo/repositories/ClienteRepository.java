package com.example.demo.repositories;

import java.util.List;

import com.example.demo.entities.Cliente;

public interface ClienteRepository {

    public List<Cliente> listar();
    public Cliente listarUm(int id) throws IllegalArgumentException;
    public Cliente inserir(Cliente c) throws IllegalArgumentException;
    public void atualizar(Cliente c);
    public void deletar(int id);

}
