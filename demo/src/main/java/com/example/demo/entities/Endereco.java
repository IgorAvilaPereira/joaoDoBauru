package com.example.demo.entities;

import org.springframework.stereotype.Component;

@Component
public class Endereco {
    // @JsonProperty("logradouro")
    private String rua;
    private String bairro;
    private String complemento;
    private String numero;
    private String cep;

    public Endereco (){
        
    }

    public Endereco(String rua, String bairro, String complemento, String numero, String cep) {
        this.rua = rua;
        this.bairro = bairro;
        this.complemento = complemento;
        this.numero = numero;
        this.cep = cep;
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
    public String getNumero() {
        return numero;
    }
    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        return "Endereco [rua=" + rua + ", bairro=" + bairro + ", complemento=" + complemento + ", numero=" + numero
                + ", cep=" + cep + "]";
    }

    
}
