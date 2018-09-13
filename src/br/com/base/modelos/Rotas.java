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
public class Rotas implements Serializable {
     
    @Id
     @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String nomebairro ;
    private String cidade;
    private String cep;
    private Long numerorota;

    public Rotas() {
    }
   

    
    
    public Long getId() {
        return id;
    }

    public void setId(Long Id) {
        this.id = Id;
    }
 

    public String getNomebairro() {
        return nomebairro;
    }

    public void setNomebairro(String nomebairro) {
        this.nomebairro = nomebairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Long getNumerorota() {
        return numerorota;
    }

    public void setNumerorota(Long numerorota) {
        this.numerorota = numerorota;
    }
    
     @Override
    public String toString() {
       return  nomebairro ;
    }
}
