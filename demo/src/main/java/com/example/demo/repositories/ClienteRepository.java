package com.example.demo.repositories;

import java.sql.ResultSet;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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
        return jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<Cliente>(Cliente.class));
    };

    public Optional<Cliente> findById(Integer id) {
        String sql = "SELECT * FROM cliente WHERE id = ?";
        Optional<Cliente> cliente = jdbcTemplate.query(sql, new Object[]{id}, new int[]{Types.INTEGER}, new ClienteMapper()).stream().findFirst();
        return cliente;
    };

}
