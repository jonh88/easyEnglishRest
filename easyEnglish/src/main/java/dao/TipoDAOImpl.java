/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import domain.Cuestionario;
import domain.Pregunta;
//import conn.ConnectionDB;
import domain.Tipo;
import domain.Vocabulario;
import conn.HibernateUtil;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
/**
 *
 * @author Jonh
 */
public class TipoDAOImpl implements ITipoDAO{
    
   public List<Tipo> getTipos() {
       Session session = null;
       Transaction transaccion = null;
       try{
    	   session = HibernateUtil.getSessionFactory().getCurrentSession();
    	   transaccion = session.beginTransaction();    			   
    	   Query query = session.createQuery("from Tipo");    	   
    	   List listaTipos = query.list();
    	   transaccion.commit();
    	   if ((listaTipos != null)&&(!listaTipos.isEmpty()))
    			   return (List<Tipo>)listaTipos;
    	   else
    		   return null;
    	   
       }catch (Exception e){
    	   e.printStackTrace();
    	   return null;
       }finally{
    	   if (session.isOpen())
       		session.close();
       }
    }
  
    public Tipo addTipo(Tipo t) {

    	Session session = null;
        Transaction transaction = null;
        try {
        	        	        		
			session = HibernateUtil.getSessionFactory().getCurrentSession();
	        transaction = session.beginTransaction();
	        session.save(t);
	        transaction.commit();
	        return t;
        	            
        }catch (Exception e){
        	e.printStackTrace();
            return null;
        }finally{
        	if (session.isOpen())
        		session.close();
        }
    }

    public boolean modify(Tipo tipoModified) {
    	Session session =null;
        try  {
           session = HibernateUtil.getSessionFactory().getCurrentSession();
           session.beginTransaction();
           session.saveOrUpdate(tipoModified);
           session.flush();
           session.getTransaction().commit();
           return true;
        }catch (Exception e){
        	e.printStackTrace();
            return false;
        }finally{
        	if (session.isOpen())
        		session.close();
        }
    }

    public Tipo getTipo(Integer id) {
    	Session session = null;        
        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Query query = session.createQuery("from Tipo where id = :id");
            query.setParameter("id", id);
            
            List q = query.list();
            session.getTransaction().commit();
            
            if ((q != null)&&(q.isEmpty())){
            	return null;
            }else if ((q != null)&&(!q.isEmpty())){
            	return (Tipo)q.get(0);
            }
            
            return null;            
        }catch (Exception e){
        	e.printStackTrace();
            return null;
        }finally{
        	if (session.isOpen())
        		session.close();
        }
    }

    public boolean delete(int id) {
    	Session session = null;
        Transaction transaction = null;
        try{
        	session = HibernateUtil.getSessionFactory().getCurrentSession();
        	transaction = session.beginTransaction();
        	Query createQuery = session.createQuery("delete from Tipo where id = :id");
        	createQuery.setParameter("id", id);
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
        
    public Set<Vocabulario> getVocabulariosTipo (int idTipo){
    	Session session = null;        
        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Query query = session.createQuery("from Tipo where id = :id");
            query.setParameter("id", idTipo);
            
            List q = query.list();
            Tipo c = (Tipo)q.get(0);
            //al utilizar un metodo del set, hibernate va  a la bd a por el contenido,
            //debe hacerse en dentro de la misma session que cuando vas a por la entidad.
            if (c.getVocabularios().isEmpty()){
            	return null;
            }
                        
            session.getTransaction().commit();
            
            return c.getVocabularios();
                     
        }catch (Exception e){
        	e.printStackTrace();
            return null;
        }finally{
        	if (session.isOpen())
        		session.close();
        }
    }
    
    
}
