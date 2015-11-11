package dtu.projman.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

/*
 * @author Filipe Silva
 */
public class TestCreateProject {

	ProjManApp app = new ProjManApp();
	Employee emp1;
	
	@Before
	public void setUp() throws Exception {
		// Create an employee
		String fullname1 = "Filipe Silva";
		String username1 = "filp"; 
		String email1 = "filipesilva@gmail.com";
		
		emp1 = new Employee(fullname1, username1, email1);
		
		// Register employees
		app.registerEmployee(emp1);
	}
	
	/** 
	 * Tests the scenario when an employee wants to create a project.
	 * <ol>
	 *  <li> The employee has to login with the username.
	 *  <li> The project to be added is created.
	 *  <li> The project is added to the application by calling addProject from the Project Manager application.
	 * </ol>
	 */
	@Test
	public void testCreateProject() throws OperationNotAllowedException {
		// Login an employee
		app.employeeLogin("filp");
		assertEquals(emp1,app.getEmployeeLoggedIn());

		// Check that there are no projects in the list
		assertEquals(0,app.getProjects().size());

		// Create a project
		String name = "Project 1 - Test";
		ProjectType type = ProjectType.EXTERNAL;
		String companyName = "Microsoft";
		
		Project project = app.createProject(name, type, companyName);

		// Check that the project is created	
		assertEquals(project.getName(), name);
		assertEquals(project.getType(), type);
		assertEquals(project.getCustomerName(), companyName);
		assertEquals(project.getState(), State.NOTSTARTED);
				
		// Check that the project is added to the app
		List<Project> projects = app.getProjects();
		assertEquals(1, projects.size());
		assertEquals("Project 1 - Test", projects.get(0).getName());
		assertEquals(1, projects.get(0).getId());
		assertEquals(project, app.getProjectById(project.getId()));
		assertEquals(project.getApp(), app);
		assertTrue(project.getApp() == app);
	}
	
	/** 
	 * Tests the scenario when an employee wants to add a project, but is not logged in.
	 * <ol>
	 *  <li> The project to be added is created.
	 *  <li> The project is added to the list by calling addProject from the Project Manager application.
	 *  <li> The application throws an exception.
	 * </ol>
	 */
	@ Test
	public void testCreateProjectNotLoggedIn() throws OperationNotAllowedException {
		// Check that nobody is logged in.
		assertNull(app.getEmployeeLoggedIn());
		
		// Check that there are no projects in the list
		assertEquals(0,app.getProjects().size());
		
		// Create a project
		String name = "Project 1 - Test";
		ProjectType type = ProjectType.EXTERNAL;
		String companyName = "Microsoft";

		Project project = null;
		// Add the project to the app
		try {
			project = app.createProject(name, type, companyName);
			fail("OperationNotAllowedException exception should have been thrown; employee is not logged in");
		} catch (OperationNotAllowedException e) {
			// Check that the exception thrown has the correct error message and knows which operation failed.
			assertEquals(Error.NO_EMPLOYEE_LOGGED_IN,e.getError());
		}
		
		// Check that the project is not added to the app
		assertEquals(0,app.getProjects().size());
		assertNull(project);
	}

	@Test
	public void testCreateProjectMandInfoNotProvided() throws OperationNotAllowedException {
		// Login an employee.
		app.employeeLogin("filp");
		assertEquals(emp1,app.getEmployeeLoggedIn());

		// Check that there are no projects in the list
		assertEquals(0,app.getProjects().size());

		// Create a project
		String name = "";
		ProjectType type = ProjectType.EXTERNAL;
		String companyName = "Microsoft";
		
		Project project = null;
		try {
			project = app.createProject(name, type, companyName);
			fail("An exception should have been thrown since all the mandatory info has to be provided.");
		}
		catch(OperationNotAllowedException e) {
			assertEquals(Error.NAME_ERROR, e.getError());
		}
		
		// Check that the project is not added to the app
		assertNull(project);
		assertEquals(0,app.getProjects().size());
	}
	
}
