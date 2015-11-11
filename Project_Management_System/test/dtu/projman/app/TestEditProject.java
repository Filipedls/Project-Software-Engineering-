package dtu.projman.app;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

/*
 * @author Atakan Kaya
 */
public class TestEditProject {
	
	ProjManApp app = new ProjManApp();
	Employee emp1, emp2, emp3;
	Project project;
	
	@Before
	public void setUp() throws Exception {
		// Create three employees
		String fullname1 = "Atakan Kaya", 
				fullname2 = "Filipe Silva",
				fullname3 = "Marc Thomsen";
		String username1 = "kaat",
				username2 = "filp", 
				username3 = "marc";
		String email1 = "kayaatakan@gmail.com",
				email2 = "filipesilva@gmail.com",
				email3 = "marcthomsen@gmail.com";
		
		emp1 = new Employee(fullname1, username1, email1);
		emp2 = new Employee(fullname2, username2, email2);
		emp3 = new Employee(fullname3, username3, email3);
		
		// Register the first two employees
		app.registerEmployee(emp1);
		app.registerEmployee(emp2);

		// Login an employee
		app.employeeLogin("filp");
		
		// Create a project
		String name = "Project 1 - Test";
		ProjectType type = ProjectType.EXTERNAL;
		String companyName = "Microsoft";
		
		project = app.createProject(name, type, companyName);
		
		app.employeeLogoff();
	}

	@Test
	public void testEditProject() throws OperationNotAllowedException {	
		// Check that nobody is logged in
		assertNull(app.getEmployeeLoggedIn());
		
		// Login an employee
		app.employeeLogin("filp");
		assertEquals(emp2,app.getEmployeeLoggedIn());
		
		// Parameters Set 
		String newName = "New Project";
		ProjectType newType = ProjectType.INTERNAL;
		String newCustomerName = "Software House A/S";
		String newDescription = "This is a new test project.";
		Calendar newStartDate = new GregorianCalendar(2013, Calendar.APRIL, 15);
		Calendar newEndDate = new GregorianCalendar(2013, Calendar.APRIL, 19);
		
		// Edit Project
		project.setName(newName);
		project.setType(newType);
		project.setCustomerName(newCustomerName);
		project.setDescription(newDescription);
		project.setStartDate(newStartDate);
		project.setEndDate(newEndDate);
		project.setManager(emp1);
		
		// Test if data has been saved
		Project projectInApp = app.getProjectById(project.getId());
		assertEquals(newName, projectInApp.getName());
		assertEquals(newType, projectInApp.getType());
		assertEquals(newCustomerName, projectInApp.getCustomerName());
		assertEquals(newDescription, projectInApp.getDescription());
		assertEquals(newStartDate, projectInApp.getStartDate());
		assertEquals(newEndDate, projectInApp.getEndDate());
		assertEquals(emp1, projectInApp.getManager());
		
	}
	
	@Test
	public void testEditProjectNotLoggedIn() throws OperationNotAllowedException {	
		// Check that nobody is logged in
		assertNull(app.getEmployeeLoggedIn());
		
		// Parameters set
		String newName = "New Project";
		ProjectType newType = ProjectType.INTERNAL;
		String newCustomerName = "Software House A/S";
		String newDescription = "This is a new test project.";
		Calendar newStartDate = new GregorianCalendar(2013, Calendar.APRIL, 15);
		Calendar newEndDate = new GregorianCalendar(2013, Calendar.APRIL, 19);
		
		// Old values
		String oldName = project.getName();
		ProjectType oldType = project.getType();
		String oldCustomerName = project.getCustomerName();
		String oldDescription = project.getDescription();
		Calendar oldStartDate = project.getStartDate();
		Calendar oldEndDate = project.getEndDate();
		
		// Edit Project
		try {
			project.setName(newName);
			fail("Exception should have been thrown, no employee islogged in");
		} catch (OperationNotAllowedException e) {
			assertEquals(Error.NO_EMPLOYEE_LOGGED_IN, e.getError());
		}
		try {
			project.setType(newType);
			fail("Exception should have been thrown, no employee is logged in");
		} catch (OperationNotAllowedException e) {
			assertEquals(Error.NO_EMPLOYEE_LOGGED_IN, e.getError());
		}
		try {
			project.setCustomerName(newCustomerName);
			fail("Exception should have been thrown, no employee is logged in");
		} catch (OperationNotAllowedException e) {
			assertEquals(Error.NO_EMPLOYEE_LOGGED_IN, e.getError());
		}
		try {
			project.setDescription(newDescription);
			fail("Exception should have been thrown, no employee is logged in");
		} catch (OperationNotAllowedException e) {
			assertEquals(Error.NO_EMPLOYEE_LOGGED_IN, e.getError());
		}
		try {
			project.setStartDate(newStartDate);
			fail("Exception should have been thrown, no employee is logged in");
		} catch (OperationNotAllowedException e) {
			assertEquals(Error.NO_EMPLOYEE_LOGGED_IN, e.getError());
		}
		try {
			project.setEndDate(newEndDate);
			fail("Exception should have been thrown, no employee is logged in");
		} catch (OperationNotAllowedException e) {
			assertEquals(Error.NO_EMPLOYEE_LOGGED_IN, e.getError());
		}
		try {
			project.setManager(emp1);
			fail("Exception should have been thrown, no employee is logged in");
		} catch (OperationNotAllowedException e) {
			assertEquals(Error.NO_EMPLOYEE_LOGGED_IN, e.getError());
		}
		
		// Test if data has been saved
		Project projectInApp = app.getProjectById(project.getId());
		assertEquals(oldName, projectInApp.getName());
		assertEquals(oldType, projectInApp.getType());
		assertEquals(oldCustomerName, projectInApp.getCustomerName());
		assertEquals(oldDescription, projectInApp.getDescription());
		assertEquals(oldStartDate, projectInApp.getStartDate());
		assertEquals(oldEndDate, projectInApp.getEndDate());
		assertNull(projectInApp.getManager());
		
	}

