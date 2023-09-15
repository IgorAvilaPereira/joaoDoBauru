package com.example.demo.repositories;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Produto;
import com.example.demo.repositories.mappers.ProdutoRowMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProdutoRepositoryImpl implements ProdutoRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ProdutoRowMapper produtoRowMapper;

    public List<Produto> listar() {
        String sql = "SELECT *, ativo as p_ativo from produto where ativo is true;";
        final List<Produto> Produtos = jdbcTemplate.query(sql, produtoRowMapper);
        return Produtos;
    }

    public Produto listarUm(int id) {
        String sql = "SELECT *, ativo as p_ativo FROM produto WHERE id = ? and ativo is true;";

        Produto produto = jdbcTemplate.queryForObject(sql, produtoRowMapper, id);
        if (produto == null) {
            return new Produto();
        }
        return produto;
    }

    public void deletar(int id) {
    jdbcTemplate.update("""
    update produto set ativo = false where id = ?
    """, id);
    }

    public Produto inserir(Produto c) {
        String sql = """
                INSERT INTO produto (descricao, valor, estoque)
                VALUES (?, ?, ?) RETURNING id;
                """;
        Integer id = jdbcTemplate.queryForObject(sql, Integer.class,
                new Object[] { c.getDescricao(), c.getValor(), c.getEstoque() });
        if (id != null)
            c.setId(id.intValue());

        return c;
    }

    public void atualizar(Produto c) {

        jdbcTemplate.update("""
                UPDATE
                  produto
                SET
                  descricao=?,
                  valor=?,
                  estoque=?,
                  ativo = ?
                WHERE
                  id=?;

                    """, c.getDescricao(), c.getValor(), c.getEstoque(), c.isAtivo(), c.getId());

    }

}