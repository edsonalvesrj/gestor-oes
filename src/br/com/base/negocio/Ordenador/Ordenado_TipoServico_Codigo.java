/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.base.negocio.Ordenador;

import br.com.base.modelos.TipoServico;
import javafx.collections.ObservableList;



/**
 *
 * @author EDSON
 */
public class Ordenado_TipoServico_Codigo extends ControleTemplateMethod<TipoServico> {   

    public Ordenado_TipoServico_Codigo(ObservableList<TipoServico> array) {
        super(array);
    }

    @Override
    public boolean ePrimeiro(TipoServico obj, TipoServico obj1) {
       if(obj.getId()<= obj1.getId())return true ; 
       else return false ;
    }

    
   

    
    
    
    
}
