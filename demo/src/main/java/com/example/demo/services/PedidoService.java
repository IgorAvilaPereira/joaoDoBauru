package com.example.demo.services;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Pedido;
import com.example.demo.repositories.PedidoRepositoryImpl;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepositoryImpl repository;

    public List<Pedido> findAll() {
        return repository.listar();
    }

    public Pedido findById(int id) throws SQLException {
        Pedido p = repository.listarUm(id);
        if ( p == null) {
            throw new SQLException("Pedido não encontrado");
        };
        return p;
    }
}
