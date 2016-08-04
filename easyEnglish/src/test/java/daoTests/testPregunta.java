package daoTests;

import static org.junit.Assert.*;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import dao.PreguntaDAOImpl;
import domain.Pregunta;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class testPregunta {
	private static Pregunta q;
	private static PreguntaDAOImpl preguntaManager;
	
	@BeforeClass
	public static void init(){
		q = new Pregunta();
		q.setPregunta("what is your name?");
		q.setRespA("Mary");
		q.setRespB("Jane");
		q.setRespC("Jonh");
		q.setRespD("Juan");
		q.setRespOK("C");
		
		preguntaManager = new PreguntaDAOImpl();
	}
	
	@Test
	public void AinsertPregunta(){
		q = preguntaManager.insertPregunta(q);
		assertTrue(q.getId()>0);
	}
	
	@Test
	public void BupdatePregunta(){
		q.setRespOK("A");
		assertTrue(preguntaManager.updatePregunta(q));
	}

	@Test
	public void CgetPregunta(){
		Pregunta res = preguntaManager.getPregunta(q.getId());
		assertTrue(res.getId() == q.getId());
	}
	
	@Test
	public void DdeletePregunta(){
		assertTrue(preguntaManager.delete(q.getId()));
	}
}
