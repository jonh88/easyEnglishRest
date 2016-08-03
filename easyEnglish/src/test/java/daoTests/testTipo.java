package daoTests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import dao.TipoDAOImpl;
import domain.Tipo;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class testTipo {

	private static int idTipo;
	
	@Test
	public void AtestAddTipo() {
		Tipo t = new Tipo("testing");
		
		TipoDAOImpl tipo = new TipoDAOImpl();
		Tipo inserted = tipo.addTipo(t);
		
		assertTrue (inserted != null);
	}

	@Test
	public void BtestModify() {
		Tipo t = new Tipo("testingModified");
		
		TipoDAOImpl tipo = new TipoDAOImpl();
		Tipo modified = tipo.modify(t);
		assertTrue(modified != null);
	}

	@Test
	public void CtestGetTipo() {
		TipoDAOImpl tipoManager = new TipoDAOImpl();
		Tipo t = tipoManager.getTipo(testTipo.idTipo);
		assertTrue(t != null);
	}

	@Test
	public void DtestGetTipos() {
		TipoDAOImpl tipo = new TipoDAOImpl();
		ArrayList<Tipo> l = (ArrayList<Tipo>) tipo.getTipos();
		assertTrue(l.size()>0);
	}

	@Test
	public void EtestDelete() {
		TipoDAOImpl tipo = new TipoDAOImpl();
		
		boolean result = tipo.delete(testTipo.idTipo);
		assertTrue(result);
	}

}
