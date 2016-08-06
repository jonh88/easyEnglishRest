package daoTests;

import static org.junit.Assert.*;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import dao.CuestionarioDAOImpl;
import dao.PreguntaDAOImpl;
import dao.UsuarioDAOImpl;
import domain.Cuestionario;
import domain.Pregunta;
import domain.Usuario;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class testCuestionario {
	private static Pregunta q1,q2,q3,q4,q5; 	
	private static CuestionarioDAOImpl cuestionarioManager;
	private static Cuestionario cuestionario;
	private static UsuarioDAOImpl userManager; 
	private static final int NUMPREGUNTAS = 3;
	
	
	@BeforeClass
	public static void setUp(){
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
		
		cuestionarioManager = new CuestionarioDAOImpl();
	}
	
	@Test
	public void AinsertCuestionario(){
		Usuario testUser = userManager.findUserByEmail("prueba2@gmail.com");
		cuestionario = cuestionarioManager.insertCuestionario(testUser.getId(), NUMPREGUNTAS);
		
		assertTrue(cuestionario != null);				
	}
	
	@Test
	public void Bupdate(){
		cuestionario.setNumFallos(2);
		assertTrue(cuestionarioManager.update(cuestionario));
	}
	
	@Test
	public void CgetCuestionarioObject(){
		Cuestionario c = cuestionarioManager.getCuestionarioObject(cuestionario.getId());
		
		assertTrue(c.getId() == cuestionario.getId());
		
	}

	@Test
	public void DgetPreguntasCuestionario(){
		Set<Pregunta> preguntas = cuestionarioManager.getPreguntasCuestionario(cuestionario.getId());
		
		assertTrue(preguntas.size() == NUMPREGUNTAS);
	}
	
	@Test
	public void Edelete(){
		assertTrue(cuestionarioManager.delete(cuestionario.getId()));
	}	
	
	@AfterClass
	public static void tearDown(){
		PreguntaDAOImpl preguntaManager = new PreguntaDAOImpl();
		preguntaManager.delete(q1.getId());
		preguntaManager.delete(q2.getId());
		preguntaManager.delete(q3.getId());
		preguntaManager.delete(q4.getId());
		preguntaManager.delete(q5.getId());
	}
}