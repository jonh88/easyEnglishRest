package daoTests;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

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
	public void BgetCount(){
		long n = preguntaManager.getCount();
		assertTrue(n == 6L);
	}
	
	@Test
	public void CupdatePregunta(){
		q.setRespOK("A");
		assertTrue(preguntaManager.updatePregunta(q));
	}

	@Test
	public void DgetPregunta(){
		Pregunta res = preguntaManager.getPregunta(q.getId());
		assertTrue(res.getId() == q.getId());
	}
	
	@Test
	public void EdeletePregunta(){
		assertTrue(preguntaManager.delete(q.getId()));
	}
	@Test
	public void FgetAll(){		
		Pregunta p = new Pregunta();
		p.setPregunta("what is your name?");
		p.setRespA("Mary");
		p.setRespB("Jane");
		p.setRespC("Jonh");
		p.setRespD("Juan");
		p.setRespOK("C");
		int a = preguntaManager.insertPregunta(p).getId();
		int b = preguntaManager.insertPregunta(p).getId();
		
		List<Pregunta> preguntas = preguntaManager.getAll();
		
		preguntaManager.delete(a);
		preguntaManager.delete(b);
		
		assertTrue(preguntas.size() == 7);
				
	}
}
