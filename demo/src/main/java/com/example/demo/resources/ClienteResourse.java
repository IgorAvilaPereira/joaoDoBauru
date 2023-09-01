/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.resources;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Cliente;
import com.example.demo.services.ClienteService;

/**
 *
 * @author iapereira
 */
@RestController
@RequestMapping(value = "/clientes")
public class ClienteResourse {
    
    @Autowired
    private ClienteService service;

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Cliente>> getClients() {

        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Optional<Cliente>> getClient(@PathVariable(value ="id") Integer id) {
        return ResponseEntity.ok().body(service.findById(id));
    }


}