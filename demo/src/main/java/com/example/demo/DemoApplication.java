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

@SpringBootApplication
@Configuration
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public RowMapper<Cliente> clientRowMapper() {
        return new RowMapper<Cliente>() {

            @Override
            @Nullable
            public Cliente mapRow(ResultSet rs, int arg1) throws SQLException {
                // if (rs.next()) {
                    Cliente c = new Cliente();
                    c.setId(rs.getInt("id"));
                    c.setNome(rs.getString("nome"));
                    c.setEndereco(new Endereco(rs.getString("rua"), rs.getString("bairro"), rs.getString("complemento"), rs.getString("numero"), rs.getString("cep")));
                    c.setTelefone(rs.getString("telefone"));
                    return c;
                // }
                // return null;
                
            }
        
        };
    }

}
