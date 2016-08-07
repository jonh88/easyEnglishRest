package daoTests;

import static org.junit.Assert.*;
import java.util.List;
import java.util.Set;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import dao.UsuarioDAOImpl;
import domain.Cuestionario;
import domain.Usuario;
import domain.Vocabulario;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class testUsuario {
	
	private UsuarioDAOImpl userManager = new UsuarioDAOImpl();

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
	public void FtestGetVocabularios() {					
		Set<Vocabulario> vocs = userManager.getVocabularios(4);		
		assertTrue (vocs.size()>0);
	}
	
	@Test
	public void FtestGetCuestionarios() {	
		//id = 1 --> usuario de tests
		Set<Cuestionario> cuestionarios = userManager.getCuestionarios(1);		
		assertTrue (cuestionarios.size()>0);
	}
	
	@Test
	public void GtestDelete() {
		Usuario user = userManager.findUserByEmail("email@changed.com");
		assertTrue(userManager.delete(user.getId()));
		assertTrue(true);
	}

}
