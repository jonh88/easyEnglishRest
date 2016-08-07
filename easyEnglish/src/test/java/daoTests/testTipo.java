package daoTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import dao.TipoDAOImpl;
import domain.Tipo;
import domain.Vocabulario;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class testTipo {

	private static Tipo type;
	private static TipoDAOImpl tipoManager;
	
	@BeforeClass
	public static void init(){
		tipoManager = new TipoDAOImpl();
	}
	
	@Test
	public void AtestAddTipo() {
		type = new Tipo("testing");		
		tipoManager.addTipo(type);				
		assertTrue (type.getId()>0);
	}

	@Test
	public void BtestModify() {
		type.setType("tipoModificado");				
		assertTrue(tipoManager.modify(type));
	}

	@Test
	public void CtestGetTipo() {
		Tipo t = tipoManager.getTipo(type.getId());
		assertTrue(t.getId() == type.getId());
	}

	@Test
	public void DtestGetTipos() {
		List<Tipo> tipos = tipoManager.getTipos();	
		assertTrue(tipos.size()>0);
	}		

	@Test
	public void EtestDelete() {		
		assertTrue(tipoManager.delete(type.getId()));
	}
	
	@Test
	public void FtestGetVocabulariosTipo (){
		Set<Vocabulario> vocs = tipoManager.getVocabulariosTipo(4);
		assertTrue(vocs.size()>0);
	}

}
