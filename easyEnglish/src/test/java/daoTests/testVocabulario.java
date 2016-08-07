package daoTests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import dao.TipoDAOImpl;
import dao.UsuarioDAOImpl;
import dao.VocabularioDAOImpl;
import domain.Tipo;
import domain.Usuario;
import domain.Vocabulario;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class testVocabulario {

	private static VocabularioDAOImpl vocManager;
	private static Vocabulario voc;
	private static final int IDTIPO = 4;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		vocManager = new VocabularioDAOImpl();
		voc = new Vocabulario();
		voc.setEnglish("test");
		voc.setSpanish("prueba");
		
		TipoDAOImpl tipoManager = new TipoDAOImpl();
		Tipo t = tipoManager.getTipo(IDTIPO);
		
		voc.setTipo(t);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		
	}

	@Test
	public void AinsertVocabulary() {
		vocManager.insertVocabulary(voc);
		assertTrue(voc.getId()>0);		
	}

	@Test
	public void BupdateVocabulario() {
		voc.setEnglish("testUpdated");
		assertTrue(vocManager.updateVocabulario(voc));		
	}

	@Test
	public void CfindVocabularyById() {
		Vocabulario aux = vocManager.findVocabularyById(voc.getId());
		assertTrue(aux.getId() == voc.getId());
		
	}

	@Test
	public void DgetAll() {				
		assertTrue(vocManager.getAll().size()>0);
	}

	@Test
	public void Edelete() {
		assertTrue(vocManager.delete(voc.getId()));
	}

}
