/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.base.dao;

import br.com.base.modelos.Motivos;
import br.com.base.util.HibernateUtil;
import br.com.base.util.InterfacePersistence;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;

/**
 *
 * @author Edson
 */
public class MotivoDao implements InterfacePersistence<Motivos> {

    public MotivoDao() {
    }

    @Override
    public Motivos getById(Motivos rota) {
        SessionFactory sessao = HibernateUtil.getSessionFactory();
        Session sess = sessao.openSession();
        Example example = Example.create(rota);
        List<Motivos> mot = sess.createCriteria(Motivos.class).add(example).list();
        if (mot.isEmpty()) {
            return new Motivos();
        }
        sess.close();
        return mot.get(0);
    }
    
     public List<Motivos> get(String tipo) {

        SessionFactory sessao = HibernateUtil.getSessionFactory();
        Session sess = sessao.openSession();  
        
        Query query = sess.createSQLQuery("SELECT * FROM Motivos   where  tipo = :t").addEntity(Motivos.class);
                 
        query.setString("t", tipo);
        Iterator<Motivos> pu = query.list().iterator();
        List<Motivos> pe = new ArrayList<>();
        while (pu.hasNext()) {  
            Motivos  or = pu.next();
            pe.add(or);

        }
        
          sess.close();         
          return pe ;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Motivos> findAll() {
        SessionFactory sessao = HibernateUtil.getSessionFactory();
        Session sess = sessao.openSession();
        Transaction tx = sess.beginTransaction();
        List<Motivos> mot = sess.createQuery("FROM " + Motivos.class.getName()).list();
        sess.close();
        return mot;
    }

    @Override
    public void persist(Motivos model) {

        SessionFactory sessao = HibernateUtil.getSessionFactory();
        Session sess = sessao.openSession();
        Transaction tx = sess.beginTransaction();

        try {
            sess.saveOrUpdate(model);
            tx.commit();
            sess.close();
        } catch (Exception ex) {
            tx.rollback();
        }

    }

    @Override
    public void remove(Motivos model) {
        SessionFactory sessao = HibernateUtil.getSessionFactory();
        Session sess = sessao.openSession();
        Transaction tx = sess.beginTransaction();
        sess.delete(model);
        tx.commit();
        sess.close();
    }

    @Override
    public void removeById(Long id) {
        try {
            Motivos model = new Motivos();
            model.setId(id);
            model = getById(model);
            remove(model);
        } catch (Exception ex) {
        }
    }

}
