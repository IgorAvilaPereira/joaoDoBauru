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
        String sql = "SELECT * from item inner join produto on (produto.id = item.produto_id) where pedido_id = ?";
        final List<Item> items = jdbcTemplate.query(sql, itemRowMapper, pedido_id);
        return items;
    }

    public Item listarUm(int id) {
        String sql = "SELECT * from item inner join produto on (produto.id = item.produto_id) where item.id = ?";
        Item item = jdbcTemplate.queryForObject(sql, itemRowMapper, id);
        return item;
    }

    public void deletar(int id) {
        // Item i = listarUm(id);

       
            // String sql = "SELECT removeItem(?);";
            // Antes do DELETE ser efetivado, dispara-se um trigger
            String sql = "DELETE FROM item WHERE id = ?;";

            Object[] args = new Object[] {id};
        
            jdbcTemplate.update(sql, args);
           
    }

    public boolean inserir(Item i, int pedidoId) {
        try {
            // String sql = "SELECT adicionaItem(?,?,?);";
            String sql = "INSERT INTO item (produto_id, quantidade, pedido_id, valor_atual) VALUES (?, ?, ?, (SELECT valor FROM produto WHERE id = ?))";

           Object[] args = new Object[] {
                            i.getProduto().getId(),
                            i.getQuantidade(),
                            pedidoId,
                            i.getProduto().getId()
                    };

                     jdbcTemplate.update(sql, args);

            return true;

        } catch (Exception ex) {
            // System.out.println(ex.getMessage());
            // System.out.println("Provavelmente sem estoque ou item já pertencente ao pedido");
            return false;
        }
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
    public Item atualizar(Item c) throws Exception {
        if (c.getQuantidade() == 0) {
            this.deletar(c.getId());
            return null;
        } else {

            boolean resultado = jdbcTemplate.queryForObject("SELECT atualizaItem(?,?)", Boolean.class,
                    new Object[] { c.getId(), c.getQuantidade() });
            if (!resultado) {
                // System.out.println(e.get);
                throw new Exception("Não foi possível atualizar");
            }
        }
        return this.listarUm(c.getId());
    }

}
