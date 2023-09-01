package com.example.demo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Endereco {
    // @JsonProperty("logradouro")
    private String rua;
    private String bairro;
    private String complemento;
    private String cep;

    public Endereco (){
        
    }
    public String getRua() {
        return rua;
    }
    public void setRua(String rua) {
        this.rua = rua;
    }
    public String getBairro() {
        return bairro;
    }
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }
    public String getComplemento() {
        return complemento;
    }
    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }
    public String getCep() {
        return cep;
    }
    public void setCep(String cep) {
        this.cep = cep;
    }

    

}
