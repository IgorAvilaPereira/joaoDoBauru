package com.example.demo.repositories.mappers;

import java.math.BigDecimal;
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

    // TODO: Seria legal se colocassemos no item todo o objeto de pedido e todo o objeto de produto
    @Override
    @Nullable
    public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
        Item item = new Item();
        item.setId(rs.getInt("id"));
    	item.setQuantidade(rs.getInt("quantidade"));
        item.setValor_atual(rs.getDouble("valor_atual_numerico"));                
        pedido.setId(rs.getInt("pedido_id"));
        item.setPedido(pedido);
        produto.setId(rs.getInt("produto_id"));
        produto.setDescricao(rs.getString("descricao"));
        produto.setEstoque(rs.getInt("estoque"));
        // produto.setValor(BigDecimal.valueOf(rs.getDouble("valor_numerico")));
        produto.setValor(rs.getBigDecimal("valor_numerico"));
        item.setProduto(produto);
        return item;

    }

}
