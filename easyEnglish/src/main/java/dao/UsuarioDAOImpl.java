/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import conn.HibernateUtil;
import dao.IUsuarioDAO;
import domain.Test;
import domain.Usuario;
import java.util.ArrayList;
import java.util.List;
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

    public List<Test> getTestUser (int idUser){
    	String select = "select * from tbl_test where tbl_Users_Id_usr = ?";
        List<Test> aTest = new ArrayList<Test>();
        
        try{
            Connection conex = this.cn.getConn();            
            PreparedStatement ps = conex.prepareStatement(select);
            ps.setInt(1, idUser);
            ResultSet res = ps.executeQuery();
            // relleno un list con los vocabularios pertenecientes al test en cuestion
            while(res.next()){                                
            	aTest.add(this.managerTest.getTestObject(res.getInt("Id_test")));                
            }                    
            
            return aTest;
            
        }catch (Exception e){
            return null;
        }
    }
}