	@Test
	public void testEditProjectStartDateNotMonday() throws OperationNotAllowedException {
		// Login an employee.
		app.employeeLogin("filp");
		assertEquals(emp2,app.getEmployeeLoggedIn());

		Calendar oldStartDate = project.getStartDate();
		Calendar newStartDate = new GregorianCalendar(2013, Calendar.APRIL, 2);
		try {		
			project.setStartDate(newStartDate);
			fail("An exception should have been thrown since April the 2nd 2013 is not a Monday and cannot be a start date for the project.");
		}
		catch(OperationNotAllowedException e) {
			assertEquals(Error.START_DATE_NOT_MONDAY, e.getError());
		}
		
		assertNull(project.getStartDate());
		
		// Check that the project is not edited
		assertEquals(oldStartDate, project.getStartDate());
		assertEquals(oldStartDate, app.getProjectById(project.getId()).getStartDate());
	}
	
	@Test
	public void testEditProjectEndDateNotFriday() throws OperationNotAllowedException {
		// Login an employee
		app.employeeLogin("filp");
		assertEquals(emp2,app.getEmployeeLoggedIn());

		Calendar oldEndDate = project.getEndDate();
		Calendar newEndDate = new GregorianCalendar(2013, Calendar.APRIL, 2);
		try {		
			project.setStartDate(newEndDate);
			fail("An exception should have been thrown since April the 2nd 2013 is not a Monday and cannot be a start date for the project.");
		}
		catch(OperationNotAllowedException e) {
			assertEquals(Error.START_DATE_NOT_MONDAY, e.getError());
		}
		
		assertNull(project.getEndDate());
		
		// Check that project is not edited
		assertEquals(oldEndDate, project.getEndDate());
		assertEquals(oldEndDate, app.getProjectById(project.getId()).getEndDate());
	}
	
	@Test
	public void testEditProjectStartDateLaterThanEndDate() throws OperationNotAllowedException {
		// Login an employee.
		app.employeeLogin("filp");
		assertEquals(emp2,app.getEmployeeLoggedIn());
	
		Calendar oldEndDate = project.getEndDate();
		Calendar newStartDate = new GregorianCalendar(2013, Calendar.MAY, 6);	// Monday
		Calendar newEndDate = new GregorianCalendar(2013, Calendar.APRIL, 5); 	// Friday
		try {
			project.setStartDate(newStartDate);
			project.setEndDate(newEndDate);
			fail("An exception should have been thrown since start date cannot be later than end date.");
		}
		catch(OperationNotAllowedException e) {
			assertEquals(Error.END_DATE_ERROR, e.getError());
		}
		
		assertEquals(newStartDate, project.getStartDate());
		assertEquals(newStartDate, app.getProjectById(project.getId()).getStartDate());
		
		assertEquals(oldEndDate, project.getEndDate());
		assertEquals(oldEndDate, app.getProjectById(project.getId()).getEndDate());
	}
	
	/*
	 * @author Marianne Louis-Hansen
	 */
	@Test
	public void testEditProjectManagerNotRegistered() throws OperationNotAllowedException {
		// Login an employee
		app.employeeLogin("filp");
		assertEquals(emp2,app.getEmployeeLoggedIn());

		Employee oldManager = project.getManager();
		try {
			project.setManager(emp3);
			fail("An exception should have been thrown since employee to be assigned as manager is not registered in the app.");
		}
		catch(OperationNotAllowedException e) {
			assertEquals(Error.EMPLOYEE_NOT_REGISTERED, e.getError());
		}
		
		// Check that the manager has not changed
		assertEquals(oldManager, project.getEndDate());
		assertEquals(oldManager, app.getProjectById(project.getId()).getEndDate());
	}
}
