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
public class ItemRepositoryImpl implements ItemRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ItemRowMapper itemRowMapper;

    public List<Item> listarPorPedidoId(int pedido_id) {
        String sql = """
                    SELECT *,
                    cast(valor_atual as numeric(8,2)) as valor_atual_numerico,
                    cast(valor as numeric(8,2)) as valor_numerico

                    from item inner join produto on (produto.id = item.produto_id) where pedido_id = ?

                """;
        final List<Item> items = jdbcTemplate.query(sql, itemRowMapper, pedido_id);
        return items;
    }

    public Item listarUm(int id) {
        String sql = """
                    SELECT *,
                    cast(valor_atual as numeric(8,2)) as valor_atual_numerico,
                    cast(valor as numeric(8,2)) as valor_numerico

                    from item inner join produto on (produto.id = item.produto_id) where item.id = ?

                """;

        Item item = jdbcTemplate.queryForObject(sql, itemRowMapper, id);

        return item;
    }

    public void deletar(int id) {

        Item i = listarUm(id);

        String sql = "SELECT removeItem(?);";

        if (i != null) {
            jdbcTemplate.queryForObject(sql,Boolean.class,id);
        } else {
            throw new IllegalArgumentException(sql);
        }
    }

    public boolean inserir(Item i, int pedidoId) {
        try {
            String sql = "SELECT adicionaItem(?,?,?);";

            return jdbcTemplate.queryForObject(
                    sql,
                    Boolean.class,
                    new Object[] {
                            i.getProduto().getId(),
                            i.getQuantidade(),
                            pedidoId
                    });

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Provavelmente cpf incorreto ou duplicado");
            // return false;
        }

        return false;
    }

    public List<Item> listarPorIds(String ids) {
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
                    item.setValor((Double) val);
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

    @Override
    public Item atualizar(Item c) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'atualizar'");
    }

}
