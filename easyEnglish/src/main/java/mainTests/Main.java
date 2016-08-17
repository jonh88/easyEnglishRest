package mainTests;

import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;

import conn.ConnectionDB;
import conn.HibernateUtil;
import dao.AuthenticationImpl;
import dao.CuestionarioDAOImpl;
import dao.PreguntaDAOImpl;
import dao.TestDAOImpl;
import dao.TipoDAOImpl;
import dao.UsuarioDAOImpl;
import dao.VocabularioDAOImpl;
import daoTests.testUsuario;
import domain.*;
import util.FechaManager;

public class Main {
	private static Pregunta q1,q2,q3,q4,q5; 		
	private static Cuestionario cuestionario;
	private static UsuarioDAOImpl userManager = new UsuarioDAOImpl(); 
	private static final int NUMPREGUNTAS = 5;

	public static void main(String[] args) {
		TestDAOImpl test = new TestDAOImpl();
		
		//int id_user = userManager.findUserByEmail("usuarioPruebas@gmail.com").getId();
		System.out.println(test.delete(59));
	}
	
}
