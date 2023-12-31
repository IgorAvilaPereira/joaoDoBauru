/*
 * funcionariock nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * funcionariock nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Funcionario;
import com.example.demo.repositories.FuncionarioRepositoryImpl;

import lombok.RequiredArgsConstructor;

/**
 *
 * @author iapereira
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("funcionarios")
public class FuncionarioController {

    private final FuncionarioRepositoryImpl funcionarioRepository;

    @GetMapping
    public ResponseEntity<List<Funcionario>> listar() {
        List<Funcionario> vetFuncionario = funcionarioRepository.listar();
        if (vetFuncionario.size() > 0) {
            return ResponseEntity.ok().body(vetFuncionario);

        } else {
            return ResponseEntity.ok().build();

        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Funcionario> findById(@PathVariable int id) {
        try {
            Funcionario f = funcionarioRepository.listarUm(id);
            // if (/*f != null &&*/ f.getId() != 0) {
            return ResponseEntity.ok().body(f);
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Funcionario());

        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable int id) {
        this.funcionarioRepository.deletar(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<?> inserir(@RequestBody Funcionario funcionario) {
        boolean resultado = funcionarioRepository.inserir(funcionario);
        if (resultado) return ResponseEntity.ok().body(funcionario);
        return ResponseEntity.badRequest().body("Não foi possível adicionar o funcionário");
    }

    @PutMapping
    public ResponseEntity<?> atualizar(@RequestBody Funcionario funcionario) {

        try{
            funcionarioRepository.atualizar(funcionario);
            return ResponseEntity.ok().body(funcionario);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}