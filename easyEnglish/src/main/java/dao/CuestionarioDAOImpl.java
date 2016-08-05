package dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import conn.HibernateUtil;
import domain.Cuestionario;
import domain.Pregunta;

public class CuestionarioDAOImpl implements ICuestionarioDAO{

	public Cuestionario insertCuestionario(int client, int numPreguntas) {
		/*
		 * 1. Comprobar que numPreguntas es menor o igual al número de registros
		 * 	  de la tbl_Preguntas.
		 * 2. Crear registro cuestionario en tbl_cuestionario con num_fallos vacío.
		 * 3. Extraer una lista de preguntas al azar de tbl_Preguntas igual a numPreguntas 
		 * 	  e insertarlas en tbl_cuestionario_pregunta	 
		 */
		
		int fecha = 20160101;
		Cuestionario q = new Cuestionario(client,fecha,numPreguntas,0);
		return q;
	}

	public boolean update(Cuestionario cModificado) {
		Session session =null;
        try  {
           session = HibernateUtil.getSessionFactory().getCurrentSession();
           session.beginTransaction();
           session.saveOrUpdate(cModificado);
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

	public Cuestionario getCuestionarioObject(Integer id) {
		Session session = null;        
        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Query query = session.createQuery("from Cuestionario where id = :id");
            query.setParameter("id", id);
            
            List q = query.list();
            session.getTransaction().commit();
            
            if ((q != null)&&(q.isEmpty())){
            	return null;
            }else if ((q != null)&&(!q.isEmpty())){
            	return (Cuestionario)q.get(0);
            }
            
            return null;            
        }catch (Exception e){
            return null;
        }finally{
        	if (session.isOpen())
        		session.close();
        }
	}

	public List<Pregunta> listCuestionario(int idCuestionario) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean delete(int id) {
		Session session = null;
        Transaction transaction = null;
        try{
        	session = HibernateUtil.getSessionFactory().getCurrentSession();
        	transaction = session.beginTransaction();
        	Query createQuery = session.createQuery("delete from Cuestionario where id = :id");
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
