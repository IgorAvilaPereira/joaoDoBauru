package com.example.demo.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import com.example.demo.entities.Produto;

@Configuration
public class ProdutoRowMapper implements RowMapper<Produto> {

    @Override
    @Nullable
    public Produto mapRow(ResultSet rs, int rowNum) throws SQLException {
        
        Produto p = new Produto();
        p.setId(rs.getInt("id"));
        p.setDescricao(rs.getString("descricao"));
        p.setEstoque(rs.getInt("estoque"));
        // TODO: ACERTAR CONVERSÃO MONEY PARA VALOR na classe produto!
        p.setValor(rs.getBigDecimal("valor"));
        p.setAtivo(rs.getBoolean("p_ativo"));

        return p;
    
    }
    
}
