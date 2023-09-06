package com.example.demo.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import com.example.demo.entities.Endereco;

@Configuration
public class EnderecoRowMapper implements RowMapper<Endereco> {

    @Override
    @Nullable
    public Endereco mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Endereco(
                rs.getString("c_rua"),
                rs.getString("c_bairro"),
                rs.getString("c_complemento"),
                rs.getString("c_numero"),
                rs.getString("c_cep"));
    }
}
