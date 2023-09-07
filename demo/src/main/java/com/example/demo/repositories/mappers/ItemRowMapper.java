package com.example.demo.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import com.example.demo.entities.Item;
import com.example.demo.entities.Pedido;
import com.example.demo.entities.Produto;

@Configuration
public class ItemRowMapper implements RowMapper<Item>{

    private final Produto produto;
    private final Pedido pedido;

    public ItemRowMapper(Produto produto, Pedido pedido) {
        this.produto = produto;
        this.pedido = pedido;
    }

    @Override
    @Nullable
    public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
        Item item = new Item();
        item.setId(rs.getInt("id"));
    	item.setQuantidade(rs.getInt("quantidade"));
        item.setValor_atual(rs.getDouble("valor_atual"));
        pedido.setId(rs.getInt("pedido_id"));
        item.setPedido(pedido);
        produto.setId(rs.getInt("produto_id"));
        item.setProduto(produto);

        return item;

    }

}
