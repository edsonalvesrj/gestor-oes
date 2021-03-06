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
public class Cidades implements Serializable {

     @Id  
      @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String nome ;
    private String uf;

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public Cidades() {
        this.id = null;
        this.nome = "";
    }
   
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long Id) {
        this.id = Id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

   
  @Override
    public String toString() {
       return  nome ;
    }
   
    
}
