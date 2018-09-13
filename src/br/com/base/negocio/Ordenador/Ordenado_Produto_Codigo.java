/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.base.negocio.Ordenador;

import br.com.base.modelos.Produto;
import javafx.collections.ObservableList;



/**
 *
 * @author EDSON
 */
public class Ordenado_Produto_Codigo extends ControleTemplateMethod<Produto> {   

    public Ordenado_Produto_Codigo(ObservableList<Produto> array) {
        super(array);
    }

    @Override
    public boolean ePrimeiro(Produto obj, Produto obj1) {
       if(obj.getId() <= obj1.getId())return true ; 
       else return false ;
    }

    
   

    
    
    
    
}
