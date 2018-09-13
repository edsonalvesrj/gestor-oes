package br.com.base.dao;

import br.com.base.modelos.TipoServico;
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
public class TipoServicoDao implements InterfacePersistence<TipoServico> {

    public TipoServicoDao() {

    }

    @Override
    public TipoServico getById(TipoServico servico) {
        SessionFactory sessao = HibernateUtil.getSessionFactory();
        Session sess = sessao.openSession();
        Example example = Example.create(servico);
        List<TipoServico> serv = sess.createCriteria(TipoServico.class).add(example).list();
        if (serv.isEmpty()) {
            return new TipoServico();
        }
        sess.close();
        return serv.get(0);

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TipoServico> findAll() {
        SessionFactory sessao = HibernateUtil.getSessionFactory();
        Session sess = sessao.openSession();
        Transaction tx = sess.beginTransaction();
        List<TipoServico> serv = sess.createQuery("FROM " + TipoServico.class.getName()).list();
        sess.close();
        return serv;
    }

    @Override
    public void persist(TipoServico model) {

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
    public void remove(TipoServico model) {
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
            TipoServico model = new TipoServico();
            model.setId(id);
            model = getById(model);
            remove(model);
        } catch (Exception ex) {
        }
    }

}
