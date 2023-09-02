package com.example.demo.repositories;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.asm.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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
    }

    public Cliente insertCliente(Cliente c) {
        String sql = """
                INSERT INTO cliente (nome, cpf,telefone,rua,bairro,numero,complemento,cep)
                VALUES (?, ?,?,?,?,?,?,?)
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        int insertsCount = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(sql, new String[] { "id" });
            ps.setString(1, c.getNome());
            ps.setString(2, c.getCpf());
            ps.setString(3, c.getTelefone());
            ps.setString(4, c.getRua());
            ps.setString(5, c.getBairro());
            ps.setString(6, c.getNumero());
            ps.setString(7, c.getComplemento());
            ps.setString(8, c.getCep());

            return ps;
        }, keyHolder);

        if (insertsCount == 1) {
            Number key = keyHolder.getKey();
            c.setId( (Integer) key);
        }
        return c;
    };

}
