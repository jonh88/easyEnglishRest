/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

//import conn.ConnectionDB;
import domain.Tipo;
import conn.HibernateUtil;
import java.util.List;
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
       try{
    	   session = HibernateUtil.getSessionFactory().getCurrentSession();
    	   Query query = session.createQuery("from tbl_typevoc");
    	   
    	   List listaTipos = query.list();
    	   if ((listaTipos != null)&&(!listaTipos.isEmpty()))
    			   return (List<Tipo>)listaTipos;
    	   else
    		   return null;
    	   
       }catch (Exception e){
    	   e.printStackTrace();
    	   return null;
       }finally{
    	   session.close();
       }
    }
  
    public Tipo addTipo(Tipo t) {

        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(t);            
            transaction.commit();
            return t;            
        }catch (Exception e){
        	e.printStackTrace();
            return null;
        }finally{
        	session.close();
        }
    }

    public Tipo modify(Tipo tipoModified) {
       Session session = null;                                                           
        try  {
           session = HibernateUtil.getSessionFactory().getCurrentSession();
           session.saveOrUpdate(tipoModified);
           session.flush();
           return tipoModified; 
        }catch (Exception ex){
        	ex.printStackTrace();
            return null;
        } finally{
        	session.close();
        }
    }

    public Tipo getTipo(Integer id) {
        Session session = null;
        try{
           session = HibernateUtil.getSessionFactory().getCurrentSession();
           Query query = session.createQuery("from tbl_typevoc t where t.id_type = :id");
           query.setParameter("id", id);
           
           List listaTipos = query.list();
           if ((listaTipos != null)&&(!listaTipos.isEmpty()))
        	   return (Tipo) listaTipos.get(0);
           else
        	   return null;
            
        }catch (Exception e){
        	e.printStackTrace();
            return null;
        }finally{
        	session.close();
        }
    }

    public boolean delete(int id) {
        Session session = null;
        Transaction transaction = null;
        try{
        	session = HibernateUtil.getSessionFactory().getCurrentSession();
        	transaction = session.beginTransaction();
        	Query createQuery = session.createQuery("delete from tbl_typevoc t where t.id_type = :id");
        	createQuery.setParameter("id", id);
        	createQuery.executeUpdate();
        	transaction.commit();
        	return true;
        }catch (Exception e){
        	e.printStackTrace();
            return false;
        }finally{
        	session.close();
        }
    }
    
    private boolean typeExists (String type){
        String existe = "select * from tbl_typevoc where type ='"+type+"';";
        boolean result = false;
        try{
           return false;
            
        }catch (Exception e){
            return false;
        }
    }
	
    
}
