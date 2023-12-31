/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.controllers;

import java.sql.SQLException;
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

import com.example.demo.entities.Pedido;
import com.example.demo.repositories.PedidoRepositoryImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("pedidos")
public class PedidoController {

    private final PedidoRepositoryImpl pedidoRepository;

    @GetMapping
    public ResponseEntity<List<Pedido>> listar() {
        return ResponseEntity.ok().body(pedidoRepository.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> listarUm(@PathVariable Integer id) throws SQLException {
        return ResponseEntity.ok().body(pedidoRepository.listarUm(id));
    }

    @PostMapping
    public ResponseEntity<?> inserir(@RequestBody Pedido obj) {

        try {
            pedidoRepository.inserir(obj);
            return ResponseEntity.ok().body(obj);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }

    }

    @PutMapping
    public ResponseEntity<Pedido> atualizar(@RequestBody Pedido obj) {

        pedidoRepository.atualizar(obj);

        return ResponseEntity.ok().body(obj);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable int id) {
        this.pedidoRepository.deletar(id);
        return ResponseEntity.ok().build();
    }

}