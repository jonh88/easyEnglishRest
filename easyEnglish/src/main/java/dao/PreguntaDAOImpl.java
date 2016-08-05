package dao;

import domain.Pregunta;
import domain.Vocabulario;
import conn.HibernateUtil;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class PreguntaDAOImpl implements IPreguntaDAO{
	
	public PreguntaDAOImpl(){} 

	public Pregunta insertPregunta(Pregunta q) {
		Session session = null;
        Transaction transaction = null;
        try {
        	        	        		
			session = HibernateUtil.getSessionFactory().getCurrentSession();
	        transaction = session.beginTransaction();
	        session.save(q);
	        transaction.commit();
	        return q;
        	            
        }catch (Exception e){
        	e.printStackTrace();
            return null;
        }finally{
        	if (session.isOpen())
        		session.close();
        }
	}

	public boolean updatePregunta(Pregunta preguntaModified) {

		Session session =null;
        try  {
           session = HibernateUtil.getSessionFactory().getCurrentSession();
           session.beginTransaction();
           session.saveOrUpdate(preguntaModified);
           session.flush();
           session.getTransaction().commit();
           return true;
        }catch (Exception ex){        	
            return false;
        }finally{
        	if (session.isOpen())
        		session.close();
        }
	}

	public Pregunta getPregunta(int id) {

		Session session = null;        
        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Query query = session.createQuery("from Pregunta where id = :id");
            query.setParameter("id", id);
            
            List q = query.list();
            session.getTransaction().commit();
            
            if ((q != null)&&(q.isEmpty())){
            	return null;
            }else if ((q != null)&&(!q.isEmpty())){
            	return (Pregunta)q.get(0);
            }
            
            return null;            
        }catch (Exception e){
            return null;
        }finally{
        	if (session.isOpen())
        		session.close();
        }
	}

	public boolean delete(int idPreg) {

		Session session = null;
        Transaction transaction = null;
        try{
        	session = HibernateUtil.getSessionFactory().getCurrentSession();
        	transaction = session.beginTransaction();
        	Query createQuery = session.createQuery("delete from Pregunta where id = :id");
        	createQuery.setParameter("id", idPreg);
        	createQuery.executeUpdate();
        	transaction.commit();
        	return true;
        }catch (Exception e){
        	e.printStackTrace();
            return false;
        }finally{
        	if (session.isOpen())
        		session.close();
        }
	}

	public Long getCount(){
		Session session = null;
        Transaction transaction = null;
        try{
        	session = HibernateUtil.getSessionFactory().getCurrentSession();
        	transaction = session.beginTransaction();
        	Query createQuery = session.createQuery("select count(*) from Pregunta");        	
        	long n = (Long)createQuery.uniqueResult();
        	transaction.commit();
        	return n;
        }catch (Exception e){
        	e.printStackTrace();
            return -1L;
        }finally{
        	if (session.isOpen())
        		session.close();
        }
	}
}
