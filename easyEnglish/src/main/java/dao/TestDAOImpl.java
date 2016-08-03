
package dao;

import conn.ConnectionDB;
import domain.Test;
import domain.Usuario;
import domain.Vocabulario;
import dao.VocabularioDAOImpl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Jonh
 */
public class TestDAOImpl implements ITestDAO{

    private ConnectionDB cn;
    private VocabularioDAOImpl managerVoc;
        
    public TestDAOImpl(){
        this.cn = new ConnectionDB();
        this.managerVoc = new VocabularioDAOImpl();
    }
        
    public int addTest(int client, int numPreguntas) {
        
    	int result = 0;
		int user= client;
        
        String sqlInsert = "insert into tbl_Test (tbl_Users_Id_usr, fecha, num_preguntas, num_fallos)"+
                "values (?,?,?,?);";
        String sqlCount = "Select COUNT(*) from tbl_Vocabulary INNER JOIN tbl_voc_user ON"+
                      " tbl_Vocabulary.Id_Vocabulary = tbl_Voc_user.tbl_Vocabulary_id_Vocabulary"+
                      " WHERE tbl_Voc_user.tbl_Users_Id_usr = ?";
        String sqlSelect = "Select * from tbl_Vocabulary INNER JOIN tbl_voc_user ON"+
                      " tbl_Vocabulary.Id_Vocabulary = tbl_Voc_user.tbl_Vocabulary_id_Vocabulary"+
                      " WHERE tbl_Voc_user.tbl_Users_Id_usr = ?"; 
       
               
        try {
        	Connection conex = this.cn.getConn();
        	
        	/*obtengo el id_user
        	PreparedStatement ps = conex.prepareStatement("select id_user from tbl_authz where clientUUID = ?");
        	ps.setString(1, client);
        	ResultSet rs = ps.executeQuery();
        	rs.next();
        	user = rs.getInt(1);        	        	       	
        	ps.close();*/
        	
            /*compruebo que existen mas vocabularios que numPreguntas*/     
        	PreparedStatement ps;
            ps = conex.prepareStatement(sqlCount);
            
            ps.setInt(1, user);
            ResultSet res = ps.executeQuery();
            res.next();
            int numReg = res.getInt(1);
            
            ps.close();
            
             //si el numero de registros es menor que las preguntas no se genera el test
            if (numReg < numPreguntas)
                return -1;
            
            /*inserto TEST*/
            ps = conex.prepareStatement(sqlInsert);
            //pongo los parametros
            ps.setInt(1, user);
            //obtengo la fecha actual
            Date fecha = new Date();
            String fechaActual = String.valueOf(fecha.getYear()+1900)+/*el objeto date guarda el año con la 
                                                                        formula(y-1900) por eso sumo 1900*/
                    String.valueOf(fecha.getMonth()+1)+
                    String.valueOf(fecha.getDate());
            
            ps.setInt(2, Integer.parseInt(fechaActual));
            ps.setInt(3, numPreguntas);
            //inicializamos numFallos a 0 al crear el test
            ps.setInt(4, 0);
            
            //ejecuto insert
            result = ps.executeUpdate();            
            //cierro objetos 
            ps.close();
                     
                        
            /*
              Con el test creado ahora elegimos tantos vocabularios como numPreguntas
              de forma aleatoria. Con cada vocabulario seleccionado, insertamos un nuevo 
              registro en tbl_test_vocab compuesto por el id del vocabulario seleccionado 
              mas el id del test creado.              
            */
            
            ArrayList<Integer> idsVocab = new ArrayList<Integer>();
            int numAle;
            //para que genere secuencias totalmente aleatorias usamos los ms del reloj del 
            //sistema
            Random r = new Random(System.currentTimeMillis());
            
            ps = conex.prepareStatement(sqlSelect);
            ps.setInt(1, user);
            ResultSet res_ = ps.executeQuery();
                        
            //este bucle selecciona tantos vocab como numPreguntas del RecordSet
            //for (int i=0; i<numPreguntas; i++){
            while (idsVocab.size()< numPreguntas){
                numAle = r.nextInt(numReg)+1;
                //este bucle avanza el cursor aleatoriamente por el recordSet
                //el cursor siempre apuntara al reg 0
                for (int j = 0; j<numAle; j++){
                	res_.next();
                }
                if (!(idsVocab.contains(res_.getInt("id_Vocabulary")))){
                	idsVocab.add(res_.getInt("id_Vocabulary"));
                }                
                res_.beforeFirst();
            }
           ps.close();
           
            //en idsVocab estarán todos los ids de los vocabularios seleccionados
            //aleatoriamente
            /*INSERTAR en tbl_test_vocab con el id del test creado y todos los idVocab*/
           //ResultSet rs = ps.executeQuery();
           ps = conex.prepareStatement("Select id_test from tbl_test where tbl_Users_Id_usr = ? order by id_test DESC LIMIT 1");
           ps.setInt(1, user);
           res_ = ps.executeQuery();
           res_.next();
           int idTest = res_.getInt("id_test");
           ps.close();
           res_.close();
           
           for (int i = 0; i< idsVocab.size();i++){
               ps = conex.prepareStatement("insert into tbl_test_vocab (tbl_Test_Id_test, " +
                       "tbl_Vocabulary_id_Vocabulary) values ("+ idTest +", ?);");
               ps.setInt(1, idsVocab.get(i));
               ps.executeUpdate();
           }
            
           return idTest;
            
        }catch (Exception e){
            return -1;
        } 
        
    }

