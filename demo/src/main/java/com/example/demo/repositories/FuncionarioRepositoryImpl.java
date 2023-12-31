package com.example.demo.repositories;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Funcionario;
import com.example.demo.repositories.mappers.FuncionarioRowMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class FuncionarioRepositoryImpl implements FuncionarioRepository {

  private final JdbcTemplate jdbcTemplate;
  private final FuncionarioRowMapper funcionarioRowMapper;

  public List<Funcionario> listar() {
    String sql = """
            SELECT
            f.ativo as f_ativo,
            f.id as f_id,
            f.nome as f_nome,
            f.cpf as f_cpf,
            f.endereco as f_endereco,
            f.telefone as f_telefone
            FROM
              funcionario f where ativo is true;
        """;
    final List<Funcionario> Funcionarios = jdbcTemplate.query(sql, funcionarioRowMapper);
    return Funcionarios;
  }

  public Funcionario listarUm(int id) {
    String sql = """
            SELECT
            f.ativo as f_ativo, 
            f.id as f_id,
            f.nome as f_nome,
            f.cpf as f_cpf,
            f.endereco as f_endereco,
            f.telefone as f_telefone
            FROM
              funcionario f
        WHERE
          f.id = ? and ativo is true
          """;
    Funcionario funcionario = jdbcTemplate.queryForObject(sql, funcionarioRowMapper, id);
    if (funcionario == null) {
      return new Funcionario();
    }
    return funcionario;
  }

  public void deletar(int id) {
    jdbcTemplate.update("""
        BEGIN;
        UPDATE pedido SET funcionario_id = NULL where funcionario_id = ?;
        UPDATE funcionario SET ativo = false WHERE id = ?;
        COMMIT;
          """, id, id);
  }

  public boolean inserir(Funcionario f) {
    try {
      String sql = """
          INSERT INTO funcionario (nome, cpf,telefone,endereco)
          VALUES (?, ?, ?, ?) RETURNING id;
          """;
      Integer id = jdbcTemplate.queryForObject(sql, Integer.class,
          new Object[] { f.getNome(), f.getCpf(), f.getTelefone(), f.getEndereco() });
      if (id != null)
        f.setId(id.intValue());
        f.setAtivo(true);
      return true;
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
      System.out.println("Provavelmente cpf incorreto ou duplicado");
      // return false;
    }

    return false;
  }

  public void atualizar(Funcionario c) throws DataIntegrityViolationException {

    try {
      jdbcTemplate.update("""
          UPDATE
            funcionario
          SET
            nome=?,
            cpf=?,
            telefone=?,
            endereco=?,
            ativo = ?
          WHERE
            id=?;

              """, c.getNome(), c.getCpf(), c.getTelefone(), c.getEndereco(), c.isAtivo(),  c.getId());

    } catch (Exception e) {
      throw new DataIntegrityViolationException("CPF inválido!"); 
    }


  }

}