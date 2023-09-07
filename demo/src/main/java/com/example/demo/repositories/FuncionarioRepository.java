package com.example.demo.repositories;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Funcionario;
import com.example.demo.repositories.mappers.FuncionarioRowMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class FuncionarioRepository {

  private final JdbcTemplate jdbcTemplate;
  private final FuncionarioRowMapper funcionarioRowMapper;

  public List<Funcionario> findAll() {
    String sql = """
            SELECT
            f.id as f_id,
            f.nome as f_nome,
            f.cpf as f_cpf,
            f.endereco as f_endereco,
            f.telefone as f_telefone           
            FROM
              funcionario f
        """;
    final List<Funcionario> Funcionarios = jdbcTemplate.query(sql, funcionarioRowMapper);
    return Funcionarios;
  }

  public Funcionario findById(int id) {
    String sql = """
            SELECT
            f.id as f_id,
            f.nome as f_nome,
            f.cpf as f_cpf,
            f.endereco as f_endereco,
            f.telefone as f_telefone           
            FROM
              funcionario f
        WHERE
          f.id = ?
          """;
    Funcionario funcionario = jdbcTemplate.queryForObject(sql, funcionarioRowMapper, id);
    if (funcionario == null){ 
      return new Funcionario();
    }
    return funcionario;
  }

 public void deletar(int id) {
    jdbcTemplate.update("""
      BEGIN;
      UPDATE pedido SET funcionario_id = NULL where funcionario_id = ?;
      DELETE FROM funcionario WHERE id = ?;
      COMMIT;
        """, id, id);
  }

  public Funcionario inserir(Funcionario c) {
    String sql = """
        INSERT INTO funcionario (nome, cpf,telefone,endereco)
        VALUES (?, ?, ?, ?) RETURNING id;
        """;
      Integer id = jdbcTemplate.queryForObject(sql, Integer.class);
      if (id != null)
        c.setId(id.intValue());

      return c;
    // KeyHolder keyHolder = new GeneratedKeyHolder();

    // int insertsCount = jdbcTemplate.update(connection -> {
    //   PreparedStatement ps = connection
    //       .prepareStatement(sql, new String[] { "id" });
    //   ps.setString(1, c.getNome());
    //   ps.setString(2, c.getCpf());
    //   ps.setString(3, c.getTelefone());
    //   ps.setString(4, c.getEndereco());

    //   return ps;
    // }, keyHolder);

    // Number key = keyHolder.getKey();
    // if (insertsCount == 1) {
    //   if (key != null) c.setId((Integer) key);
    // }
    // return c;
  }

  public void atualizar(Funcionario c) {

    jdbcTemplate.update("""
        UPDATE 
          funcionario
        SET  
          nome=?, 
          cpf=?,
          telefone=?,
          endereco=? 
        WHERE 
          id=?;

            """, c.getNome(), c.getCpf(), c.getTelefone(),c.getEndereco(),c.getId());

  }

  /*
   * private final String INSERT_SQL = """
   * INSERT INTO Funcionario
   * (nome, cpf, telefone, rua, bairro, complemento, cep)
   * VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id;
   * """;
   */
  /*
   * // aqui ta dando erro..
   * public void inserir(Funcionario c) {
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