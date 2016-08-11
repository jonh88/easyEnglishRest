package daoTests;

import static org.junit.Assert.*;
import java.util.List;
import java.util.Set;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import dao.TipoDAOImpl;
import dao.UsuarioDAOImpl;
import dao.VocabularioDAOImpl;
import domain.Cuestionario;
import domain.Tipo;
import domain.Usuario;
import domain.Vocabulario;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class testUsuario {
	
	private UsuarioDAOImpl userManager = new UsuarioDAOImpl();
	private static Vocabulario voc;

	@Test
	public void AtestAddUser() {
				
		Usuario user = new Usuario("testName","testApe","testEmail", "testPass");		
		assertTrue(userManager.addUser(user).getId() > 0);					
	
	}

	@Test
	public void BtestModify() {		
		Usuario userModified = userManager.findUserByEmail("testEmail");
		userModified.setEmail("email@changed.com");
		boolean result = userManager.updateUser(userModified);
		assertTrue(result);
	}

	@Test
	public void CtestFindUserByEmail() {
		Usuario user = userManager.findUserByEmail("email@changed.com");		
		assertTrue (user != null);	
	}

	@Test
	public void DtestFindUserById() {
		Usuario user = userManager.findUserByEmail("email@changed.com");	
		Usuario userFound = userManager.findUserById(user.getId());		
		assertTrue (userFound.getId()>0);
	}

	@Test
	public void EtestGetUsers() {					
		List users = userManager.getUsers();
		
		assertTrue (!users.isEmpty());
	}

	@Test
	public void FtestGetTests() {					
		Set<domain.Test> tests = userManager.getTestUser(4);		
		assertTrue (tests.size()>0);
	}
	
	@Test
	public void GtestGetVocabularios() {					
		Set<Vocabulario> vocs = userManager.getVocabularios(4);		
		assertTrue (vocs.size()>0);
	}
	
	@Test
	public void HtestGetCuestionarios() {	
		//id = 1 --> usuario de tests
		Set<Cuestionario> cuestionarios = userManager.getCuestionarios(1);		
		assertTrue (cuestionarios.size()>0);
	}
	
	@Test
	public void IinsertVocabulario() {
		TipoDAOImpl tipoManager = new TipoDAOImpl();
		Tipo t = tipoManager.getTipo(4);
		
		int idUser = 4;
		voc = new Vocabulario();
		voc.setEnglish("test");
		voc.setSpanish("prueba");
		voc.setTipo(t);
		
		VocabularioDAOImpl vocManager = new VocabularioDAOImpl();
		vocManager.insertVocabulary(voc);
		
		boolean success = userManager.insertVocabulario(idUser, voc);
		
		//vocManager.delete(voc.getId());
		
		assertTrue(success);				
	}	
	
	@Test
	public void JdeleteVocabulario() {
				
		boolean success = userManager.deleteVocabulario(4, voc.getId());
		
		VocabularioDAOImpl vocManager = new VocabularioDAOImpl();
		vocManager.delete(voc.getId());
		
		assertTrue(success);				
	}
	
	@Test
	public void KtestDelete() {
		Usuario user = userManager.findUserByEmail("email@changed.com");
		assertTrue(userManager.delete(user.getId()));
		assertTrue(true);
	}

}
