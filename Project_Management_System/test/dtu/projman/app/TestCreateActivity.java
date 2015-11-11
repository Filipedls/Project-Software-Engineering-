package dtu.projman.app;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

/*
 * @author Marc Thomsen
 */
public class TestCreateActivity {

	ProjManApp app = new ProjManApp();
	Employee emp1;
	Project project;
	
	@Before
	public void setUp() throws Exception {
		// Create an employee
		String 	fullname1 = "Filipe Silva";
		String 	username1 = "filp";
		String 	email1 = "filipesilva@gmail.com";
		
		emp1 = new Employee(fullname1, username1, email1);

		
		// Register the employee
		app.registerEmployee(emp1);
		
		app.employeeLogin("filp");
		
		// Create a project
		project = app.createProject("Project 1 - Test", ProjectType.EXTERNAL, "Microsoft");
		
		app.employeeLogoff();
	}

	@Test
	public void testCreateActivity() throws OperationNotAllowedException {
		// Login an employee and check that this employee is logged in. 
		app.employeeLogin("filp");
		assertEquals(emp1,app.getEmployeeLoggedIn());

		// Check that there are no activities of project
		assertTrue(project.getActivities().isEmpty());
		
		// Create an activity
		String name = "Design";
		Activity activity = app.createActivity(project, name);
		
		// Check that the activity is created
		assertEquals(activity.getName(), name);
		assertEquals(activity.getState(), State.NOTSTARTED);
		
		// Check that activity is added to the app
		List<Activity> activities = app.getProjectById(project.getId()).getActivities();
		assertEquals(1, activities.size());
		assertEquals(name, activities.get(0).getName());
		assertEquals(activity.getId(), activities.get(0).getId());
		assertEquals(activity, app.getActivityById(activity.getId()));
		assertEquals(activity.getApp(), app);
		assertTrue(activity.getApp() == app);
	}
	
	@Test
	public void testCreateActivityNotLoggedIn() throws OperationNotAllowedException {
		// Check that nobody is logged in.
		assertNull(app.getEmployeeLoggedIn());
		
		// Check that there are no activities in the project
		assertTrue(project.getActivities().isEmpty());
		
		// Create an activity
		String name = "Design";
		Activity activity = null;

		// Add the activity to the project
		try {
			activity = app.createActivity(project, name);
			fail("OperationNotAllowedException exception should have been thrown; employee is not logged in");
		} catch (OperationNotAllowedException e) {
			// Check that the exception thrown has the correct error message and knows which operation failed.
			assertEquals(Error.NO_EMPLOYEE_LOGGED_IN, e.getError());
		}
		
		// Check that the activity is not added to the app
		assertEquals(0,app.getProjectById(project.getId()).getActivities().size());
		assertNull(activity);
	}
	
	@Test
	public void testCreateActivityMandInfoNotProvided() throws OperationNotAllowedException {
		// Login an employee and check that this employee is logged in.
		app.employeeLogin("filp");
		assertEquals(emp1,app.getEmployeeLoggedIn());

		// Check that there are no activities in the project
		assertTrue(project.getActivities().isEmpty());
		
		// Create an activity
		String name = "";
		Activity activity = null;
		try {
			activity = app.createActivity(project, name);
			fail("An exception should have been thrown since all the mandatory info has to be provided.");
		}
		catch(OperationNotAllowedException e) {
			assertEquals(Error.NAME_ERROR, e.getError());
		}
		
		// Check that the activity is not added to the app
		assertEquals(0,app.getProjectById(project.getId()).getActivities().size());
		assertNull(activity);
	}

	@Test
	public void testCreateActivityProjectDoesNotExist() throws OperationNotAllowedException {
		// Login an employee and check that this employee is logged in.
		app.employeeLogin("filp");
		assertEquals(emp1,app.getEmployeeLoggedIn());
		
		// Create a project, but don't put it in the app
		Project projectNotInApp = new Project("Test", ProjectType.EXTERNAL, "Microsoft");
		
		// Create an activity
		String name = "Design";
		Activity activity = null;
		try {
			activity = app.createActivity(projectNotInApp, name);
			fail("An exception should have been thrown since the project is not in the app.");
		}
		catch(OperationNotAllowedException e) {
			assertEquals(Error.PROJECT_NOT_REGISTERED, e.getError());
		}
		
		// Check that the activity is not added to the app
		assertEquals(0, projectNotInApp.getActivities().size());
		assertNull(activity);
	}
}