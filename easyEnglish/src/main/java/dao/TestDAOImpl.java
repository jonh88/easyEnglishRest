
package dao;

import conn.HibernateUtil;
import domain.Test;
import domain.Vocabulario;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Jonh
 */
public class TestDAOImpl implements ITestDAO{
        
    public Test insertTest(int client, int numPreguntas) {
        
    	/*
		 * 1. Comprobar que numPreguntas es menor o igual al número de vocabularios
		 * 	  que el usuario tiene añadidos.
		 * 2. Crear test con num_fallos -1 (no corregido).
		 * 3. Obtener lista de todas las preguntas y eliminar aleatoriamente
		 * 	  hasta que tamaño de lista = numPreguntas.
		 * 4. Insertar Vocs al Test.
		 * 5. Persistir Test.	 
		 */
		
		//1
    	UsuarioDAOImpl userManager = new UsuarioDAOImpl();    	
    	    	
		if (numPreguntas > userManager.getVocabularios(client).size()){
			return null;
		}
		//2
		int fecha = 20160101;		
		Test newTest = new Test(userManager.findUserById(client), fecha, numPreguntas, -1);
		//3
		List<Vocabulario> vocsUser = new ArrayList<Vocabulario>();
		vocsUser.addAll(userManager.getVocabularios(client));
		Random r = new Random(System.currentTimeMillis());
		int numAle;
		while (vocsUser.size() > numPreguntas){
			numAle = r.nextInt(vocsUser.size());
			vocsUser.remove(numAle);
		}
		//4
		for (Vocabulario v : vocsUser){
			newTest.getVocabularios().add(v);
		}
		//5
		Session session =null;
        try  {
           session = HibernateUtil.getSessionFactory().getCurrentSession();
           session.beginTransaction();
           session.save(newTest);           
           session.getTransaction().commit();
           return newTest;
        }catch (Exception ex){        	
            return null;
        }finally{
        	if (session.isOpen())
        		session.close();
        }
        
    }

    public boolean update (Test test){
    	Session session =null;
        try  {
           session = HibernateUtil.getSessionFactory().getCurrentSession();
           session.beginTransaction();
           session.saveOrUpdate(test);
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
 
    public Test getTestObject (int id) {
    	Session session = null;        
        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Query query = session.createQuery("from Test where id = :id");
            query.setParameter("id", id);
            
            List q = query.list();
            session.getTransaction().commit();
            
            if ((q != null)&&(q.isEmpty())){
            	return null;
            }else if ((q != null)&&(!q.isEmpty())){
            	return (Test)q.get(0);
            }
            
            return null;            
        }catch (Exception e){
            return null;
        }finally{
        	if (session.isOpen())
        		session.close();
        }
    }

    public Set<Vocabulario> getVocabularioTest (int idTest) {
    	Session session = null;        
        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Query query = session.createQuery("from Test where id = :id");
            query.setParameter("id", idTest);
            
            List q = query.list();
            Test c = (Test)q.get(0);
            //al utilizar un metodo del set, hibernate va  a la bd a por las preguntas,
            //debe hacerse en dentro de la misma session que cuando vas a por la entidad
            if (c.getVocabularios().isEmpty()){
            	return null;
            }
                        
            session.getTransaction().commit();
            
            return c.getVocabularios();
                     
        }catch (Exception e){
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
        	Query createQuery = session.createQuery("delete from Test where id = :id");
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
        
}
