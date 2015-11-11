package dtu.projman.app;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/*
 * @author Marc Thomsen
 */
public class TestSysSystemLogin {

	ProjManApp app;
	
	@Before
	public void setUp() throws Exception {
		app = new ProjManApp();
		
		Employee employee1 = new Employee("Marc Thomsen", "mama", "marcmthomsen@gmail.com");
		Employee employee2 = new Employee("Atakan Kaya", "kaat", "kayaatakan@gmail.com");
		app.registerEmployee(employee1);
		app.registerEmployee(employee2);
	}

	@Test
	public void testInputDatasetA1() {
		String username = null;
		assertNull(app.getEmployeeLoggedIn());

		try {
			app.employeeLogin(username);
			fail("USERNAME_ERROR should have been thrown.");
		} catch (OperationNotAllowedException e) {
			assertEquals(Error.USERNAME_ERROR, e.getError());
		}

	}	

	@Test
	public void testInputDatasetA2() {
		String username = "";
		assertNull(app.getEmployeeLoggedIn());

		try {
			app.employeeLogin(username);
			fail("USERNAME_ERROR should have been thrown.");
		} catch (OperationNotAllowedException e) {
			assertEquals(Error.USERNAME_ERROR, e.getError());
		}

	}

	@Test
	public void testInputDatasetB1() throws OperationNotAllowedException {
		String username = null;
		
		app.employeeLogin("kaat");
		assertNotNull(app.getEmployeeLoggedIn());
		
		try {
			app.employeeLogin(username);
			fail("AN_EMPLOYEE_ALREADY_LOGGED_IN should have been thrown.");
		} catch (OperationNotAllowedException e) {
			assertEquals(Error.AN_EMPLOYEE_ALREADY_LOGGED_IN, e.getError());
		}

	}		
	
	@Test
	public void testInputDatasetB2() throws OperationNotAllowedException {
		String username = "";
		
		app.employeeLogin("kaat");
		assertNotNull(app.getEmployeeLoggedIn());
		
		try {
			app.employeeLogin(username);
			fail("AN_EMPLOYEE_ALREADY_LOGGED_IN should have been thrown.");
		} catch (OperationNotAllowedException e) {
			assertEquals(Error.AN_EMPLOYEE_ALREADY_LOGGED_IN, e.getError());
		}

	}

	@Test
	public void testInputDatasetC() throws OperationNotAllowedException {
		String username = "mama";
		
		app.employeeLogin("kaat");
		assertNotNull(app.getEmployeeLoggedIn());

		try {
			app.employeeLogin(username);
			fail("AN_EMPLOYEE_ALREADY_LOGGED_IN should have been thrown.");
		} catch (OperationNotAllowedException e) {
			assertEquals(Error.AN_EMPLOYEE_ALREADY_LOGGED_IN, e.getError());
		}

	}	
	
	@Test
	public void testInputDatasetD() throws OperationNotAllowedException {
		String username = "mama";
		assertNull(app.getEmployeeLoggedIn());
		
		Employee employee = app.employeeLogin(username);	
		assertEquals(username, employee.getUsername());
		
	}	
	
}
