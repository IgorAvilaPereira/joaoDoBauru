package com.example.demo.services;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Pedido;
import com.example.demo.repositories.PedidoRepository;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository repository;

    public List<Pedido> findAll() {
        return repository.findAll();
    }

    public Pedido findById(int id) throws SQLException {
        Pedido p = repository.findById(id);
        if ( p == null) {
            throw new SQLException("Pedido não encontrado");
        };
        return p;
    }
}
