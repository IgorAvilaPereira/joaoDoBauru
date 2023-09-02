/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.resources;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Cliente;
import com.example.demo.services.ClienteService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


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
    public ResponseEntity<Cliente> getCliente(@PathVariable(value ="id") Integer id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<Cliente> insertCliente(@RequestBody Cliente c) {
       
        try {
            return ResponseEntity.ok().body(service.insertCliente(c));
        } catch (SQLException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    


}