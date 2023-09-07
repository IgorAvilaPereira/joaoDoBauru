package com.example.demo.repositories;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Produto;
import com.example.demo.repositories.mappers.ProdutoRowMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProdutoRepository {

  private final JdbcTemplate jdbcTemplate;
  private final ProdutoRowMapper produtoRowMapper;

  public List<Produto> findAll() {
    String sql = """
            SELECT
            id,
            descricao,
            valor    
            FROM
              produto
        """;
    final List<Produto> Produtos = jdbcTemplate.query(sql, produtoRowMapper);
    return Produtos;
  }

  public Produto findById(int id) {
    String sql = """
            SELECT
            *      
            FROM
              produto f
        WHERE
          f.id = ?
          """;
    Produto produto = jdbcTemplate.queryForObject(sql, produtoRowMapper, id);
    if (produto == null){ 
      return new Produto();
    }
    return produto;
  }

//  vamos deletar produto??
//  public void deletar(int id) {
//     jdbcTemplate.update("""
//       BEGIN;
//       UPDATE pedido SET produto_id = NULL where produto_id = ?;
//       DELETE FROM produto WHERE id = ?;
//       COMMIT;
//         """, id, id);
//   }

// nao tem estoque??
  public Produto inserir(Produto c) {
    String sql = """
        INSERT INTO produto (descricao, valor)
        VALUES (?, ?) RETURNING id;
        """;
      Integer id = jdbcTemplate.queryForObject(sql, Integer.class);
      if (id != null)
        c.setId(id.intValue());

      return c;
 
  }

  public void atualizar(Produto c) {

    jdbcTemplate.update("""
        UPDATE 
          produto
        SET  
          nome=?, 
          cpf=?,
          telefone=?,
          endereco=? 
        WHERE 
          id=?;

            """, c.getNome(), c.getCpf(), c.getTelefone(),c.getEndereco(),c.getId());

  }

 

}