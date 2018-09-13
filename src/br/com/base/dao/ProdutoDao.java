/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.base.dao;

import br.com.base.modelos.Produto;
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
public class ProdutoDao implements InterfacePersistence<Produto>{

      public ProdutoDao() {

    }

    @Override
    public Produto getById(Produto produto) {
        SessionFactory sessao = HibernateUtil.getSessionFactory();
        Session sess = sessao.openSession();
        Example example = Example.create(produto);
        List<Produto> prod = sess.createCriteria(Produto.class).add(example).list();
        if (prod.isEmpty()) {
            return new Produto();
        }
        sess.close();
        return prod.get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Produto> findAll() {
        SessionFactory sessao = HibernateUtil.getSessionFactory();
        Session sess = sessao.openSession();
        Transaction tx = sess.beginTransaction();
        List<Produto> lista = sess.createQuery("FROM " + Produto.class.getName()).list();

        sess.close();
        return lista;
    }

    @Override
    public void persist(Produto model) {

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
    public void remove(Produto model) {
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
            Produto model = new Produto();
            model.setId(id);
            model = getById(model);
            remove(model);
        } catch (Exception ex) {
        }
    }

}
