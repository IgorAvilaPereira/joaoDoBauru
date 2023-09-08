package com.example.demo.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import com.example.demo.entities.Cliente;

@Configuration
public class ClienteRowMapper implements RowMapper<Cliente> {

    @Autowired
    private EnderecoRowMapper enderecoRowMapper;

    @Override
    @Nullable
    public Cliente mapRow(ResultSet rs, int rowNum) throws SQLException {
        Cliente c = new Cliente();
        c.setId(rs.getInt("c_id"));
        c.setNome(rs.getString("c_nome"));
        c.setEndereco(enderecoRowMapper.mapRow(rs, rowNum));
        c.setTelefone(rs.getString("c_telefone"));
        c.setAtivo(rs.getBoolean("ativo"));
        return c;
    }
}
