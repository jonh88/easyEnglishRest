/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package conn;

import java.sql.Connection;
import java.sql.DriverManager;



/**
 *
 * @author Jonh
 */
public class ConnectionDB {
    private String host = "52.38.209.62:3306";
    private String bd = "platformdb";
    private String userDB = "admin";
    private String pwdDB = "P@ssw0rd";
    private Connection conex;
    
    public ConnectionDB(){
    	this.conex = null;
    }
    
    public Connection getConn(){
        //Connection conex = null;
        //this.log = new LogWriter("D:"+System.getProperty("file.separator")+"trazaError.txt");
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        	
            this.conex = DriverManager.getConnection("jdbc:mysql://"+host+"/"+bd, userDB, pwdDB);
        }catch (Exception e){            
            e.printStackTrace();
        }
        return conex;
        
        
    }
}
