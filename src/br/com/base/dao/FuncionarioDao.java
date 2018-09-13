/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.base.dao;

import br.com.base.modelos.Funcionario;
import br.com.base.util.HibernateUtil;
import br.com.base.util.InterfacePersistence;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;

/**
 *
 * @author Edson
 */
public class FuncionarioDao implements InterfacePersistence<Funcionario>{

    public FuncionarioDao() {
    }

    @Override
    public Funcionario getById(Funcionario funcionario) {
        SessionFactory sessao = HibernateUtil.getSessionFactory();
        Session sess = sessao.openSession();
        Example example = Example.create(funcionario);
        List<Funcionario> prod = sess.createCriteria(Funcionario.class).add(example).list();
        Funcionario msg = new Funcionario();
        if (!prod.isEmpty()) {
            try {
                msg = prod.get(0);

                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                String data = dateFormat.format(msg.getDataNascimento());

                Date dt = new SimpleDateFormat("dd/MM/yyyy").parse(data);
                msg.setDataNascimento(new java.sql.Date(dt.getTime()));

            } catch (ParseException ex) {
                ex.printStackTrace();
            }

        }
        sess.close();
        return msg;

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Funcionario> findAll() {
        SessionFactory sessao = HibernateUtil.getSessionFactory();
        Session sess = sessao.openSession();
        Transaction tx = sess.beginTransaction();

        List<Funcionario> lista = sess.createQuery("FROM " + Funcionario.class.getName()).list();
        sess.close();
        return lista;
    }

    @Override
    public void persist(Funcionario model) {

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
    public void remove(Funcionario model) {
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
            Funcionario model = new Funcionario();
            model.setId(id);
            model = getById(model);
            remove(model);
        } catch (Exception ex) {

        }
    }

}
