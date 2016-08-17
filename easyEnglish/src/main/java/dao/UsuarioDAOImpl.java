/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import conn.HibernateUtil;
import dao.IUsuarioDAO;
import domain.Cuestionario;
import domain.Test;
import domain.Usuario;
import domain.Vocabulario;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import conn.ConnectionDB;

/**
 *
 * @author Jonh
 */
public class UsuarioDAOImpl implements IUsuarioDAO{
    
    private ConnectionDB cn;
    private TestDAOImpl managerTest;
    private VocabularioDAOImpl vocManager;
    
    public UsuarioDAOImpl(){
        this.cn = new ConnectionDB();
        this.managerTest = new TestDAOImpl();
        this.vocManager = new VocabularioDAOImpl();
    }

    public Usuario addUser(Usuario user) {
    	
    	boolean exist = false;
    	if (this.userExists(user.getEmail()))
    		exist = true;
    	
       Session session = null;
       Transaction transaction = null;
        try {      	        	
        	//añadir usuario solo si no existe
        	if (!exist){
        		//encriptar pass
            	AuthenticationImpl auth = new AuthenticationImpl();
            	user.setPwd(auth.encrypt(user.getPwd()));
            	
        		session = HibernateUtil.getSessionFactory().getCurrentSession();
                transaction = session.beginTransaction();
                session.save(user);
                transaction.commit();
                return user;
        	}else{
        		return null;
        	}
            
        }catch (Exception e){
        	e.printStackTrace();
            return null;
        }finally{
        	if ((session != null)&&(session.isOpen()))
        		session.close();
        }
        
    }

