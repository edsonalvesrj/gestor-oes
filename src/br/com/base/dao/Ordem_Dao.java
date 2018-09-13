/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.base.dao;

import br.com.base.modelos.Mensageiros;
import br.com.base.modelos.Ordem_servico;
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
public class Ordem_Dao implements InterfacePersistence<Ordem_servico>{

    public Ordem_Dao() {
    }
    
    
    
  @Override
    public Ordem_servico getById(Ordem_servico ordem) {
       
        SessionFactory sessao = HibernateUtil.getSessionFactory();
        Session sess = sessao.openSession();  
        Ordem_servico  or = null ;
        Query query = sess.createQuery("SELECT o FROM Ordem_servico o  where o.id = :identificador");
        query.setLong("identificador", ordem.getId());
        or = (Ordem_servico) query.uniqueResult();
       
          sess.close();         
          return or ;
        
        
        
        
    }
   
    public Ordem_servico getByd(Ordem_servico ordem) {
        
         SessionFactory sessao = HibernateUtil.getSessionFactory();
         Session sess = sessao.openSession();        
         Example example = Example.create(ordem);
         ordem = new Ordem_servico();
         List<Ordem_servico> prod =    sess.createCriteria(Ordem_servico.class).add(example).list(); 
         if(!prod.isEmpty()){
             ordem = prod.get(0);       
         }
         sess.close();
        return ordem;
        
    }
    
    public Ordem_servico getByCodigoGetec(String codGetec) {

        SessionFactory sessao = HibernateUtil.getSessionFactory();
        Session sess = sessao.openSession();  
        Ordem_servico  or = null ;
        Query query = sess.createQuery("SELECT o FROM Ordem_servico o   where o.codigo_getec = :getec");
        query.setString("getec", codGetec);
        or = (Ordem_servico) query.uniqueResult();
       
          sess.close();         
          return or ;
    }
    
    public List<Ordem_servico> GetByMensageiroId(Mensageiros mes,String status) {
        SessionFactory sessao = HibernateUtil.getSessionFactory();
        Session sess = sessao.openSession();  
        Transaction tx = sess.beginTransaction();
        Query query = sess.createQuery("SELECT o FROM Ordem_servico o  join FETCH o.expedidor_criador join FETCH o.courrier  where o.courrier = :id and  o.statusOrdem = :stats");
        query.setLong("id", mes.getId());
        query.setString("stats", status);
        List<Ordem_servico> pe  = query.list();         
        
          sess.close();         
          return pe ;
    }
    
     public List<Ordem_servico> GetByStatus(String status) {

        SessionFactory sessao = HibernateUtil.getSessionFactory();
        Session sess = sessao.openSession();
        Transaction tx = sess.beginTransaction();
        Query query   = sess.createQuery("SELECT o FROM Ordem_servico o "
                + " left join fetch o.expedidor_criador f    where o.statusOrdem = :stats");        
        List<Ordem_servico> lista   = query.setString("stats", status).list();
        sess.close();
        return lista;
    }
     
       public Ordem_servico GetByGetecStatus(String codGetec,String status) {

        SessionFactory sessao = HibernateUtil.getSessionFactory();
        Session sess = sessao.openSession();
        Transaction tx = sess.beginTransaction();
        Query query   = sess.createQuery("SELECT o FROM Ordem_servico o "
                + " left join fetch o.expedidor_criador f    where o.statusOrdem = :stats  and o.codigo_getec = :getec");        
               query.setString("stats", status);
               query.setString("getec", codGetec);
              Ordem_servico  ordem   = (Ordem_servico) query.uniqueResult();
            sess.close();
        return ordem;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Ordem_servico> findAll() {
        SessionFactory sessao = HibernateUtil.getSessionFactory();
        Session sess = sessao.openSession();
        Transaction tx = sess.beginTransaction();       
        List<Ordem_servico> lista = sess.createQuery("SELECT o FROM Ordem_servico o  ").list();
        sess.close();
        return lista;
    }

    @Override
    public void persist(Ordem_servico model) {
        
        SessionFactory sessao = HibernateUtil.getSessionFactory();
        Session sess = sessao.openSession();
        Transaction tx = sess.beginTransaction();
        try {
            sess.saveOrUpdate(model);
            tx.commit();
            sess.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            tx.rollback();
        }
      
    }

   

    @Override
    public void remove(Ordem_servico model) {
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
             Ordem_servico model =  new  Ordem_servico();
             model.setId(id);
             model = getById(model);
             remove(model);
        } catch (Exception ex) {
        }
    }
    
    
    
//    @Override
//	public Funcionario carregarFuncionario(Funcionario funcionario) {
//		
//		try{
//		Query query = em.createQuery("select f from Funcionario f "
//				+ " left join fetch f.frequencias freq "
//				+ " left join fetch f.gradesHorario grade "
//				+ " left join fetch f.atividadesDiaria atividade "
//				+ " where f  = :funcionario");
//		query.setParameter("funcionario", funcionario);
//		return (Funcionario) query.getSingleResult();
//		}catch(NoResultException nre){
//			return null;
//		}
//	}
    

}
