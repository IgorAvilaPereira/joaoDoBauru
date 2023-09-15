/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Cliente;
import com.example.demo.repositories.ClienteRepositoryImpl;

import lombok.RequiredArgsConstructor;

/**
 *
 * @author iapereira
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("clientes")
public class ClienteController {

    private final ClienteRepositoryImpl clienteRepository;

    @CrossOrigin(origins = "https://9842-200-132-11-251.ngrok-free.app")
    @GetMapping
    public ResponseEntity<List<Cliente>> listar() {
        return ResponseEntity.ok().body(clienteRepository.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listarUm(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok().body(clienteRepository.listarUm(id));
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } 
    }

    @PostMapping
    public ResponseEntity<?> inserir(@RequestBody Cliente cli) {
        try {
            clienteRepository.inserir(cli);
            return ResponseEntity.ok().body(cli);
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

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