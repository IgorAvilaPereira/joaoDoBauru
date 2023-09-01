package com.example.demo.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Cliente;

@Repository
public class ClienteRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Cliente> findAll(){
        String sql = "SELECT * FROM cliente";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Cliente(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("telefone"),
                        rs.getString("rua"),
                        rs.getString("bairro"),
                        rs.getString("numero"),
                        rs.getString("complemento"),
                        rs.getString("cep")));
    };

}
