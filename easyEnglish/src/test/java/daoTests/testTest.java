package daoTests;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import conn.ConnectionDB;
import conn.HibernateUtil;
import dao.CuestionarioDAOImpl;
import dao.PreguntaDAOImpl;
import dao.TestDAOImpl;
import dao.TipoDAOImpl;
import dao.UsuarioDAOImpl;
import dao.VocabularioDAOImpl;
import domain.Cuestionario;
import domain.Pregunta;
import domain.Tipo;
import domain.Usuario;
import domain.Vocabulario;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class testTest {

	private static Vocabulario q1,q2,q3,q4,q5; 	
	private static TestDAOImpl testManager = new TestDAOImpl();
	private static domain.Test test;
	private static UsuarioDAOImpl userManager = new UsuarioDAOImpl(); 
	private static final int NUMPREGUNTAS = 5;
	
	@BeforeClass
	public static void setUp(){
		TipoDAOImpl tipoManager = new TipoDAOImpl();
		Tipo t = tipoManager.getTipo(4);
		
		q1 = new Vocabulario();
		q1.setEnglish("test1");
		q1.setSpanish("prueba1");
		q1.setTipo(t);
		
		q2 = new Vocabulario();
		q2.setEnglish("test2");
		q2.setSpanish("prueba2");
		q2.setTipo(t);
		
		q3 = new Vocabulario();
		q3.setEnglish("test3");
		q3.setSpanish("prueba3");
		q3.setTipo(t);
	
		q4 = new Vocabulario();
		q4.setEnglish("test4");
		q4.setSpanish("prueba4");
		q4.setTipo(t);
		
		q5 = new Vocabulario();
		q5.setEnglish("test5");
		q5.setSpanish("prueba5");
		q5.setTipo(t);
		
		VocabularioDAOImpl vocManager = new VocabularioDAOImpl();
		
		vocManager.insertVocabulary(q1);
		vocManager.insertVocabulary(q2);
		vocManager.insertVocabulary(q3);
		vocManager.insertVocabulary(q4);
		vocManager.insertVocabulary(q5);
				
	}
	
	@Test
	public void AinsertTest() {
		
		test = testManager.insertTest(4, NUMPREGUNTAS);		
		assertTrue(test.getNumFallos() == -1);	
		
	}

	@Test
	public void Bupdate(){
		test.setNumFallos(2);
		assertTrue(testManager.update(test));
	}
	
	@Test
	public void CgetTestObject(){
		domain.Test c = testManager.getTestObject(test.getId());
		
		assertTrue(c.getId() == test.getId());
		
	}

	@Test
	public void DgetVocabularioTest(){
		Set<Vocabulario> vocs = testManager.getVocabularioTest(test.getId());
		
		assertTrue(vocs.size() == NUMPREGUNTAS);
	}
	
	@Test
	public void Edelete(){
		assertTrue(testManager.delete(test.getId()));
	}
	
	@AfterClass
	public static void tearDown(){
		VocabularioDAOImpl vocManager = new VocabularioDAOImpl();
		vocManager.delete(q1.getId());
		vocManager.delete(q2.getId());
		vocManager.delete(q3.getId());
		vocManager.delete(q4.getId());
		vocManager.delete(q5.getId());
	}

}
