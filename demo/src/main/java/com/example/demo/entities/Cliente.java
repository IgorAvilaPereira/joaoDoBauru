package com.example.demo.entities;

public class Cliente {
//     id          | integer                |           | not null | nextval('cliente_id_seq'::regclass)
//  nome        | character varying(100) |           | not null | 
//  cpf         | character(11)          |           |          | 
//  telefone    | character varying(12)  |           |          | 
//  rua         | text                   |           |          | 
//  bairro      | text                   |           |          | 
//  numero      | text                   |           |          | 
//  complemento | text                   |           |          | 
//  cep  
    private int id;
    private String nome;
    private String cpf;
    private String telefone;

    private Endereco endereco;

    public Cliente(){
        this.endereco = new Endereco();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    

}
