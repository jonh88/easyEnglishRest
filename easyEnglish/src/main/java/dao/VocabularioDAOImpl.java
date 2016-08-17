package dao;

import conn.ConnectionDB;
import conn.HibernateUtil;
import domain.Pregunta;
import domain.Tipo;
import domain.Usuario;
import domain.Vocabulario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;


/**
 *
 * @author Jonh
 */
public class VocabularioDAOImpl implements IVocabularioDAO{

    private ConnectionDB cn;
    
    public VocabularioDAOImpl (){
        this.cn = new ConnectionDB();
    }
    
    public Vocabulario insertVocabulary(Vocabulario voc) {
    	//todos los vocabularios iran en lowerCase
    	voc.setEnglish(voc.getEnglish().toLowerCase());
    	voc.setSpanish(voc.getSpanish().toLowerCase());
    	
    	Session session = null;
        Transaction transaction = null;
        try {
        	        	        		
			session = HibernateUtil.getSessionFactory().getCurrentSession();
	        transaction = session.beginTransaction();
	        session.save(voc);
	        transaction.commit();
	        return voc;
        	            
        }catch (Exception e){
        	e.printStackTrace();
            return null;
        }finally{
        	if (session.isOpen())
        		session.close();
        }
    	    	
    }

    public boolean updateVocabulario(Vocabulario vocModified) {
    	Session session =null;
        try  {
           session = HibernateUtil.getSessionFactory().getCurrentSession();
           session.beginTransaction();
           session.saveOrUpdate(vocModified);
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

    public Vocabulario findVocabularyById(int id) {
    	Session session = null;        
        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Query query = session.createQuery("from Vocabulario where id = :id");
            query.setParameter("id", id);
            
            List voc = query.list();
            session.getTransaction().commit();
            
            if ((voc != null)&&(voc.isEmpty())){
            	return null;
            }else if ((voc != null)&&(!voc.isEmpty())){
            	return (Vocabulario)voc.get(0);
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
         
    public boolean delete(int idVoc) {
    	Session session = null;
        Transaction transaction = null;
        try{
        	session = HibernateUtil.getSessionFactory().getCurrentSession();
        	transaction = session.beginTransaction();
        	Query createQuery = session.createQuery("delete from Vocabulario where id = :id");
        	createQuery.setParameter("id", idVoc);
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

    public List<Vocabulario> getAll() {
    	Session session = null;        
        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Query query = session.createQuery("from Vocabulario");            
            
            List q = query.list();
            session.getTransaction().commit();
            
            if ((q != null)&&(q.isEmpty())){
            	return null;
            }else if ((q != null)&&(!q.isEmpty())){
            	return (List<Vocabulario>)q;
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
    
    public boolean vocabularyExists (String mEnglish){
    	        
		 Vocabulario voc = this.findVocabularyByEnglish(mEnglish.toLowerCase());
		 if (voc == null)
		 	return false;
		 else
		 	return true;
    	      
    }
    
    public Vocabulario findVocabularyByEnglish(String english){
    	Session session = null;        
        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Query query = session.createQuery("from Vocabulario where english = :english");
            query.setParameter("english", english);
            
            List voc = query.list();
            session.getTransaction().commit();
            
            if ((voc != null)&&(voc.isEmpty())){
            	return null;
            }else if ((voc != null)&&(!voc.isEmpty())){
            	return (Vocabulario)voc.get(0);
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
    
    
}
