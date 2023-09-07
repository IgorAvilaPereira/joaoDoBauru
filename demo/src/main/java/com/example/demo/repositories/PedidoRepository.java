package com.example.demo.repositories;

import java.sql.PreparedStatement;
import java.sql.SQLException;
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
public class PedidoRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Pedido> pedidoRowMapper;
    private final RowMapper<Item> itemRowMapper;
    private final RowMapper<Produto> produtoRowMapper;

    public List<Pedido> findAll() {
        String sql = "SELECT * FROM historicoPedidos()";
        final List<Pedido> pedidos = jdbcTemplate.query(sql, pedidoRowMapper);

        if(pedidos != null){

            pedidos.forEach( p -> {
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

    public Pedido findById(int id) {
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

    private final String DELETE_SQL = """
            BEGIN;
            DELETE FROM item WHERE pedido_id in (
              SELECT id FROM pedido WHERE cliente_id = ?
            );
            DELETE FROM pedido WHERE cliente_id = ?;
            DELETE FROM cliente WHERE id = ?; COMMIT;
            """;

    public void deletar(int id) {
        jdbcTemplate.update(DELETE_SQL, id, id, id);
    }

    public Pedido inserir(Pedido p) throws SQLException {
        String sql = """
                INSERT INTO pedido (cliente_id, funcionario_id)
                VALUES (?, ?)
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        String[] colunas = new String[] { "id", "data_hora" };

        int insertsCount = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(sql, colunas);
            ps.setInt(1, p.getCliente().getId());
            ps.setInt(2, p.getFuncionario().getId());

            return ps;
        }, keyHolder);

        if (insertsCount == 1) {
            Map<String, Object> keys = keyHolder.getKeys();

            if (keys != null)
                keys.forEach((key, value) -> {
                    if (key == "id")
                        p.setId((int) value);
                    if (key == "data_hora")
                        p.setData_hora((java.sql.Timestamp) value);

                });
            return p;
        } else {
            throw new SQLException("Não retornou nenhum id");
        }
    }

    public void atualizar(Pedido p) {

        // jdbcTemplate.update("""
        // UPDATE cliente
        // SET nome=?, cpf=?, telefone=?,
        // rua=?, bairro=?, numero=?, complemento=?, cep=?
        // WHERE id=?;

        // """, p.getNome(), p.getCpf(), p.getTelefone(), p.getEndereco().getRua(),
        // p.getEndereco().getBairro(), p.getEndereco().getNumero(),
        // p.getEndereco().getComplemento(), p.getEndereco().getCep(),
        // p.getId());
    }

}