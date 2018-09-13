/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.base.negocio.Ordenador;

import br.com.base.modelos.Rotas;
import javafx.collections.ObservableList;

/**
 *
 * @author Edson
 */
public class Ordenado_Rotas_Codigo extends ControleTemplateMethod<Rotas> {

    public Ordenado_Rotas_Codigo(ObservableList<Rotas> array) {
        super(array);
    }

    @Override
    public boolean ePrimeiro(Rotas obj, Rotas obj1) {
        if(obj.getId()<= obj1.getId())return true ; 
       else return false ;
    }
    
}
