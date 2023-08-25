/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author iapereira
 */
@RestController
@RequestMapping("clientes")
public class Cliente {
    
    @GetMapping("/")
    public String hello() {
        return "hello";
    }

    // https://20a4-200-132-11-251.ngrok.io

    // https://8496-200-132-11-251.ngrok.io
    
    @GetMapping("/teste")
    public String teste() {
        return "hello teste";
    }

    @GetMapping("/fernando")
    public String fernando() {
        return "hello Fernando Barbosa";
    }
    
    @GetMapping("/josue")
    public String josue() {
        return "hello josue Fernandes";
    }

    @GetMapping("/anaflavia")
    public String ana() {
        return "hello Ana Flavia";
    }

    


}