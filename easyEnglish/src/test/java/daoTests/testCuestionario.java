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
import dao.UsuarioDAOImpl;
import domain.Cuestionario;
import domain.Pregunta;
import domain.Usuario;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class testCuestionario {
	private static Pregunta q1,q2,q3,q4,q5; 	
	private static CuestionarioDAOImpl cuestionarioManager;
	private static Cuestionario cuestionario;
	private static UsuarioDAOImpl userManager = new UsuarioDAOImpl(); 
	private static final int NUMPREGUNTAS = 3;
	
	
	@BeforeClass
	public static void setUp(){
		q1 = new Pregunta();
		q1.setPregunta("test question 1");
		q1.setRespA("a");
		q1.setRespB("b");
		q1.setRespC("c");
		q1.setRespD("d");
		q1.setRespOK("a");
		
		q2 = new Pregunta();
		q2.setPregunta("test question 2");
		q2.setRespA("a");
		q2.setRespB("b");
		q2.setRespC("c");
		q2.setRespD("d");
		q2.setRespOK("a");
		
		q3 = new Pregunta();
		q3.setPregunta("test question 3");
		q3.setRespA("a");
		q3.setRespB("b");
		q3.setRespC("c");
		q3.setRespD("d");
		q3.setRespOK("a");
	
		q4 = new Pregunta();
		q4.setPregunta("test question 4");
		q4.setRespA("a");
		q4.setRespB("b");
		q4.setRespC("c");
		q4.setRespD("d");
		q4.setRespOK("a");
		
		q5 = new Pregunta();
		q5.setPregunta("test question 5");
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
	public void AAinsertCuestionarioPreguntas(){
		Usuario testUser = userManager.findUserByEmail("prueba2@gmail.com");
		Session session = HibernateUtil.getSessionFactory().openSession();		
		session.beginTransaction();
		
		cuestionario = new Cuestionario(testUser,20160101,3,-1);
		Set<Pregunta> preguntas = new HashSet<Pregunta>();
		preguntas.add(q1);
		preguntas.add(q2);
		preguntas.add(q3);		
		cuestionario.setPreguntas(preguntas);
		
		session.save(cuestionario);		
		session.getTransaction().commit();
		
		//comprobar que en tbl_cuestionario_pregunta se han insertado los 3 registros
		String select = "select count(*) from tbl_cuestionario_pregunta where tbl_cuestionario_id = ?";
        List<Test> aTest = new ArrayList<Test>();
        
        int n = 0;
        try{
        	ConnectionDB cn = new ConnectionDB();
            Connection conex = cn.getConn();            
            PreparedStatement ps = conex.prepareStatement(select);
            ps.setInt(1, cuestionario.getId());
            ResultSet res = ps.executeQuery();
            
            while(res.next()){                                
            	n = res.getInt(1);                
            }                                           
            
        }catch (Exception e){
            assertTrue(false);
        }
		
        //borrar datos
        cuestionarioManager.delete(cuestionario.getId());
        
        assertTrue(n==3);
			
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