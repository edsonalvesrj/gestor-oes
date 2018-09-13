/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.base.util;

import java.util.List;

/**
 *
 * @author Edson
 * @param <T>
 */
public interface InterfacePersistence <T>{
    
   public T getById(T model);
   public List<T> findAll() ;
   public void persist(T model);  
   public void remove(T model);
   public void removeById(final Long id);
}
