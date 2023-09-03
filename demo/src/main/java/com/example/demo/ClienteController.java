/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo;

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

import lombok.RequiredArgsConstructor;

/**
 *
 * @author iapereira
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("clientes")
public class ClienteController {

    private final ClienteRepository clienteRepository;

    @GetMapping
    public ResponseEntity<List<Cliente>> listar() {
        return ResponseEntity.ok().body(clienteRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Cliente> inserir(@RequestBody Cliente cli) {

        clienteRepository.inserir(cli);

        return ResponseEntity.ok().body(cli);
    }

    @PutMapping
    public ResponseEntity<Cliente> atualizar(@RequestBody Cliente cli) {

        clienteRepository.atualizar(cli);

        return ResponseEntity.ok().body(cli);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable int id) {
        this.clienteRepository.deletar(id);
        return ResponseEntity.ok().build();
    }

}