package mainTests;

import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.hibernate.Session;
import org.junit.Test;

import conn.ConnectionDB;
import conn.HibernateUtil;
import dao.AuthenticationImpl;
import dao.CuestionarioDAOImpl;
import dao.PreguntaDAOImpl;
import dao.UsuarioDAOImpl;
import daoTests.testUsuario;
import domain.*;

public class Main {
	private static Pregunta q1,q2,q3,q4,q5; 		
	private static Cuestionario cuestionario;
	private static UsuarioDAOImpl userManager = new UsuarioDAOImpl(); 
	private static final int NUMPREGUNTAS = 5;

	public static void main(String[] args) {
				
		/*init();
		
		Usuario testUser = userManager.findUserByEmail("prueba@gmail.com");
		Session session = HibernateUtil.getSessionFactory().openSession();		
		session.beginTransaction();
		
		cuestionario = new Cuestionario(testUser,20160101,NUMPREGUNTAS,-1);
		Set<Pregunta> preguntas = new HashSet<Pregunta>();
		preguntas.add(q1);
		preguntas.add(q2);
		preguntas.add(q3);
		preguntas.add(q4);
		preguntas.add(q5);
		cuestionario.setPreguntas(preguntas);
		
		session.save(cuestionario);		
		session.getTransaction().commit();
			*/	
		
		
		UsuarioDAOImpl userManager = new UsuarioDAOImpl();
    	Usuario user = userManager.findUserById(4);
    	
    	System.out.println(user.getPwd());
    	
    	AuthenticationImpl auth = new AuthenticationImpl();
    	user.setPwd(auth.encrypt(user.getPwd()));
    	
    	boolean success = userManager.updateUser(user);
    	
    	System.out.println(success);
    	
    	
					
	}
	
	public static void init(){
		q1 = new Pregunta();
		q1.setPregunta("question 1");
		q1.setRespA("a");
		q1.setRespB("b");
		q1.setRespC("c");
		q1.setRespD("d");
		q1.setRespOK("a");
		
		q2 = new Pregunta();
		q2.setPregunta("question 2");
		q2.setRespA("a");
		q2.setRespB("b");
		q2.setRespC("c");
		q2.setRespD("d");
		q2.setRespOK("a");
		
		q3 = new Pregunta();
		q3.setPregunta("question 3");
		q3.setRespA("a");
		q3.setRespB("b");
		q3.setRespC("c");
		q3.setRespD("d");
		q3.setRespOK("a");
	
		q4 = new Pregunta();
		q4.setPregunta("question 4");
		q4.setRespA("a");
		q4.setRespB("b");
		q4.setRespC("c");
		q4.setRespD("d");
		q4.setRespOK("a");
		
		q5 = new Pregunta();
		q5.setPregunta("question 5");
		q5.setRespA("a");
		q5.setRespB("b");
		q5.setRespC("c");
		q5.setRespD("d");
		q5.setRespOK("a");
		
		PreguntaDAOImpl preguntaManager = new PreguntaDAOImpl();
		
		preguntaManager.insertPregunta(q1);
		preguntaManager.insertPregunta(q2);
		preguntaManager.insertPregunta(q3);
		preguntaManager.insertPregunta(q4);
		preguntaManager.insertPregunta(q5);
				
	}

	public static void finish(){
		PreguntaDAOImpl preguntaManager = new PreguntaDAOImpl();
		preguntaManager.delete(q1.getId());
		preguntaManager.delete(q2.getId());
		preguntaManager.delete(q3.getId());
		preguntaManager.delete(q4.getId());
		preguntaManager.delete(q5.getId());
	}
}
