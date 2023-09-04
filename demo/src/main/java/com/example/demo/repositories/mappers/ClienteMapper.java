package com.example.demo.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import com.example.demo.entities.Cliente;
import com.example.demo.entities.Endereco;

@Configuration
public class ClienteMapper implements RowMapper<Cliente> {

    @Override
    @Nullable
    public Cliente mapRow(ResultSet rs, int rowNum) throws SQLException {
        Cliente c = new Cliente();
        c.setId(rs.getInt("c_id"));
        c.setNome(rs.getString("c_nome"));
        c.setEndereco(new Endereco(
                rs.getString("c_rua"),
                rs.getString("c_bairro"),
                rs.getString("c_complemento"),
                rs.getString("c_numero"),
                rs.getString("c_cep")));
        c.setTelefone(rs.getString("c_telefone"));
        return c;
    }
}
