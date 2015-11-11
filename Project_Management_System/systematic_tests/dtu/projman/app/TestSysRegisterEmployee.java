package dtu.projman.app;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/*
 * @author Marianne Louis-Hansen
 */
public class TestSysRegisterEmployee {

	ProjManApp app;
	
	@Before
	public void setUp() throws Exception {
		app = new ProjManApp();
	}

	@Test
	public void testDatasetA1() {
		String fullname = "Jane Doe", 
				username = null,
				email = "jane@doe.com";
		
		Employee emp;
		try {
			emp = new Employee(fullname, username, email);
			app.registerEmployee(emp);
			fail("USERNAME_ERROR should have been thrown.");
		} catch (OperationNotAllowedException e) {
			assertEquals(Error.USERNAME_ERROR, e.getError());
		}
		
	}

	@Test
	public void testDatasetA2() {
		String fullname = "Jane Doe", 
				username = "",
				email = "jane@doe.com";
		
		Employee emp;
		try {
			emp = new Employee(fullname, username, email);
			app.registerEmployee(emp);
			fail("USERNAME_ERROR should have been thrown.");
		} catch (OperationNotAllowedException e) {
			assertEquals(Error.USERNAME_ERROR, e.getError());
		}
	}
	
	@Test
	public void testDatasetA3() {
		String fullname = "Jane Doe", 
				username = "janedoe",
				email = "jane@doe.com";
		
		Employee emp;
		try {
			emp = new Employee(fullname, username, email);
			app.registerEmployee(emp);
			fail("USERNAME_ERROR should have been thrown.");
		} catch (OperationNotAllowedException e) {
			assertEquals(Error.USERNAME_ERROR, e.getError());
		}
	}
	@Test
	public void testDatasetB1() {
		String fullname = null, 
				username = "jdoe",
				email = "jane@doe.com";
		
		Employee emp;
		try {
			emp = new Employee(fullname, username, email);
			app.registerEmployee(emp);
			fail("EMPTY_FIELD should have been thrown.");
		} catch (OperationNotAllowedException e) {
			assertEquals(Error.EMPTY_FIELD, e.getError());
		}
	}
	
	@Test
	public void testDatasetB2() {
		String fullname = "", 
				username = "jdoe",
				email = "jane@doe.com";
		
		Employee emp;
		try {
			emp = new Employee(fullname, username, email);
			app.registerEmployee(emp);
			fail("EMPTY_FIELD should have been thrown.");
		} catch (OperationNotAllowedException e) {
			assertEquals(Error.EMPTY_FIELD, e.getError());
		}
	}
	
	@Test
	public void testDatasetC1() {
		String fullname = "Jane Doe", 
				username = "jdoe",
				email = null;
		
		Employee emp;
		try {
			emp = new Employee(fullname, username, email);
			app.registerEmployee(emp);
			fail("EMPTY_FIELD should have been thrown.");
		} catch (OperationNotAllowedException e) {
			assertEquals(Error.EMPTY_FIELD, e.getError());
		}
	}
	
	@Test
	public void testDatasetC2() {
		String fullname = "Jane Doe", 
				username = "jdoe",
				email = "";
		
		Employee emp;
		try {
			emp = new Employee(fullname, username, email);
			app.registerEmployee(emp);
			fail("EMPTY_FIELD should have been thrown.");
		} catch (OperationNotAllowedException e) {
			assertEquals(Error.EMPTY_FIELD, e.getError());
		}
	}
	
	@Test
	public void testDatasetD() throws OperationNotAllowedException {
		String fullname = "Jane Doe", 
				username = "jdoe",
				email = "jane@doe.com";
	
		Employee emp = new Employee(fullname, username, email);
		app.registerEmployee(emp);
		Employee registeredEmp = app.getEmployeeByUsername(username);
		assertEquals(emp, registeredEmp);
		assertEquals(fullname, registeredEmp.getFullname());
		assertEquals(username, registeredEmp.getUsername());
		assertEquals(email, registeredEmp.getEmail());
	}
	
	@Test
	public void testDatasetE1() throws OperationNotAllowedException {
		String fullname = "", 
				username = "jdoe",
				email = "jane@doe.com";
		Employee preRegisteredEmp = new Employee("Atakan Kaya", "jdoe", "kayaatakan@gmail.com");
		app.registerEmployee(preRegisteredEmp);
		
		Employee emp;
		try {
			emp = new Employee(fullname, username, email);
			app.registerEmployee(emp);
			fail("EMPTY_FIELD should have been thrown.");
		} catch (OperationNotAllowedException e) {
			assertEquals(Error.EMPTY_FIELD, e.getError());
		}
	}
	
	@Test
	public void testDatasetE2() throws OperationNotAllowedException {
		String fullname = "Jane Doe", 
				username = "jdoe",
				email = "";
		Employee preRegisteredEmp = new Employee("Atakan Kaya", "jdoe", "kayaatakan@gmail.com");
		app.registerEmployee(preRegisteredEmp);
		
		Employee emp;
		try {
			emp = new Employee(fullname, username, email);
			app.registerEmployee(emp);
			fail("EMPTY_FIELD should have been thrown.");
		} catch (OperationNotAllowedException e) {
			assertEquals(Error.EMPTY_FIELD, e.getError());
		}
	}
	
	@Test
	public void testDatasetE3() throws OperationNotAllowedException {
		String fullname = "Jane Doe", 
				username = "jdoe",
				email = "jane@doe.com";
		Employee preRegisteredEmp = new Employee("Atakan Kaya", "jdoe", "kayaatakan@gmail.com");
		app.registerEmployee(preRegisteredEmp);
		
		Employee emp;
		try {
			emp = new Employee(fullname, username, email);
			app.registerEmployee(emp);
			fail("USERNAME_EXISTS should have been thrown.");
		} catch (OperationNotAllowedException e) {
			assertEquals(Error.USERNAME_EXISTS, e.getError());
		}
	}
	
}
