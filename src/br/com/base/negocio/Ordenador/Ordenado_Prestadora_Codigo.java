/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.base.negocio.Ordenador;

import br.com.base.modelos.PrestadoraServico;
import javafx.collections.ObservableList;



/**
 *
 * @author EDSON
 */
public class Ordenado_Prestadora_Codigo extends ControleTemplateMethod<PrestadoraServico> {   

    public Ordenado_Prestadora_Codigo(ObservableList<PrestadoraServico> array) {
        super(array);
    }

    @Override
    public boolean ePrimeiro(PrestadoraServico obj, PrestadoraServico obj1) {
       if(obj.getId()<= obj1.getId())return true ; 
       else return false ;
    }

    
   

    
    
    
    
}
