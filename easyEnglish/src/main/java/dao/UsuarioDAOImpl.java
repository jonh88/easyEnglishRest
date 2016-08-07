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
    
    public UsuarioDAOImpl(){
        this.cn = new ConnectionDB();
        this.managerTest = new TestDAOImpl();
    }

    public Usuario addUser(Usuario user) {
       Session session = null;
       Transaction transaction = null;
        try {
        	//encriptar pass
        	AuthenticationImpl auth = new AuthenticationImpl();
        	user.setPwd(auth.encrypt(user.getPwd()));
        	
        	//añadir usuario solo si no existe
        	if (!this.userExists(user.getEmail())){
        		session = HibernateUtil.getSessionFactory().getCurrentSession();
                transaction = session.beginTransaction();
                session.save(user);
                transaction.commit();
                return user;
        	}else{
        		return null;
        	}
            
        }catch (Exception e){
            return null;
        }finally{
        	if (session.isOpen())
        		session.close();
        }
        
    }

    public boolean updateUser(Usuario user) {        
    	Session session =null;
        try  {
           session = HibernateUtil.getSessionFactory().getCurrentSession();
           session.beginTransaction();
           session.saveOrUpdate(user);
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
            return null;
        }finally{
        	if (session.isOpen())
        		session.close();
        }
		
	}
}
