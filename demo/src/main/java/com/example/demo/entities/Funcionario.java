package com.example.demo.entities;

public class Funcionario {

    private int id;
    private String nome;
    private String cpf;
    private String endereco;
    private String telefone;

    public Funcionario() {
    }

    public Funcionario(int id, String nome, String cpf, String endereco, String telefone) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.endereco = endereco;
        this.telefone = telefone;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return this.cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEndereco() {
        return this.endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Funcionario id(int id) {
        setId(id);
        return this;
    }

    public Funcionario nome(String nome) {
        setNome(nome);
        return this;
    }

    public Funcionario cpf(String cpf) {
        setCpf(cpf);
        return this;
    }

    public Funcionario endereco(String endereco) {
        setEndereco(endereco);
        return this;
    }

    public Funcionario telefone(String telefone) {
        setTelefone(telefone);
        return this;
    }

 

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Funcionario)) {
            return false;
        }
        Funcionario funcionario = (Funcionario) o;
        return id == funcionario.id;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
    
}
