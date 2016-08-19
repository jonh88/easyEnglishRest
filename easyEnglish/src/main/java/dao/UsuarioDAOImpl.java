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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static Logger logger = LoggerFactory.getLogger(UsuarioDAOImpl.class);
    
    public UsuarioDAOImpl(){
        this.cn = new ConnectionDB();
        this.managerTest = new TestDAOImpl();
        this.vocManager = new VocabularioDAOImpl();
    }

    public Usuario addUser(Usuario user) {
    	logger.info("Adding user: {}", user);
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
        		logger.warn("User already exists.");
        		return null;
        	}
            
        }catch (Exception e){
        	logger.error("Error adding the user: {}", user, e);
        	//e.printStackTrace();
            return null;
        }finally{
        	if ((session != null)&&(session.isOpen()))
        		session.close();
        }
        
    }

    public boolean updateUser(Usuario user) { 
    	logger.info("Updating user: {}", user);
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
        	logger.error("Error adding the user: {}", user, e);
        	//e.printStackTrace();
            return false;
        }finally{
        	if (session.isOpen())
        		session.close();
        }
    }

    public Usuario findUserByEmail(String email) {
    	logger.info("Searching user with email: {}", email);
    	Session session = null;        
        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();           
            Query query = session.createQuery("from Usuario where email = :e");
            query.setParameter("e", email);
            List user = query.list();
            
            session.getTransaction().commit();
            if ((user != null)&&(user.isEmpty())){
            	logger.warn("List of users is empty");
            	return null;
            }else if ((user != null)&&(!user.isEmpty())){
            	logger.debug("Retrieving user: {}", (Usuario)user.get(0));
            	return (Usuario)user.get(0);
            }
            logger.debug("List of users is null");
            return null;            
        }catch (Exception e){
        	logger.error("Error finding the user with email: {]", email, e);
        	//e.printStackTrace();
            return null;
        }finally{
        	if (session.isOpen())
        		session.close();
        } 
       
    }

    public Usuario findUserById(int idUser){
    	logger.info("Searching user with id: {}", idUser);
    	Session session = null;        
        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Query query = session.createQuery("from Usuario where id = :id");
            query.setParameter("id", idUser);
            
            List user = query.list();
            session.getTransaction().commit();
            
            if ((user != null)&&(user.isEmpty())){
            	logger.warn("List of users is empty");
            	return null;
            }else if ((user != null)&&(!user.isEmpty())){
            	logger.debug("Retrieving user: {}", (Usuario)user.get(0));
            	return (Usuario)user.get(0);
            }
            
            logger.debug("List of users is null");
            return null;            
        }catch (Exception e){
        	logger.error("Error finding the user with id: {}", idUser, e);
        	//e.printStackTrace();
            return null;
        }finally{
        	if (session.isOpen())
        		session.close();
        }
    	
    }
    
    public List<Usuario> getUsers() {
    	logger.info("Getting all users");
        Session session = null;               
        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Query query = session.createQuery("from Usuario");
            List users = query.list();
            session.getTransaction().commit();
            if ((users != null)&&(users.isEmpty())){
            	logger.warn("List of users is empty");
            	return null;
            }else if ((users != null)&&(!users.isEmpty())){
            	logger.debug("Retrieving list of users");
            	return (List<Usuario>)users;
            }
            return null;
        }catch (Exception e){
        	logger.error("Error getting the list of users", e);
        	//e.printStackTrace();
            return null;
        }finally{
        	if (session.isOpen())
        		session.close();
        }
        
    }

    public boolean delete(int idUser) {
        logger.info("Deleting user with id: {}", idUser);
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
        	logger.error("Error deleting user with id: {}", idUser, e);
        	//e.printStackTrace();
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
    	logger.info("Getting tests of user with id: {}", idUser);
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
            	logger.debug("Tests of user: {} is empty", c);
            	return null;
            }
                        
            session.getTransaction().commit();
            logger.debug("Retrieving list of test of the user: {}",c);
            return c.getTests();
                     
        }catch (Exception e){
        	logger.error("Error getting list of test of user with id: {}", idUser, e);
        	//e.printStackTrace();
            return null;
        }finally{
        	if (session.isOpen())
        		session.close();
        }
    	
    }

	public Set<Vocabulario> getVocabularios(int idUser) {
		logger.info("Getting vocabularies of user with id: {}", idUser);
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
            	logger.debug("Vocabularies of user: {} is empty", c);
            	return null;
            }
                        
            session.getTransaction().commit();
            
            logger.debug("Retrieving list of vocabularies of the user: {}",c);
            return c.getVocabularios();
                     
        }catch (Exception e){
        	logger.error("Error getting list of vocabularies of user with id: {}", idUser, e);
        	//e.printStackTrace();
            return null;
        }finally{
        	if (session.isOpen())
        		session.close();
        }
		
	}

	public Set<Cuestionario> getCuestionarios(int idUser) {
		logger.info("Getting cuestionaries of user with id: {}", idUser);
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
            	logger.debug("Cuetionaries of user: {} is empty", c);
            	return null;
            }
                        
            session.getTransaction().commit();
            
            logger.debug("Retrieving list of cuestionaries of the user: {}",c);
            return c.getCuestionarios();
                     
        }catch (Exception e){
        	logger.error("Error getting list of cuestionaries of user with id: {}", idUser, e);
        	//e.printStackTrace();
            return null;
        }finally{
        	if (session.isOpen())
        		session.close();
        }
		
	}

	public boolean insertVocabulario(int idUser, Vocabulario voc) {
		logger.info("Inserting vocabulario: {} for user with id: {}",voc, idUser);
		Session session = null;        
        try{
        	/**
             * comprobar que el vocabulario existe en la bbdd, 
             * sino insertar y luego añadir al user.
             */
            Vocabulario v;
            if (vocManager.vocabularyExists(voc.getEnglish())){
            	logger.debug("Vocabulary exists. Getting vocabulary.");
            	v = vocManager.findVocabularyByEnglish(voc.getEnglish());          
            }else{
            	logger.debug("Vocabulary does not exist. Inserting vocabulariy");
            	v = vocManager.insertVocabulary(voc); 
            }        	
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
            	logger.debug("The user did not have the vocabulary. Inserting vocabulary: {}",
            			v);
            	user.getVocabularios().add(v);
            	session.getTransaction().commit();
            }else{
            	logger.debug("The user already had the vocabulary");
            	return false;
            }
            
            logger.debug("Vocabulary: {} inserted for user: {}", v, user);
            return true;
                     
        }catch (Exception e){
        	logger.error("Error inserting vocabulary: {} for user with id: {}", voc, idUser, e);
        	//e.printStackTrace();
            return false;
        }finally{
        	if (session.isOpen())
        		session.close();
        }
		
	}

	public boolean deleteVocabulario (int idUser, int idVoc){
		logger.info("Deleting vocabulary with id: {} for user with id: {}", idVoc, idUser);
		
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
            		logger.debug("Vocabulary {} found", v);
            		f = true;
            		break;
            	}            		
            	index++;
            }
            
            if (f){
            	logger.debug("Removing vocabulary");
            	vocs.remove(index);
            	Set<Vocabulario> newSet = new HashSet<Vocabulario>(vocs);
                
                user.setVocabularios(newSet);
                
                session.save(user);
                tran.commit();
            }
            	           
            return f;
                     
        }catch (Exception e){
        	logger.error("Error deleting vocabulary with id: {} for user with id: {}", 
        			idVoc, idUser, e);
        	//e.printStackTrace();
            return false;
        }finally{
        	if (session.isOpen())
        		session.close();
        }
	}
}
