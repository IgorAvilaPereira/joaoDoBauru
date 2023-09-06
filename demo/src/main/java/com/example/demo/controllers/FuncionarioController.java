/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
        return ResponseEntity.ok().body(funcionarioRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        return ResponseEntity.ok().body(funcionarioRepository.findById(id));
    }

    // @PostMapping
    // public ResponseEntity<Funcionario> inserir(@RequestBody Funcionario cli) {

    //     funcionarioRepository.inserir(cli);

    //     return ResponseEntity.ok().body(cli);
    // }

    // @PutMapping
    // public ResponseEntity<Funcionario> atualizar(@RequestBody Funcionario cli) {

    //     funcionarioRepository.atualizar(cli);

    //     return ResponseEntity.ok().body(cli);
    // }

    // @DeleteMapping("/{id}")
    // public ResponseEntity<?> deletar(@PathVariable int id) {
    //     this.funcionarioRepository.deletar(id);
    //     return ResponseEntity.ok().build();
    // }

}