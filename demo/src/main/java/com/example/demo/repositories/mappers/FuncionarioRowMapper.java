package com.example.demo.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;
import com.example.demo.entities.Funcionario;
@Configuration
public class FuncionarioRowMapper implements RowMapper<Funcionario> {

    @Override
    public Funcionario mapRow(ResultSet rs, int arg1) throws SQLException {
        Funcionario f = new Funcionario();
        f.setId(rs.getInt("f_id"));
        f.setNome( rs.getString("f_nome"));
        f.setCpf(rs.getString("f_cpf"));
        f.setEndereco(rs.getString("f_endereco"));
        f.setTelefone(rs.getString("f_telefone"));
        
        return f;
    }

}
