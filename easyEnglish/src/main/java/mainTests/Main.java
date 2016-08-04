package mainTests;

import org.hibernate.Session;

import conn.HibernateUtil;
import dao.AuthenticationImpl;
import dao.PreguntaDAOImpl;
import dao.UsuarioDAOImpl;
import daoTests.testUsuario;
import domain.*;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Pregunta q = new Pregunta();
		q.setPregunta("what is your name?");
		q.setRespA("Mary");
		q.setRespB("Jane");
		q.setRespC("Jonh");
		q.setRespD("Juan");
		q.setRespOK("C");
		
		PreguntaDAOImpl preguntaManager = new PreguntaDAOImpl();
		
		//Pregunta a = preguntaManager.insertPregunta(q);
		boolean f = preguntaManager.delete(1);
		System.out.println(f);
		//System.out.println(a.getId());
					
	}

}
