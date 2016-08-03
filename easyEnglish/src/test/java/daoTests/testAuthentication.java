package daoTests;

import static org.junit.Assert.*;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import dao.AuthenticationImpl;
import domain.Token;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class testAuthentication {

	@Test
	public void AtestCheckUser() {
		AuthenticationImpl authz = new AuthenticationImpl();
	//	int idUser = authz.checkUser("jonhy_mrtlz@hotmail.com", "atras");
		
		//assertTrue(idUser == 4);
	}

	@Test
	public void DtestCheckToken() {
		AuthenticationImpl authz = new AuthenticationImpl();
		//comprueba tb el update del token
		Token t = new Token(4);
		/*if (authz.setToken(t.getToken(), "jonhy_mrtlz@hotmail.com")){
			//ha echo bien el update
			boolean result = authz.checkToken(t.getToken(), 4);
			assertTrue(result);
		}else{
			assertTrue(false);
		}*/
					
	}

	@Test
	public void BtestSetTokenFirstTime() {
		Token t = new Token(4);
		AuthenticationImpl authz = new AuthenticationImpl();		
		//boolean result = authz.setToken(t.getToken(), "jonhy_mrtlz@hotmail.com");
		//assertTrue (result);
	}
	
	@Test
	public void CtestSetTokenUpdate() {
		Token t = new Token(4);
		AuthenticationImpl authz = new AuthenticationImpl();
		//boolean result = authz.setToken(t.getToken(), "jonhy_mrtlz@hotmail.com");
		//assertTrue (result);
	}

	@Test
	public void AAtestTokenExist() {
		AuthenticationImpl authz = new AuthenticationImpl();
		//boolean result = authz.tokenExist(4);
		//assertTrue (result == false);
	}
}
