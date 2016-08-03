/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import conn.ConnectionDB;
import conn.HibernateUtil;
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
    
    public Vocabulario addVocabulary(Vocabulario voc, int idUsr) {
    	/*Session session = null;
        Transaction transaction = null;
         try {
         	         	
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
         }*/
         
        int result = 0;        
        //en bbdd debe estar todo a minúsculas
        String minEnglish = voc.getEnglish().toLowerCase();
        String minSpanish = voc.getSpanish().toLowerCase();                     
              
        
        try {
            Connection conex = this.cn.getConn();
                          
            if (!(vocabularyExists(minEnglish))){
                //si no existe la palabra se añade a tbl_vocabulary 
                String sql = "insert into tbl_vocabulary (english, spanish, tbl_typeVoc_id_type) values (?,?,?)";
                PreparedStatement ps = conex.prepareStatement(sql);                        
                ps.setString(1, minEnglish);
                ps.setString(2, minSpanish);
                ps.setInt(3, voc.getTipo());                              
                result = ps.executeUpdate();
                ps.close();
            }                    
            
            // hay que enlazarla a este usuario en tbl_voc_user
            String sqlInsert = "insert into tbl_voc_user (tbl_Users_Id_usr, tbl_Vocabulary_id_Vocabulary)"+
                " values (?,?)";
            Vocabulario v = getVocabulary(minEnglish);                
            PreparedStatement ps = conex.prepareStatement(sqlInsert);
            ps.setInt(1,idUsr);
            ps.setInt(2, v.getId());
            result = ps.executeUpdate();
            ps.close();
            if (result==0)
                return null;
            else
                return null;            
                  
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
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
        }catch (Exception ex){        	
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
            return null;
        }finally{
        	if (session.isOpen())
        		session.close();
        }
    }

    private Vocabulario findVocabularyByEnglish(String english){
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
            return null;
        }finally{
        	if (session.isOpen())
        		session.close();
        }
    	
    }
    
    public List<Vocabulario> getVocabularies(int idUser) {
    	
    	/*Session session = null;               
        try{
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Query query = session.createQuery("from Vocabulario");
            List vocabs = query.list();
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
        }*/
    	
        String select = "select * from tbl_vocabulary inner join tbl_voc_user on tbl_vocabulary.id_Vocabulary " +
        				"= tbl_voc_user.tbl_Vocabulary_id_Vocabulary " +
        				"where tbl_voc_user.tbl_Users_Id_usr = ?";
        List<Vocabulario> vocab = new ArrayList<Vocabulario>();
        
        try{
            Connection conex = this.cn.getConn();            
            PreparedStatement ps = conex.prepareStatement(select);   
            ps.setInt(1, idUser);
            ResultSet res = ps.executeQuery();
                                 
            while(res.next()){                 
                Vocabulario nVoc = new Vocabulario();
                nVoc.setId(res.getInt("id_Vocabulary"));
                nVoc.setEnglish(res.getString("english"));                
                nVoc.setSpanish(res.getString("spanish"));
                nVoc.setTipo(res.getInt("tbl_typeVoc_id_type"));
                              
                vocab.add(nVoc);
            }                    
            
            return vocab;
            
        }catch (Exception e){
            System.out.print(e.getMessage());
            return null;
        }
    }

    public boolean delete(int idVoc) {
    	//al borrar un voc se debe borrar tb el reg en la tbl_voc_user
                
        String deleteVocUsr = "DELETE FROM tbl_voc_user where tbl_Vocabulary_id_Vocabulary = ? and "+
                "tbl_users_Id_usr = ?";
        //String deleteVoc = "DELETE FROM tbl_Vocabulary where id_Vocabulary = ?";
               
        try{
            //solo se borra de tbl_voc_usr, otros usuarios podrian tener la misma palabra
            Connection conex = cn.getConn();
            PreparedStatement ps = conex.prepareStatement(deleteVocUsr);
            ps.setInt(1, idVoc);
            ps.setInt(2, idUser);
            
            int result = ps.executeUpdate();
            ps.close();
            if (result == 0)
                return false;
            else
                return true;     
          
        }catch (Exception e){
            return false;
        }
    }
    
    private boolean vocabularyExists (String mEnglish){
        
		 Vocabulario voc = this.findVocabularyByEnglish(mEnglish);
		 if (voc == null)
		 	return false;
		 else
		 	return true;
    	      
    }
    
}
