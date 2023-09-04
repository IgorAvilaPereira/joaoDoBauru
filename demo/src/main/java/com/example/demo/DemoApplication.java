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

}
