package mainTests;

import org.hibernate.Session;

import conn.HibernateUtil;
import dao.AuthenticationImpl;
import dao.UsuarioDAOImpl;
import daoTests.testUsuario;
import domain.Usuario;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		UsuarioDAOImpl userManager = new UsuarioDAOImpl();
		
		Usuario user = new Usuario();
		user.setName("testName");
		user.setApellidos("testApe");
		user.setEmail("testEmail");
		user.setPwd("12345");
		
		Usuario a = userManager.addUser(user);
		
		AuthenticationImpl au = new AuthenticationImpl();
		
		int valido = au.checkUser(a.getEmail(), "12345");
		
		System.out.println(valido);
					
	}

}