    public boolean updateUser(Usuario user) {        
    	Session session =null;
        try  {
           session = HibernateUtil.getSessionFactory().getCurrentSession();
           session.beginTransaction();
           Usuario u = (Usuario)session.get(Usuario.class, user.getId());
           u.setEmail(user.getEmail());
           u.setName(user.getName());
           u.setApellidos(user.getApellidos());
           //encriptar pass
       	   AuthenticationImpl auth = new AuthenticationImpl();
       	   u.setPwd(auth.encrypt(user.getPwd()));
           
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

    public Usuario findUserByEmail(String email) {
    	Session session = null;        
        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();           
            Query query = session.createQuery("from Usuario where email = :e");
            query.setParameter("e", email);
            List user = query.list();
            
            session.getTransaction().commit();
            if ((user != null)&&(user.isEmpty())){
            	return null;
            }else if ((user != null)&&(!user.isEmpty())){
            	return (Usuario)user.get(0);
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

    public Usuario findUserById(int idUser){
    	
    	Session session = null;        
        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Query query = session.createQuery("from Usuario where id = :id");
            query.setParameter("id", idUser);
            
            List user = query.list();
            session.getTransaction().commit();
            
            if ((user != null)&&(user.isEmpty())){
            	return null;
            }else if ((user != null)&&(!user.isEmpty())){
            	return (Usuario)user.get(0);
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
    
    public List<Usuario> getUsers() {      
        Session session = null;               
        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Query query = session.createQuery("from Usuario");
            List users = query.list();
            session.getTransaction().commit();
            if ((users != null)&&(users.isEmpty())){
            	return null;
            }else if ((users != null)&&(!users.isEmpty())){
            	return (List<Usuario>)users;
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

    public boolean delete(int idUser) {
        
        Session session = null;
        Transaction transaction = null;
        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            Query query = session.createQuery("delete from Usuario where id = :id");
            query.setParameter("id", idUser);
            query.executeUpdate();
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
        
    private boolean userExists (String email){
        Usuario user = this.findUserByEmail(email);
        if (user == null)
        	return false;
        else
        	return true;
    }

    public Set<Test> getTestUser (int idUser){
    	Session session = null;        
        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Query query = session.createQuery("from Usuario where id = :id");
            query.setParameter("id", idUser);
            
            List q = query.list();
            Usuario c = (Usuario)q.get(0);
            //al utilizar un metodo del set, hibernate va  a la bd a por los datos,
            //debe hacerse en dentro de la misma session que cuando vas a por la entidad ppal.
            if (c.getTests().isEmpty()){
            	return null;
            }
                        
            session.getTransaction().commit();
            
            return c.getTests();
                     
        }catch (Exception e){
        	e.printStackTrace();
            return null;
        }finally{
        	if (session.isOpen())
        		session.close();
        }
    	
    }

	public Set<Vocabulario> getVocabularios(int idUser) {
		Session session = null;        
        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Query query = session.createQuery("from Usuario where id = :id");
            query.setParameter("id", idUser);
            
            List q = query.list();
            Usuario c = (Usuario)q.get(0);
            //al utilizar un metodo del set, hibernate va  a la bd a por los datos,
            //debe hacerse en dentro de la misma session que cuando vas a por la entidad ppal.
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

	public Set<Cuestionario> getCuestionarios(int idUser) {
		Session session = null;        
        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Query query = session.createQuery("from Usuario where id = :id");
            query.setParameter("id", idUser);
            
            List q = query.list();
            Usuario c = (Usuario)q.get(0);
            //al utilizar un metodo del set, hibernate va  a la bd a por los datos,
            //debe hacerse en dentro de la misma session que cuando vas a por la entidad ppal.
            if (c.getCuestionarios().isEmpty()){
            	return null;
            }
                        
            session.getTransaction().commit();
            
            return c.getCuestionarios();
                     
        }catch (Exception e){
        	e.printStackTrace();
            return null;
        }finally{
        	if (session.isOpen())
        		session.close();
        }
		
	}

	public boolean insertVocabulario(int idUser, Vocabulario voc) {
		Session session = null;        
        try{
        	/**
             * comprobar que el vocabulario existe en la bbdd, 
             * sino insertar y luego añadir al user.
             */
            Vocabulario v;
            if (vocManager.vocabularyExists(voc.getEnglish()))
            	v = vocManager.findVocabularyByEnglish(voc.getEnglish());          
            else
            	v = vocManager.insertVocabulary(voc); 
        	        	
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Usuario user = (Usuario)session.get(Usuario.class, idUser);                                  	           
            
            //comprobar si ya tiene insertado el voc
            Iterator it = user.getVocabularios().iterator();            
            Vocabulario iterador;
            boolean insertado = false;
            while (it.hasNext()){
            	iterador = (Vocabulario)it.next();
            	if (iterador.getEnglish().equals(v.getEnglish())){
            		insertado = true;
            		break;
            	}            		
            }
            
            if (!insertado){
            	user.getVocabularios().add(v);
            	session.getTransaction().commit();
            }else
            	return false;
            
            
            return true;
                     
        }catch (Exception e){
        	e.printStackTrace();
            return false;
        }finally{
        	if (session.isOpen())
        		session.close();
        }
		
	}

	public boolean deleteVocabulario (int idUser, int idVoc){
		//VocabularioDAOImpl vocManager = new VocabularioDAOImpl();
		//Vocabulario voc = vocManager.findVocabularyById(idVoc);
		
		Session session = null;     
		Transaction tran= null;
        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tran = session.beginTransaction();
            Query query = session.createQuery("from Usuario where id = :id");
            query.setParameter("id", idUser);
            
            List q = query.list();
            Usuario user = (Usuario)q.get(0);
            
            List<Vocabulario> vocs = new ArrayList<Vocabulario>();
            
            vocs.addAll(user.getVocabularios());
            
            boolean f = false;
            int index = 0;
            for (Vocabulario v : vocs){
            	if (v.getId() == idVoc){
            		f = true;
            		break;
            	}            		
            	index++;
            }
            
            if (f){
            	vocs.remove(index);
            	Set<Vocabulario> newSet = new HashSet<Vocabulario>(vocs);
                
                user.setVocabularios(newSet);
                
                session.save(user);
                tran.commit();
            }
            	            
            return f;
                     
        }catch (Exception e){
        	e.printStackTrace();
            return false;
        }finally{
        	if (session.isOpen())
        		session.close();
        }
	}
}
