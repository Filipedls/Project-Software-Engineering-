package dtu.projman.app;

import static org.junit.Assert.*;

import org.junit.Test;

/*
 * @author Filipe Silva
 */
public class TestRegisterEmployee {

	/** 
	 * Tests the scenario when an employee wants to register himself.
	 * <ol>
	 *  <li> The employee to be added is created.
	 *  <li> The Project Manager application check if that username is already in the employees list.
	 *  <li> The employees is added to the application by calling registerEmployee from the Project Manager application.
	 * </ol>
	 * @throws OperationNotAllowedException 
	 */
	@Test
	public void TestRegisterEmployeeToApp() throws OperationNotAllowedException {
		// Create the app
		ProjManApp app = new ProjManApp();
		
		// Create an employee
		String fullname = "Atakan Kaya";
		String username = "atkn";
		String email = "kayaatakan@gmail.com";
		Employee emp = new Employee(fullname, username, email);
		
		// Test if employee info is correct
		assertEquals(emp.getFullname(), fullname);
		assertEquals(emp.getUsername(), username);
		assertEquals(emp.getEmail(), email);
		
		// Assure that no employee is registered and register employee
		assertTrue(app.getEmployees().isEmpty());
		app.registerEmployee(emp);
		
		// Test if employee is registered
		assertEquals(app.getEmployeeByUsername(username), emp);
		assertEquals(1,app.getEmployees().size());
	}
	
	/** 
	 * Tests the scenario when an employee wants to register himself, but the username is already in the application's employees list.
	 * <ol>
	 *  <li> The employee to be added is created.
	 *  <li> The Project Manager application check if that username is already in the employees list.
	 *  <li> The Project Manager application throws an exception.
	 * </ol>
	 * @throws OperationNotAllowedException 
	 */
	@Test
	public void TestRegisterEmployeeToAppUsernameExists() throws OperationNotAllowedException {
		// Create the app
		ProjManApp app = new ProjManApp();
		
		// Create an employee
		String fullname1 = "Atakan Kaya";
		String username1 = "atkn";
		String email1 = "kayaatakan@gmail.com";
		Employee emp1 = new Employee(fullname1, username1, email1);
		
		// Register employee
		assertTrue(app.getEmployees().isEmpty());
		app.registerEmployee(emp1);

		// Test if employee is registered
		assertEquals(app.getEmployeeByUsername(username1), emp1);
		assertEquals(1,app.getEmployees().size());

		// Create another employee with the same username
		String fullname2 = "Deniz Kaya";
		String username2 = "atkn";
		String email2 = "denizbehrem@gmail.com";
		Employee emp2 = new Employee(fullname2, username2, email2);		
		
		// Test registering of the last employee. It should not be allowed.
		try {
			app.registerEmployee(emp2);

			fail("OperationNotAllowedException exception should have been thrown; username already exists");
		} catch (OperationNotAllowedException e) {
			// Check that the exception thrown has the correct error message and knows which operation failed.
			assertEquals("Register employee operation is not allowed since username already exists",e.getMessage());
		}
		
		// Check that the employee has not been registered to the app.		
		assertEquals(0,app.getEmployeesByFullname(fullname2).size());
		assertEquals(1,app.getEmployees().size());
	}

	/** 
	 * Tests the scenario when an employee wants to register himself with an empty string.
	 * <ol>
	 *  <li> The employee to be added is created.
	 *  <li> The Project Manager application check if that username is not an empty string.
	 *  <li> The Project Manager application throws an exception.
	 * </ol>
	 * @throws OperationNotAllowedException 
	 */
	@Test
	public void TestRegisterEmployeeEmptyUsername() throws OperationNotAllowedException {
		// Create the app
		ProjManApp app = new ProjManApp();

		// Assure that no employee is registered in the system
		assertTrue(app.getEmployees().isEmpty());
		
		// Test registering of the last employee. It should not be allowed.
		try {
			// Create an employee
			String fullname = "Filipe Silva";
			String username = "";
			String email = "filipe.dls@gmail.com";
			Employee emp = new Employee(fullname, username, email);

			app.registerEmployee(emp);
			fail("OperationNotAllowedException exception should have been thrown; username too long");
		} catch (OperationNotAllowedException e) {
			// Check that the exception thrown has the correct error message and knows which operation failed.
			assertEquals(Error.USERNAME_ERROR, e.getError());
		}
				
		// Test if employee is not registered
		assertTrue(app.getEmployees().isEmpty());
	}
	
	/** 
	 * Tests the scenario when an employee wants to register himself with more than 4 characters.
	 * <ol>
	 *  <li> The employee to be added is created.
	 *  <li> The Project Manager application check if that username is bigger than 4 characters.
	 *  <li> The Project Manager application throws an exception.
	 * </ol>
	 * @throws OperationNotAllowedException 
	 */
	@Test
	public void TestRegisterEmployeeMoreThan4Chars() throws OperationNotAllowedException {
		// Create the app
		ProjManApp app = new ProjManApp();

		// Assure that no employee is registered in the system
		assertTrue(app.getEmployees().isEmpty());
		
		// Test registering of the last employee. It should not be allowed.
		try {
			// Create an employee
			String fullname = "Filipe Silva";
			String username = "filpe";
			String email = "filipe.dls@gmail.com";
			Employee emp = new Employee(fullname, username, email);

			app.registerEmployee(emp);
			fail("OperationNotAllowedException exception should have been thrown; username too long");
		} catch (OperationNotAllowedException e) {
			// Check that the exception thrown has the correct error message and knows which operation failed.
			assertEquals(Error.USERNAME_ERROR, e.getError());
		}
				
		// Test if employee is not registered
		assertTrue(app.getEmployees().isEmpty());
	}

}
