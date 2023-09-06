package com.example.demo.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import com.example.demo.entities.Pedido;
import com.example.demo.repositories.ItemRepository;

@Configuration
public class PedidoRowMapper implements RowMapper<Pedido> {

    @Autowired
    private ClienteRowMapper clienteRowMapper;
    @Autowired
    private FuncionarioRowMapper funcionarioMapper;
    @Autowired
    private ItemRepository itemRepository;

    @Override
    @Nullable
    public Pedido mapRow(ResultSet rs, int arg1) throws SQLException {

        Pedido p = new Pedido();
        p.setId(rs.getInt("id"));
        p.setData_hora(rs.getTimestamp("data_hora"));
        p.setCliente(clienteRowMapper.mapRow(rs, arg1));
        p.setTotal(rs.getBigDecimal("total"));
        p.setFuncionario(funcionarioMapper.mapRow(rs, arg1));

        p.setItems(itemRepository.findAllByIds(rs.getString("items")));

        return p;

    }

};