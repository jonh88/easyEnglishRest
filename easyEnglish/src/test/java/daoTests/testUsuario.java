package daoTests;

import static org.junit.Assert.*;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import dao.UsuarioDAOImpl;
import domain.Usuario;


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
	public void FtestGetTestUser() {					
		List<domain.Test> tests = userManager.getTestUser(4);		
		assertTrue (tests.size()==2);
	}
	
	@Test
	public void GtestDelete() {
		Usuario user = userManager.findUserByEmail("email@changed.com");
		assertTrue(userManager.delete(user.getId()));
		assertTrue(true);
	}

}
