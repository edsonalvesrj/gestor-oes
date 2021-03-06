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
public class Motivos implements Serializable {

     @Id  
      @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String nome ;
    private String tipo;

   

    public Motivos() {
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

   
 

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
   
     @Override
    public String toString() {
       return  nome ;
    }
}
