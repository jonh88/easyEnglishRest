package daoTests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import dao.UsuarioDAOImpl;
import dao.VocabularioDAOImpl;
import domain.Usuario;
import domain.Vocabulario;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class testVocabulario {

	private static int idUser;
	private static int idVoc;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		UsuarioDAOImpl userImpl = new UsuarioDAOImpl();
		Usuario user = new Usuario("testName","testApe","testEmail", "testPass");
		testVocabulario.idUser = userImpl.addUser(user).getId();
		assertTrue(testVocabulario.idUser > 0);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		UsuarioDAOImpl userImpl = new UsuarioDAOImpl();
		boolean result = userImpl.delete(testVocabulario.idUser);
		assertTrue(result);
	}

	@Test
	public void AtestAddVocabulary() {
		Vocabulario voc = new Vocabulario();
		voc.setEnglish("english");
		voc.setSpanish("spanish");
		voc.setTipo(1);
		
		VocabularioDAOImpl vocImpl = new VocabularioDAOImpl();
		//testVocabulario.idVoc = vocImpl.addVocabulary(voc, testVocabulario.idUser);
		assertTrue(testVocabulario.idVoc > 0);
		
	}

	@Test
	public void BtestModify() {
		Vocabulario voc = new Vocabulario();
		voc.setEnglish("house");
		voc.setSpanish("casa");
		voc.setTipo(1);
		
		VocabularioDAOImpl vocImpl = new VocabularioDAOImpl();
		//boolean result = vocImpl.modify(voc, testVocabulario.idVoc);
		
		//assertTrue(result);
		
	}

	@Test
	public void CtestGetVocabulary() {
		VocabularioDAOImpl vocImpl = new VocabularioDAOImpl();
		//Vocabulario voc = vocImpl.getVocabulary(testVocabulario.idVoc);
		
		//assertTrue(voc != null);
		
	}

	@Test
	public void DtestGetVocabularies() {
		VocabularioDAOImpl vocImpl = new VocabularioDAOImpl();
		ArrayList<Vocabulario> lvoc = (ArrayList<Vocabulario>) vocImpl.getVocabularies(testVocabulario.idUser);
		
		assertTrue(lvoc.size()>0);
	}

	@Test
	public void EtestDelete() {
		VocabularioDAOImpl vocImpl = new VocabularioDAOImpl();
		//boolean result = vocImpl.delete(idVoc, idUser);
		//assertTrue(result);
	}

}
