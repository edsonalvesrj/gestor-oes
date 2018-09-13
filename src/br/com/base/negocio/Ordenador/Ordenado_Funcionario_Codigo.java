/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.base.negocio.Ordenador;

import br.com.base.modelos.Funcionario;
import javafx.collections.ObservableList;



/**
 *
 * @author EDSON
 */
public class Ordenado_Funcionario_Codigo extends ControleTemplateMethod<Funcionario> {   

    public Ordenado_Funcionario_Codigo(ObservableList<Funcionario> array) {
        super(array);
    }

    @Override
    public boolean ePrimeiro(Funcionario obj, Funcionario obj1) {
       if(obj.getId()<= obj1.getId())return true ; 
       else return false ;
    }

    
   

    
    
    
    
}
