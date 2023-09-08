package com.example.demo.repositories;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Cliente;
import com.example.demo.repositories.mappers.ClienteRowMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ClienteRepository {

  private final JdbcTemplate jdbcTemplate;
  private final ClienteRowMapper clientRowMapper;

  public List<Cliente> findAll() {
    String sql = """
            SELECT
              ativo, c.id as c_id, c.nome as c_nome, c.cpf as c_cpf, c.telefone as c_telefone, c.rua as c_rua, c.bairro as c_bairro, c.numero as c_numero, c.complemento as c_complemento, c.cep as c_cep
            FROM
              cliente c where ativo is true;
        """;
    final List<Cliente> clientes = jdbcTemplate.query(sql, clientRowMapper);
    return clientes;
  }

  public Cliente findById(int id) throws IllegalArgumentException{
    try {
      String sql = """
          SELECT
            ativo, c.id as c_id, c.nome as c_nome, c.cpf as c_cpf, c.telefone as c_telefone, c.rua as c_rua, c.bairro as c_bairro, c.numero as c_numero, c.complemento as c_complemento, c.cep as c_cep
          FROM
            cliente c
          WHERE
            id = ? and ativo is true;
            """;
      Cliente cliente = jdbcTemplate.queryForObject(sql, clientRowMapper, id);
      return cliente;
    } catch (Exception e) {
      throw new IllegalArgumentException("Argumento Invalido");
    }
  }

  // private final String DELETE_SQL = """
  //     BEGIN;
  //     DELETE FROM item WHERE pedido_id in (
  //       SELECT id FROM pedido WHERE cliente_id = ?
  //     );
  //     DELETE FROM pedido WHERE cliente_id = ?;
  //     DELETE FROM cliente WHERE id = ?; COMMIT;
  //     """;

  public void deletar(int id) {
    jdbcTemplate.update("update cliente set ativo = false where id = ?;", id);
  }

  public Cliente inserir(Cliente c) throws IllegalArgumentException{
    try {
      String sql = """
          INSERT INTO
            cliente (nome, cpf,telefone,rua,bairro,numero,complemento,cep)
          VALUES
            (?,?,?,?,?,?,?,?)
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

      Number key = keyHolder.getKey();
      if (insertsCount == 1) {
        if (key != null)
          c.setId((Integer) key);
      }
      return c;
    } catch (Exception e) {
      throw new IllegalArgumentException("Cpf ja cadastrado e/ou Cpf inválido");
    }
  }

  public void atualizar(Cliente c) {
    String sql = """
        UPDATE
          cliente
        SET
          nome=?, cpf=?, telefone=?, rua=?, bairro=?, numero=?, complemento=?, cep=?, ativo = ? 
        WHERE
          id=?;
        """;
    jdbcTemplate.update(sql, c.getNome(), c.getCpf(), c.getTelefone(), c.getEndereco().getRua(),
        c.getEndereco().getBairro(), c.getEndereco().getNumero(),
        c.getEndereco().getComplemento(), c.getEndereco().getCep(), c.isAtivo(), 
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