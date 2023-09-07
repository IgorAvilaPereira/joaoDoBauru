package com.example.demo.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import com.example.demo.entities.Item;
import com.example.demo.entities.Pedido;

@Configuration
public class PedidoRowMapper implements RowMapper<Pedido> {

    @Autowired
    private ClienteRowMapper clienteRowMapper;
    @Autowired
    private FuncionarioRowMapper funcionarioRowMapper;

    @Override
    @Nullable
    public Pedido mapRow(ResultSet rs, int arg1) throws SQLException {

        Pedido p = new Pedido();
        p.setId(rs.getInt("id"));
        p.setData_hora(rs.getTimestamp("data_hora"));
        p.setCliente(clienteRowMapper.mapRow(rs, arg1));
        p.setTotal(rs.getBigDecimal("total"));
        p.setFuncionario(funcionarioRowMapper.mapRow(rs, arg1));

        List<Item> items = new ArrayList<>();

        String [] itemsIds = rs.getString("items").split(";");

        for (int i = 0; i < itemsIds.length; i++) {
            Item item = new Item();
            item.setId(Integer.parseInt(itemsIds[i]));
            items.add(item);
        }
        p.setItems(items);

        return p;

    }

};