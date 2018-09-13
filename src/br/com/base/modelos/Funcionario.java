/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.base.modelos;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Edson
 */
@Entity
public class Funcionario implements Serializable   {    
   
   @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
   private Long id ;
    
   private String nome;
   private String cpf;
   private String rg; 
   private String telefone ;
   private String celular;
   private String senha ;
   private String tipofuncionario;
   @Temporal(TemporalType.DATE)
   private Date dataNascimento;
  
   private String  statusFuncionario;
   private String endereco;
   private String cidade;
   private String estado;
   private String orgao;
   
  
  
   
   
    public Funcionario() {
    }

    public Funcionario(Long Id, String nome, String cpf, String rg, String telefone, String celular, String senha,String tipoFuncionario, Date dataNascimento, String statusFuncionario, String endereco, String cidade, String estado) {
        this.id = Id;
        this.nome = nome;
        this.cpf = cpf;
        this.rg = rg;
        this.telefone = telefone;
        this.celular = celular;
        this.senha = senha;
        this.tipofuncionario =tipoFuncionario;
        this.dataNascimento = dataNascimento;
        this.statusFuncionario = statusFuncionario;
        this.endereco = endereco;
        this.cidade = cidade;
        this.estado = estado;
        
    }
   
   
  public String getOrgao() {
        return orgao;
    }

    public void setOrgao(String orgao) {
        this.orgao = orgao;
    }
   

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getStatusFuncionario() {
        return statusFuncionario;
    }

    public void setStatusFuncionario(String statusFuncionario) {
        this.statusFuncionario = statusFuncionario;
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

   

    public String getTipofuncionario() {
        return tipofuncionario;
    }

    public void setTipofuncionario(String tipofuncionario) {
        this.tipofuncionario = tipofuncionario;
    }
 @Override
    public String toString() {
        return nome; 
    }
    
}
