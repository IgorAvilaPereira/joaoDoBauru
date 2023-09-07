/*
 * produtock nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * produtock nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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

import com.example.demo.entities.Produto;
import com.example.demo.repositories.ProdutoRepository;

import lombok.RequiredArgsConstructor;

/**
 *
 * @author iapereira
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("produtos")
public class ProdutoController {

    private final ProdutoRepository produtoRepository;

    @GetMapping
    public ResponseEntity<List<Produto>> listar() {
        List<Produto> vetProduto = produtoRepository.findAll();
        if (vetProduto.size() > 0) {
            return ResponseEntity.ok().body(produtoRepository.findAll());

        } else {
            return ResponseEntity.ok().build();

        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> findById(@PathVariable int id) {
        try {
            Produto f = produtoRepository.findById(id);
            // if (/*f != null &&*/ f.getId() != 0) {
            return ResponseEntity.ok().body(f);
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Produto());

        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable int id) {
        this.produtoRepository.deletar(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Produto> inserir(@RequestBody Produto produto) {
        produtoRepository.inserir(produto);
        return ResponseEntity.ok().body(produto);
    }

    @PutMapping
    public ResponseEntity<Produto> atualizar(@RequestBody Produto produto) {
        produtoRepository.atualizar(produto);
        return ResponseEntity.ok().body(produto);
    }

}