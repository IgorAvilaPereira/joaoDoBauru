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
import com.example.demo.repositories.FuncionarioRepository;

import lombok.RequiredArgsConstructor;

/**
 *
 * @author iapereira
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("funcionarios")
public class FuncionarioController {

    private final FuncionarioRepository funcionarioRepository;

    @GetMapping
    public ResponseEntity<List<Funcionario>> listar() {
        List<Funcionario> vetFuncionario = funcionarioRepository.findAll();
        if (vetFuncionario.size() > 0) {
            return ResponseEntity.ok().body(funcionarioRepository.findAll());

        } else {
            return ResponseEntity.ok().build();

        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Funcionario> findById(@PathVariable int id) {
        try {
            Funcionario f = funcionarioRepository.findById(id);
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
    public ResponseEntity<Funcionario> inserir(@RequestBody Funcionario funcionario) {
        funcionarioRepository.inserir(funcionario);
        return ResponseEntity.ok().body(funcionario);
    }

    @PutMapping
    public ResponseEntity<Funcionario> atualizar(@RequestBody Funcionario funcionario) {
        funcionarioRepository.atualizar(funcionario);
        return ResponseEntity.ok().body(funcionario);
    }

}