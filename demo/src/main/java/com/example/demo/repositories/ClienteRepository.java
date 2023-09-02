package com.example.demo.repositories;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Cliente;
import com.example.demo.repositories.mappers.ClienteMapper;

@Repository
public class ClienteRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Cliente> findAll() {
        String sql = "SELECT * FROM cliente;";
        return jdbcTemplate.query(sql, new ClienteMapper());
    };

    public Cliente findById(Integer id) {
        String sql = "SELECT * FROM cliente WHERE id = ?";
        Cliente cliente = jdbcTemplate.queryForObject(sql, new ClienteMapper(), id);
        return cliente;
    };

}
