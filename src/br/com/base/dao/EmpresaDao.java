/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.base.dao;

import br.com.base.modelos.Empresa;
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
public class EmpresaDao implements InterfacePersistence<Empresa> {

    public EmpresaDao() {

    }

    @Override
    public Empresa getById(Empresa empr) {
        SessionFactory sessao = HibernateUtil.getSessionFactory();
        Session sess = sessao.openSession();
        Example example = Example.create(empr);
        List<Empresa> empresa = sess.createCriteria(Empresa.class).add(example).list();
        if (empresa.isEmpty()) {
            return new Empresa();
        }
        Empresa emp = empresa.get(0);
        sess.close();
        return emp;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Empresa> findAll() {
        SessionFactory sessao = HibernateUtil.getSessionFactory();
        Session sess = sessao.openSession();
        Transaction tx = sess.beginTransaction();
        List<Empresa> lista = sess.createQuery("FROM " + Empresa.class.getName()).list();
        sess.close();
        return lista;
    }

    @Override
    public void persist(Empresa model) {

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
    public void remove(Empresa model) {
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
            Empresa model = new Empresa();
            model.setId(id);
            model = getById(model);
            remove(model);
        } catch (Exception ex) {
        }
    }

}
