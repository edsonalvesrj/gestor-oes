/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.base.negocio.Ordenador;

import br.com.base.modelos.Mensageiros;
import javafx.collections.ObservableList;

/**
 *
 * @author Edson
 */
public class Ordenado_Mensageiro_Codigo extends ControleTemplateMethod<Mensageiros>{

    public Ordenado_Mensageiro_Codigo(ObservableList<Mensageiros> array) {
        super(array);
    }

    @Override
    public boolean ePrimeiro(Mensageiros obj, Mensageiros obj1) {
          if(obj.getId()<= obj1.getId())return true ; 
       else return false ;
    }
    
}
