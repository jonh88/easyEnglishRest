package daoTests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import dao.AuthenticationImpl;
import dao.UsuarioDAOImpl;
import domain.Usuario;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class testAuthentication {
	private static Usuario user;
	private static final int USERID = 4;
	private static final String PASS = "P@ssw0rd";
	private static String token;
	private static AuthenticationImpl authz;
	
	@BeforeClass
	public static void init(){
		authz = new AuthenticationImpl();
		UsuarioDAOImpl userManager = new UsuarioDAOImpl();
		user = userManager.findUserById(USERID);
	}

	@Test
	public void AtestGetToken() {		
		token = authz.getToken(user.getEmail(), PASS);
		assertTrue(token != null);				
	}

	@Test
	public void BvalidaToken() {
		assertTrue(authz.validaToken(token, USERID) == 1);					
	}
	
}
