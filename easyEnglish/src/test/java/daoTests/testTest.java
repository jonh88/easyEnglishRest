package daoTests;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import dao.TestDAOImpl;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class testTest {

	
	@Test
	public void AtestAddTest() {
		//en la bbdd user 4 -> 10, user 2 -> 2, user 1 ->1
		TestDAOImpl test = new TestDAOImpl();
		int result = test.addTest(4, 5);
		
		if (result < 0)
			assertTrue(false);
		else
			assertTrue(true);
		
	}

	@Test
	public void BtestGetTests() {
		TestDAOImpl test = new TestDAOImpl();
		assertTrue (test.getTest(4).size()>0);
		
	}
	
	@Test
	public void CtestGetTestObject() {
		TestDAOImpl test = new TestDAOImpl();
		domain.Test t = test.getTestObject(47);
		assertTrue (t.getIdUsr() == 4);
		
	}

	@Test
	public void DtestDelete() {
		TestDAOImpl test = new TestDAOImpl();
		boolean result = test.delete(4);
		assertTrue(result);
	}

}
