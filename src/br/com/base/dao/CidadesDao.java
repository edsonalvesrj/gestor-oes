
package br.com.base.dao;

import br.com.base.modelos.Cidades;
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
public class CidadesDao implements InterfacePersistence<Cidades> {

    public CidadesDao() {

    }

    @Override
    public Cidades getById(Cidades cit) {
        SessionFactory sessao = HibernateUtil.getSessionFactory();
        Session sess = sessao.openSession();
        Example example = Example.create(cit);
        List<Cidades> serv = sess.createCriteria(TipoServico.class).add(example).list();
        if (serv.isEmpty()) {
            return new Cidades();
        }
        Cidades cidade = serv.get(0);
        sess.close();

        return cidade;

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Cidades> findAll() {
        SessionFactory sessao = HibernateUtil.getSessionFactory();
        Session sess = sessao.openSession();
        Transaction tx = sess.beginTransaction();
        List<Cidades> lista = sess.createQuery("FROM " + Cidades.class.getName()).list();
        sess.close();
        return lista;

    }

    @Override
    public void persist(Cidades model) {

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
    public void remove(Cidades model) {
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
            Cidades model = new Cidades();
            model.setId(id);
            model = getById(model);
            remove(model);
        } catch (Exception ex) {
        }
    }

}
