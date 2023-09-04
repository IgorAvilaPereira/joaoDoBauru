package com.example.demo.repositories;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Cliente;
import com.example.demo.repositories.mappers.ClienteMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ClienteRepository {

  private final JdbcTemplate jdbcTemplate;
  private final ClienteMapper clientRowMapper;

  public List<Cliente> findAll() {
    String sql = """
            SELECT
              c.id as c_id,
              c.nome as c_nome,
              c.cpf as c_cpf,
              c.telefone as c_telefone,
              c.rua as c_rua,
              c.bairro as c_bairro,
              c.numero as c_numero,
              c.complemento as c_complemento,
              c.cep as c_cep
            FROM
              cliente c
        """;
    final List<Cliente> clientes = jdbcTemplate.query(sql, clientRowMapper);
    return clientes;
  }

  public Cliente findById(int id) {
    String sql = """
        SELECT
          c.id as c_id,
          c.nome as c_nome,
          c.cpf as c_cpf,
          c.telefone as c_telefone,
          c.rua as c_rua,
          c.bairro as c_bairro,
          c.numero as c_numero,
          c.complemento as c_complemento,
          c.cep as c_cep
        FROM
          cliente c
        WHERE
          id = ?
          """;
    Cliente cliente = jdbcTemplate.queryForObject(sql, clientRowMapper, id);
    return cliente;
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

  public Cliente inserir(Cliente c) {
    String sql = """
        INSERT INTO cliente (nome, cpf,telefone,rua,bairro,numero,complemento,cep)
        VALUES (?, ?,?,?,?,?,?,?)
        """;

    KeyHolder keyHolder = new GeneratedKeyHolder();

    int insertsCount = jdbcTemplate.update(connection -> {
      PreparedStatement ps = connection
          .prepareStatement(sql, new String[] { "id" });
      ps.setString(1, c.getNome());
      ps.setString(2, c.getCpf());
      ps.setString(3, c.getTelefone());
      ps.setString(4, c.getEndereco().getRua());
      ps.setString(5, c.getEndereco().getBairro());
      ps.setString(6, c.getEndereco().getNumero());
      ps.setString(7, c.getEndereco().getComplemento());
      ps.setString(8, c.getEndereco().getCep());

      return ps;
    }, keyHolder);

    if (insertsCount == 1 && keyHolder.getKey() != null) {
      Number key = keyHolder.getKey();
      c.setId((Integer) key);
    }
    return c;
  }

  public void atualizar(Cliente c) {

    jdbcTemplate.update("""
          UPDATE cliente
          SET  nome=?, cpf=?, telefone=?,
          rua=?, bairro=?, numero=?, complemento=?, cep=?
        WHERE id=?;

            """, c.getNome(), c.getCpf(), c.getTelefone(), c.getEndereco().getRua(),
        c.getEndereco().getBairro(), c.getEndereco().getNumero(),
        c.getEndereco().getComplemento(), c.getEndereco().getCep(),
        c.getId());

  }

  /*
   * private final String INSERT_SQL = """
   * INSERT INTO cliente
   * (nome, cpf, telefone, rua, bairro, complemento, cep)
   * VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id;
   * """;
   */
  /*
   * // aqui ta dando erro..
   * public void inserir(Cliente c) {
   * final Integer id = jdbcTemplate.query(INSERT_SQL,
   * c.getNome(),
   * c.getCpf(),
   * c.getTelefone(),
   * c.getEndereco().getRua(),
   * c.getEndereco().getBairro(),
   * c.getEndereco().getComplemento(),
   * c.getEndereco().getCep(),
   * (rs, rowNum) -> rs.getInt("id")
   * );
   * c.setId(id);
   * }
   */

}