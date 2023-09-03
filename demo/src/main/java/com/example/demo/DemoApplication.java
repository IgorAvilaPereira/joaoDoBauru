package com.example.demo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import com.example.demo.entities.Cliente;
import com.example.demo.entities.Endereco;
import com.example.demo.entities.Funcionario;
import com.example.demo.entities.Pedido;

@SpringBootApplication
@Configuration
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public RowMapper<Cliente> clienteRowMapper() {
        return new RowMapper<Cliente>() {

            @Override
            @Nullable
            public Cliente mapRow(ResultSet rs, int arg1) throws SQLException {
                // if (rs.next()) {
                Cliente c = new Cliente();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setEndereco(new Endereco(rs.getString("rua"), rs.getString("bairro"), rs.getString("complemento"),
                        rs.getString("numero"), rs.getString("cep")));
                c.setTelefone(rs.getString("telefone"));
                return c;
                // }
                // return null;

            }

        };
    }

    @Bean
    public RowMapper<Pedido> pedidoRowMapper() {
        return new RowMapper<Pedido>() {

            @Override
            @Nullable
            public Pedido mapRow(ResultSet rs, int arg1) throws SQLException {

                Pedido p = new Pedido();
                p.setId(rs.getInt("id"));
                p.setData_hora(rs.getTimestamp("data_hora"));
                p.setCliente(
                        new Cliente(
                                rs.getInt("c_id"),
                                rs.getString("c_nome"),
                                rs.getString("c_cpf"),
                                rs.getString("c_telefone"),
                                new Endereco(
                                        rs.getString("c_rua"),
                                        rs.getString("c_bairro"),
                                        rs.getString("c_complemento"),
                                        rs.getString("c_numero"),
                                        rs.getString("c_cep"))));
                p.setTotal(rs.getBigDecimal("total"));
                p.setFuncionario(
                        new Funcionario(
                                rs.getInt("f_id"),
                                rs.getString("f_nome"),
                                rs.getString("f_cpf"),
                                rs.getString("f_endereco"),
                                rs.getString("f_telefone")));
                return p;

            }

        };
    }

}