    public int modify(Test test){
    	/*
        query para actualizar los datos del usuario,
        userModified tiene que ser el usuario con los datos actualizados            
    	 */
  
    String sql = "update tbl_test set tbl_Users_Id_usr = ?, fecha = ?, num_preguntas = ?, num_fallos = ? where Id_test = ?";
                              
    try  {
        Connection conex = this.cn.getConn();
        //preparamos el preparedStatement con la query sql
        PreparedStatement ps = conex.prepareStatement(sql);

        // le pasamos los parametros
        ps.setInt(1,test.getIdUsr());
        ps.setInt(2,test.getFecha());
        ps.setInt(3,test.getNumPreguntas());
        ps.setInt(4,test.getNumFallos());
        ps.setInt(5, test.getIdTest());

        // call executeUpdate to execute our sql update statement
        int result = ps.executeUpdate();         
        ps.close();
        conex.close();
        if (result == 1)
            return 1;
        else
            return 0;
    }catch (Exception ex){  
    	System.out.println(ex.getMessage());
        return -1;
    } 
    }
 
    public Test getTestObject (Integer id) {
        String select = "select * from tbl_test where Id_test = "+id+";";
        Test getTest = new Test();
        try{
            Connection conex = this.cn.getConn();            
            PreparedStatement ps = conex.prepareStatement(select);            
            ResultSet res = ps.executeQuery();
                                 
            if (res.next()){
                //resultSet tendra solo un valor                             
                getTest.setIdTest(res.getInt("Id_test"));
                getTest.setIdUsr(res.getInt("tbl_Users_Id_usr"));
                getTest.setFecha(res.getInt("fecha"));
                getTest.setNumPreguntas(res.getInt("num_preguntas"));
                getTest.setNumFallos(res.getInt("num_fallos"));
            }else{
               throw new SQLException();
            }                      
            
            return getTest;
            
        }catch (Exception e){
            return null;
        }
    }

    public List<Vocabulario> getTest(int idTest) {
        String select = "select * from tbl_test_vocab where tbl_test_Id_test = ?";
        List<Vocabulario> aVoc = new ArrayList<Vocabulario>();
        
        try{
            Connection conex = this.cn.getConn();            
            PreparedStatement ps = conex.prepareStatement(select);
            ps.setInt(1, idTest);
            ResultSet res = ps.executeQuery();
            // relleno un list con los vocabularios pertenecientes al test en cuestion
            while(res.next()){
                                
            	//aVoc.add(this.managerVoc.getVocabulary(res.getInt("tbl_Vocabulary_id_Vocabulary")));
                /*t.setIdTest(res.getInt("Id_test"));
                t.setIdUsr(res.getInt("tbl_Users_Id_usr"));
                t.setFecha(res.getInt("fecha"));
                t.setNumPreguntas(res.getInt("num_preguntas"));
                t.setNumFallos(res.getInt("num_fallos"));
                
                tests.add(t);*/
            }                    
            
            return aVoc;
            
        }catch (Exception e){
            return null;
        }
    }

    public boolean delete(int idUsr) {
    	            
        try{
            Connection conex = cn.getConn();
            //selecciono todos los test que tiene el user
            String selectTest = "Select Id_test from tbl_test where tbl_Users_Id_usr = ?";
            PreparedStatement ps = conex.prepareStatement(selectTest);
            ps.setInt(1, idUsr);
            ResultSet rs = ps.executeQuery();
            
            ArrayList<Integer> tests = new ArrayList<Integer>();
            while (rs.next()){
            	tests.add(rs.getInt("Id_test"));
            }
            
            String deletefromTblTestVocab = "DELETE FROM tbl_test_vocab where tbl_Test_Id_test = ?";
            String deleteTest = "Delete from tbl_test where id_test = ?";
            int result = 1;
            int i= 0;
            //para cada uno de los test borro primero sus preguntas y por ultimo el test
            //for (int i = 0; i < tests.size(); i++){
            while ((i<tests.size())&&(result > 0)){
            	ps = conex.prepareStatement(deletefromTblTestVocab);
            	ps.setInt(1, tests.get(i));
            	result = ps.executeUpdate();
            	ps = conex.prepareStatement(deleteTest);
            	ps.setInt(1, tests.get(i));
            	result = ps.executeUpdate();
            	i++;
            }
            
            if (result > 0)
                return true;
            else
                return false;
          
        }catch (Exception e){
            return false;
        }
    }
    
    
}
