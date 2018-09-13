/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.base.dao;

import br.com.base.modelos.PrestadoraServico;
import br.com.base.util.HibernateUtil;
import br.com.base.util.InterfacePersistence;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;

/**
 *
 * @author Edson
 */
public class PrestadoraDao implements InterfacePersistence<PrestadoraServico> {

    public PrestadoraDao() {

    }

    @Override
    public PrestadoraServico getById(PrestadoraServico prestadora) {
        SessionFactory sessao = HibernateUtil.getSessionFactory();
        Session sess = sessao.openSession();
        Example example = Example.create(prestadora);

        List<PrestadoraServico> prod = sess.createCriteria(PrestadoraServico.class).add(example).list();
        if (prod.isEmpty()) {
            return new PrestadoraServico();
        }
        PrestadoraServico prest = prod.get(0);
        sess.close();
        return prest;
    }

    @Override
    public List<PrestadoraServico> findAll() {
        SessionFactory sessao = HibernateUtil.getSessionFactory();
        Session sess = sessao.openSession();
        Transaction tx = sess.beginTransaction();
        List<PrestadoraServico> lista = sess.createQuery("FROM " + PrestadoraServico.class.getName()).list();
        sess.close();
        return lista;
    }

    @Override
    public void persist(PrestadoraServico model) {

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
    public void remove(PrestadoraServico model) {
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
            PrestadoraServico model = new PrestadoraServico();
            model.setId(id);
            model = getById(model);
            remove(model);
        } catch (Exception ex) {
        }
    }

}
