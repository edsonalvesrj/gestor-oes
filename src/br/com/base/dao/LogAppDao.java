/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.base.dao;

import br.com.base.modelos.LogAplication;
import br.com.base.util.HibernateUtil;
import br.com.base.util.InterfacePersistence;
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
public class LogAppDao implements InterfacePersistence<LogAplication> {

    public LogAppDao() {
    }

    @Override
    public LogAplication getById(LogAplication obs) {
        SessionFactory sessao = HibernateUtil.getSessionFactory();
        Session sess = sessao.openSession();
        Example example = Example.create(obs);
        List<LogAplication> mot = sess.createCriteria(LogAplication.class).add(example).list();
        if (mot.isEmpty()) {
            return new LogAplication();
        }
        sess.close();
        return mot.get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<LogAplication> findAll() {
        SessionFactory sessao = HibernateUtil.getSessionFactory();
        Session sess = sessao.openSession();
        Transaction tx = sess.beginTransaction();
        List<LogAplication> mot = sess.createQuery("FROM " + LogAplication.class.getName()).list();
        sess.close();
        return mot;
    }

    
    
    public List<LogAplication> GetByCodGetec(String getec) {

        SessionFactory sessao = HibernateUtil.getSessionFactory();
        Session sess = sessao.openSession();
        Transaction tx = sess.beginTransaction();
        Query query   = sess.createQuery("SELECT l FROM LogAplication l  where  l.ordem_servicos.codigo_getec = :getec");
        
        List<LogAplication> lista   = query.setString("getec", getec).list();
        sess.close();
        return lista;
    }
    @Override
    public void persist(LogAplication model) {

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
    public void remove(LogAplication model) {
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
            LogAplication model = new LogAplication();
            model.setId(id);
            model = getById(model);
            remove(model);
        } catch (Exception ex) {
        }
    }

}
