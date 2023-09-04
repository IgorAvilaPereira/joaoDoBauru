package com.example.demo.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import com.example.demo.entities.Funcionario;
import com.example.demo.entities.Pedido;

@Configuration
public class PedidoMapper implements RowMapper<Pedido> {

    private final ClienteMapper clienteRowMapper;

    PedidoMapper(ClienteMapper clienteRowMapper) {
        this.clienteRowMapper = clienteRowMapper;
    }

    @Override
    @Nullable
    public Pedido mapRow(ResultSet rs, int arg1) throws SQLException {

        Pedido p = new Pedido();
        p.setId(rs.getInt("id"));
        p.setData_hora(rs.getTimestamp("data_hora"));
        p.setCliente( clienteRowMapper.mapRow(rs, arg1));
        p.setTotal(rs.getBigDecimal("total"));
        p.setFuncionario(
                new Funcionario(
                        rs.getInt("f_id"),
                        rs.getString("f_nome"),
                        rs.getString("f_cpf"),
                        rs.getString("f_endereco"),
                        rs.getString("f_telefone")));
        return p;

    }

};