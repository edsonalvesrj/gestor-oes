/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.base.negocio.Ordenador;

import br.com.base.modelos.Motivos;
import javafx.collections.ObservableList;



/**
 *
 * @author EDSON
 */
public class Ordenado_Motivo_Codigo extends ControleTemplateMethod<Motivos> {   

    
    public Ordenado_Motivo_Codigo(ObservableList array) {
        super(array);
    }

    @Override
    public boolean ePrimeiro(Motivos obj, Motivos obj1) {
       return true;
    }

    
    
    
    
}
