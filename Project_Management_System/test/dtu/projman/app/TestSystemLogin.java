package dtu.projman.app;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/*
 * @author Marc Thomsen
 */
public class TestSystemLogin {
	
	ProjManApp app = new ProjManApp();
	Employee emp1, emp2;
	
	@Before
	public void setUp() throws Exception {
		// Create two employees
		String fullname1 = "Atakan Kaya", 
				fullname2 = "Filipe Silva";
		String username1 = "kaat",
				username2 = "flsi";
		String email1 = "kayaatakan@gmail.com",
				email2 = "filipesilva@gmail.com";
		emp1 = new Employee(fullname1, username1, email1);
		emp2 = new Employee(fullname2, username2, email2);
		
		// Register employees
		app.registerEmployee(emp1);
		app.registerEmployee(emp2);
	}

	@Test
	public void testLogin() throws OperationNotAllowedException { // main scenario
		// First check that no employee is logged in.
		assertNull(app.getEmployeeLoggedIn());
		
		// Login
		Employee empLoggedIn = app.employeeLogin("kaat");
		
		// Check that the method returned the employee and check that employee is logged in.
		assertNotNull(empLoggedIn);
		assertEquals("kaat", empLoggedIn.getUsername());
		assertEquals(emp1, app.getEmployeeLoggedIn());
	}

	@Test
	public void testLoginWrongUsername() { // alternative scenario
		// First check that no employee is logged in (precondition).
		assertNull(app.getEmployeeLoggedIn());
		
		Employee empLoggedIn = null; 
		// Login with wrong username (Employee does not exist)
		try {
			empLoggedIn = app.employeeLogin("no employee");
		} catch (OperationNotAllowedException e) {
			assertEquals(Error.USERNAME_ERROR, e.getError());
		}
		
		// Check that the method returned null and check that no employee is logged in.
		assertNull(empLoggedIn);
		assertNull(app.getEmployeeLoggedIn());
	}

	@Test
	public void testLoginAlreadyLoggedIn() throws OperationNotAllowedException { // alternative scenario
		// Check first that no employee is not logged in.
		assertNull(app.getEmployeeLoggedIn());
		
		// Login emp1
		app.employeeLogin("kaat");
		
		// Assure emp1 logged in 
		assertEquals(emp1, app.getEmployeeLoggedIn());
		
		// Login emp2 without logging out emp1
		try {
			app.employeeLogin("flsi");
			fail("Assertion failed: System login error: First logout. Exception should have been thrown. ");
		}
		catch(OperationNotAllowedException e) {
			assertEquals(Error.AN_EMPLOYEE_ALREADY_LOGGED_IN, e.getError());
		}
		
		// Check that emp2 is not logged in and emp1 is still logged in.
		assertEquals(emp1, app.getEmployeeLoggedIn());
		assertFalse(emp2.equals(app.getEmployeeLoggedIn()));
	}
	
}
