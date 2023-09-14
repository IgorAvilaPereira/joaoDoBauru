package com.example.demo.repositories;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Item;
import com.example.demo.entities.Pedido;
import com.example.demo.entities.Produto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PedidoRepositoryImpl implements PedidoRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Pedido> pedidoRowMapper;
    private final RowMapper<Item> itemRowMapper;
    private final RowMapper<Produto> produtoRowMapper;

    public List<Pedido> listar() {
        String sql = "SELECT * FROM historicoPedidos()";
        final List<Pedido> pedidos = jdbcTemplate.query(sql, pedidoRowMapper);

        if (pedidos != null) {

            pedidos.forEach(p -> {
                List<Item> items = p.getItems();
                List<Item> itemsFinal = new ArrayList<>();

                items.forEach(i -> {
                    String sqlItem = "select * from item inner join produto on produto.id = item.produto_id where item.id = ?;";
                    Item item = jdbcTemplate.queryForObject(sqlItem, itemRowMapper, i.getId());

                    String sqlProduto = "select * from produto where id = ?";
                    if (item != null) {
                        Produto produto = jdbcTemplate.queryForObject(sqlProduto, produtoRowMapper,
                                item.getProduto().getId());
                        item.setProduto(produto);
                    }

                    itemsFinal.add(item);
                });

                p.setItems(itemsFinal);
            });
        }

        return pedidos;
    }

    public Pedido listarUm(int id) {
        String sql = "SELECT * FROM historicoPedidos() WHERE id = ?";
        Pedido pedido = jdbcTemplate.queryForObject(sql, pedidoRowMapper, id);

        if (pedido != null) {
            List<Item> items = pedido.getItems();
            List<Item> itemsFinal = new ArrayList<>();

            items.forEach(i -> {
                String sqlItem = "select * from item inner join produto on produto.id = item.produto_id where item.id = ?;";
                Item item = jdbcTemplate.queryForObject(sqlItem, itemRowMapper, i.getId());

                String sqlProduto = "select * from produto where id = ?";
                if (item != null) {
                    Produto produto = jdbcTemplate.queryForObject(sqlProduto, produtoRowMapper,
                            item.getProduto().getId());
                    item.setProduto(produto);
                }

                itemsFinal.add(item);
            });

            pedido.setItems(itemsFinal);
        }

        return pedido;
    }

    public void deletar(int id) {

        // TODO: FALTA DEVOLVER ESTOQUE PARA O PRODUTO: retornar os items de um pedido
        String DELETE_SQL = """
                BEGIN;
                DELETE FROM item WHERE pedido_id = ?;
                DELETE FROM pedido WHERE id = ?;
                COMMIT;
                """;
        jdbcTemplate.update(DELETE_SQL, id, id);
    }

    public Pedido inserir(Pedido p) throws IllegalArgumentException {
        try {

            String sqlPedido = """
                    INSERT INTO
                        pedido (cliente_id, funcionario_id)
                    VALUES
                        (?, ?)
                    """;

            KeyHolder keyHolder = new GeneratedKeyHolder();

            String[] colunas = new String[] { "id", "data_hora" };

            int insertsCount = jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sqlPedido, colunas);
                ps.setInt(1, p.getCliente().getId());
                ps.setInt(2, p.getFuncionario().getId());

                return ps;
            }, keyHolder);

            if (insertsCount == 1) {
                Map<String, Object> keys = keyHolder.getKeys();

                if (keys != null) {
                    keys.forEach((key, value) -> {
                        if (key.equals("id"))
                            p.setId((int) value);
                        if (key.equals("data_hora"))
                            p.setData_hora((java.sql.Timestamp) value);
                    });
                }

                List<Item> items = p.getItems();

                String sqlItem = "SELECT adicionaItem(?,?,?)";

                items.forEach(item -> {

                    jdbcTemplate.queryForObject(
                            sqlItem,
                            Boolean.class,
                            item.getProduto().getId(),
                            item.getQuantidade(),
                            p.getId());

                });

                return p;
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Cliente ou Funcionário invalido");
        }
        return null;
    }

    public void atualizar(Pedido p) {

        jdbcTemplate.update("""
                UPDATE
                    pedido
                SET
                    cliente_id=?, funcionario_id=?
                WHERE
                    id=?;
                """, p.getCliente().getId(), p.getFuncionario().getId(), p.getId());

        List<Item> oldItems = jdbcTemplate.query("select * from item", itemRowMapper);

        if (oldItems.size() > 0) {
            oldItems.forEach(item -> {
                jdbcTemplate.update("""
                        DELETE FROM
                            item
                        WHERE
                            id=?;
                        """,
                        item.getId());

            });
        }

        List<Item> items = p.getItems();
        String sqlItem = "SELECT adicionaItem(?,?,?)";

        items.forEach(item -> {

            jdbcTemplate.queryForObject(
                    sqlItem,
                    Boolean.class,
                    item.getProduto().getId(),
                    item.getQuantidade(),
                    p.getId());
        });

    }

}