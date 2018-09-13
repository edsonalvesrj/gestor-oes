/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.base.negocio.Ordenador;


import javafx.collections.ObservableList;

/**
 *
 * @author EDSON
 * @param <T>
 */
public abstract class ControleTemplateMethod<T> extends Thread {
    
    ObservableList<T> array;
    public ControleTemplateMethod(ObservableList<T> array) {
          this.array = array;
    }
   
    public abstract boolean ePrimeiro(T obj, T obj1);
    
    public ObservableList<T> listagemEmOrdem( ) throws Exception {
        try {
          
            final int meio = array.size();

            for (int i = 0; i < meio; i++) {
                for (int j = i; j < array.size(); j++) {

                    if (!ePrimeiro(array.get(i), array.get(j))) {
                        T temp = array.get(j);
                        array.set(j, array.get(i));
                        array.set(i, temp);
                    }
                }
            }

            return array;
        } catch (Exception erro) {
            throw erro;
        }
    }

}
