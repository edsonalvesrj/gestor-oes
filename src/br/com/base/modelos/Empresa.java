/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.base.modelos;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Edson
 */
@Entity
public class Empresa implements Serializable {
    
    @Id 
     @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
      
    private String nomerazao;
    private String cpfcnpj;
    private String inscricaoestadual;
    private String email;
    private String telefone;
    private String endereco;
    private String cidade;
    private String estado;

    public Empresa() {
    }

    
    
    public Empresa(Long id, String nomerazao, String cpfcnpj, String inscricaoestadual, String email, String telefone, String endereco, String cidade, String estado) {
        this.id = id;
        this.nomerazao = nomerazao;
        this.cpfcnpj = cpfcnpj;
        this.inscricaoestadual = inscricaoestadual;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
        this.cidade = cidade;
        this.estado = estado;
    }

   

    
    
    public String getNomerazao() {
        return nomerazao;
    }

    public void setNomerazao(String nomerazao) {
        this.nomerazao = nomerazao;
    }

    public String getCpfcnpj() {
        return cpfcnpj;
    }

    public void setCpfcnpj(String cpfcnpj) {
        this.cpfcnpj = cpfcnpj;
    }

    public String getInscricaoestadual() {
        return inscricaoestadual;
    }

    public void setInscricaoestadual(String inscricaoestadual) {
        this.inscricaoestadual = inscricaoestadual;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

   

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
     @Override
    public String toString() {
        return "empresa{" + "nomerazao=" + nomerazao + ", cpfcnpj=" + cpfcnpj + ", inscricaoestadual=" + inscricaoestadual + ", email=" + email + ", telefone=" + telefone + ", endereco=" + endereco + ", cidade=" + cidade + ", estado=" + estado + '}';
    }
}


