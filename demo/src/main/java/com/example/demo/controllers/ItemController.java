/*
 * itemck nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * itemck nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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

import com.example.demo.entities.Item;
import com.example.demo.repositories.ItemRepositoryImpl;

import lombok.RequiredArgsConstructor;

/**
 *
 * @author iapereira
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/pedidos/{pedidoId}/itens")
public class ItemController {

    private final ItemRepositoryImpl itemRepository;

    @GetMapping
    public ResponseEntity<List<Item>> listar(@PathVariable int pedidoId) {
        List<Item> vetItem = itemRepository.listarPorPedidoId(pedidoId);
        if (vetItem.size() > 0) {
            return ResponseEntity.ok().body(vetItem);

        } else {
            return ResponseEntity.ok().build();

        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> findById(@PathVariable int id) {
        try {
            Item f = itemRepository.listarUm(id);
            return ResponseEntity.ok().body(f);
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Item());

        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Item> deletar(@PathVariable int id) {
        itemRepository.deletar(id);
        return ResponseEntity.ok().body(new Item());
    }

    @PostMapping
    public ResponseEntity<Item> inserir(@RequestBody Item item, @PathVariable int pedidoId) {
        itemRepository.inserir(item, pedidoId);
        return ResponseEntity.ok().body(item);
    }

    @PutMapping
    public ResponseEntity<?> atualizar(@PathVariable int pedidoId, @RequestBody Item item) {
        try {            
            return ResponseEntity.ok().body(itemRepository.atualizar(item));
        } catch (Exception e) {
            // System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Não foi possível atualizar o item");

        }
        
    }

}