package dao;

import java.util.List;
import java.util.Random;
import java.util.Set;

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
		 * 2. Crear cuestionario con num_fallos -1 (no corregido).
		 * 3. Obtener lista de todas las preguntas y eliminar aleatoriamente
		 * 	  hasta que tamaño de lista = numPreguntas.
		 * 4. Insertar preguntas en set cuestionario.
		 * 5. Persistir cuestionario.	 
		 */
		
		//1
		PreguntaDAOImpl preguntaManager = new PreguntaDAOImpl();
		if (numPreguntas > preguntaManager.getCount()){
			return null;
		}
		//2
		int fecha = 20160101;
		Cuestionario newCuestionario = new Cuestionario(client,fecha, numPreguntas, -1);
		//3
		List<Pregunta> preg = preguntaManager.getAll();
		Random r = new Random(System.currentTimeMillis());
		int numAle;
		while (preg.size() > numPreguntas){
			numAle = r.nextInt(preg.size());
			preg.remove(numAle);
		}
		//4
		for (Pregunta p : preg){
			newCuestionario.getPreguntas().add(p);
		}
		//5
		Session session =null;
        try  {
           session = HibernateUtil.getSessionFactory().getCurrentSession();
           session.beginTransaction();
           session.save(newCuestionario);           
           session.getTransaction().commit();
           return newCuestionario;
        }catch (Exception ex){        	
            return null;
        }finally{
        	if (session.isOpen())
        		session.close();
        }
										
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

	public Set<Pregunta> getPreguntasCuestionario (int idCuestionario) {
		
		Cuestionario c = this.getCuestionarioObject(idCuestionario);
		
		return c.getPreguntas();
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
