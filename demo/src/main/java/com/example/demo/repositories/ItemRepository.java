package com.example.demo.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Item;
import com.example.demo.entities.Pedido;
import com.example.demo.entities.Produto;
import com.example.demo.repositories.mappers.ItemRowMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ItemRowMapper itemRowMapper;

    public List<Item> findByPedidoId(int pedido_id) {
        String sql = """
                    SELECT *, 
                    cast(valor_atual as numeric(8,2)) as valor_atual_numerico,
                    cast(valor as numeric(8,2)) as valor_numerico                    
                    
                    from item inner join produto on (produto.id = item.produto_id) where pedido_id = ?

                """;
        final List<Item> items = jdbcTemplate.query(sql, itemRowMapper, pedido_id);
        return items;
    }

    public Item findOne(int id) {
      String sql = """
                    SELECT *, 
                    cast(valor_atual as numeric(8,2)) as valor_atual_numerico,
                    cast(valor as numeric(8,2)) as valor_numerico                    
                    
                    from item inner join produto on (produto.id = item.produto_id) where item.id = ?

                """;

        Item item = jdbcTemplate.queryForObject(sql, itemRowMapper, id);

        return item;
    }

    public List<Item> findAllByIds(String ids) {
        String sql = """
                SELECT * FROM item where
                """;

        String[] itemsIds = ids.split(",");

        for (int i = 0; i < itemsIds.length; i++) {
            if (i < itemsIds.length - 1) {
                sql += "id = " + itemsIds[i] + " or ";
                continue;
            }
            sql += "id = " + itemsIds[i];
        }

        List<Map<String, Object>> itemsMaped = jdbcTemplate.queryForList(sql);
        List<Item> items = new ArrayList<Item>();

        itemsMaped.forEach(map -> {
            Item item = new Item();
            map.forEach((col, val) -> {
                System.out.println(col + " " + val.toString());
                if (col.equals("id"))
                    item.setId((Integer) val);
                if (col.equals("valor_atual"))
                    item.setValor_atual((Double) val);
                if (col.equals("quantidade"))
                    item.setQuantidade((Integer) val);
                if (col.equals("pedido_id")) {
                    Pedido p = new Pedido();
                    p.setId((Integer) val);
                    item.setPedido(p);
                }
                if (col.equals("produto_id")) {
                    Produto p = new Produto();
                    p.setId((Integer) val);
                    item.setProduto(p);
                }
            });

            items.add(item);
        }

        );
        return items;

    }

}
