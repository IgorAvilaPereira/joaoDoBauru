package com.example.demo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ClienteRepository {

  // @Autowired
  private final JdbcTemplate jdbcTemplate;
  // @Autowired
  private final RowMapper<Cliente> clientRowMapper;
  

  public List<Cliente> findAll() {
    final List<Cliente> clientes = jdbcTemplate.query(
      "SELECT * FROM cliente", clientRowMapper);
      return clientes;
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

  private final String INSERT_SQL = """
      INSERT INTO cliente 
        (nome, cpf, telefone, rua, bairro, complemento, cep)
      VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id;
      """;

  public void inserir(Cliente c) {
    final Integer id = jdbcTemplate.query(INSERT_SQL,
      c.getNome(),
      c.getCpf(),
      c.getTelefone(),
      c.getEndereco().getRua(),
      c.getEndereco().getBairro(),
      c.getEndereco().getComplemento(),
      c.getEndereco().getCep(),
      (rs, rowNum) -> rs.getInt("id")
    );
    c.setId(id);
  }


  

}