package dtu.projman.app;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

/*
 * @author Atakan Kaya
 */
public class TestCreateTask {

	ProjManApp app = new ProjManApp();
	Employee emp1, emp2, emp3;
	Project project;
	Activity activity;
	
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
		
		// Register employees
		app.registerEmployee(emp1);
		app.registerEmployee(emp2);
		
		app.employeeLogin("filp");
		
		// Create project and activity
		project = app.createProject("Project 1 - Test", ProjectType.EXTERNAL, "Microsoft");
		activity = app.createActivity(project, "Implementation");
		
		app.employeeLogoff();
	}

	@Test
	public void testCreateTask() throws OperationNotAllowedException {
		// Login
		app.employeeLogin("filp");
		assertEquals(emp2,app.getEmployeeLoggedIn());

		// Check that there are no tasks of the activity
		assertTrue(activity.getTasks().isEmpty());
		
		// Create task
		String name = "Implement the create task functionality ";
		Task task = app.createTask(activity, name);
		
		// Check that task is created
		assertEquals(task.getName(), name);
		assertEquals(task.getState(), State.NOTSTARTED);
		assertEquals(task.getType(), TaskType.MAIN);
		
		// Check that task is added to the app
		List<Task> tasks = app.getActivityById(activity.getId()).getTasks();
		assertEquals(1, tasks.size());
		assertEquals(name, tasks.get(0).getName());
		assertEquals(task.getId(), tasks.get(0).getId());
		assertEquals(task, app.getTaskById(task.getId()));
		assertEquals(task.getApp(), app);
		assertTrue(task.getApp() == app);
	}

	@Test
	public void testCreateTestNotLoggedIn() throws OperationNotAllowedException {
		// Check that nobody is logged in.
		assertNull(app.getEmployeeLoggedIn());
		
		// Check that there are no tasks of the activity
		assertTrue(activity.getTasks().isEmpty());
		
		// Create task
		String name = "Implement the create task functionality ";
		Task task = null;

		// Add the task to the activity
		try {
			task = app.createTask(activity, name);
			fail("OperationNotAllowedException exception should have been thrown; username not logged in");
		} catch (OperationNotAllowedException e) {
			// Check that the exception thrown has the correct error message and knows which operation failed.
			assertEquals(Error.NO_EMPLOYEE_LOGGED_IN,e.getError());
		}
		
		// Check that task is not added to the app
		assertEquals(0,app.getActivityById(activity.getId()).getTasks().size());
		assertNull(task);
	}
	
	@Test
	public void testCreateTaskMandInfoNotProvided() throws OperationNotAllowedException {
		// Login
		app.employeeLogin("filp");
		assertEquals(emp2,app.getEmployeeLoggedIn());

		// Check that there are no tasks of the activity
		assertTrue(activity.getTasks().isEmpty());
		
		// Create task
		String name = "";
		Task task = null;

		try {
			task = app.createTask(activity, name);
			fail("An exception should have been thrown since all the mandatory info has to be provided.");
		}
		catch(OperationNotAllowedException e) {
			assertEquals(Error.NAME_ERROR, e.getError());
		}
		
		// Check that task is not added to the app
		assertEquals(0,app.getActivityById(activity.getId()).getTasks().size());
		assertNull(task);
	}
	
	@Test
	public void testCreateTaskActivityDoesNotExist() throws OperationNotAllowedException {
		// Login
		app.employeeLogin("filp");
		assertEquals(emp2,app.getEmployeeLoggedIn());
		
		// Create an activity, but dont put in the app
		Activity activityNotInApp = new Activity("Testing");
		
		// Create task
		String name = "Implement the create task functionality ";
		Task task = null;
		try {
			task = app.createTask(activityNotInApp, name);
			fail("An exception should have been thrown since project is not in the app.");
		}
		catch(OperationNotAllowedException e) {
			assertEquals(Error.ACTIVITY_NOT_REGISTERED, e.getError());
		}
		
		// Check that task is not added to the app
		assertEquals(0,app.getActivityById(activity.getId()).getTasks().size());
		assertNull(task);
	}
}
