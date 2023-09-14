package com.example.demo.repositories;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;

import com.example.demo.entities.Funcionario;

public interface FuncionarioRepository {

    public List<Funcionario> listar();
    public Funcionario listarUm(int id);
    public boolean inserir(Funcionario c);
    public void atualizar(Funcionario c) throws DataIntegrityViolationException;
    public void deletar(int id);

}
