package dtu.projman.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Test;

/*
 * @author Marianne Louis-Hansen
 */
public class TestAddEmployeeToTask extends SampleDataSetup {
	
	/** 
	 * Tests when an employee is assigned as developer of a task.
	 * <ol>
	 *  <li> The employee has to login with the username.
	 *  <li> The employee is assigned to a task.
	 * </ol>
	 * @author Filipe Silva
	 * @throws OperationNotAllowedException 
	 */
	@Test
	public void testAddEmployeeToTask() throws OperationNotAllowedException{ 
	
		app.employeeLogoff();
		Employee employee = app.employeeLogin("fili");
		
		// Set the employee as project manager
		Project project = app.getProjects().get(1);
		project.setManager(employee);
		
		// Select the task to test
		Task task = project.getActivities().get(0).getTasks().get(0);
		
		// Complete the date information in the task
		Calendar StartDate = new GregorianCalendar(2013, Calendar.APRIL, 22);
		Calendar EndDate = new GregorianCalendar(2013, Calendar.APRIL, 26);
		
		task.setStartDate(StartDate);
		task.setEndDate(EndDate);
		
		// Add the employee to the task and check for it
		task.assignDeveloper(employee);
		assertEquals(task.getDeveloper(), employee);
	}
	
	/** 
	 * Tests when an employee is assigned as developer of a task without start and end date (Alternative Scenario B).
	 * <ol>
	 *  <li> The employee has to login with the username.
	 *  <li> The employee tries to assigned an employee to a task.
	 *  <li> The system notifies the employee and doesn't save the operation.
	 * </ol>
	 * @author Filipe Silva
	 * @throws OperationNotAllowedException 
	 */
	@Test
	public void testAddEmployeeToTaskWithoutDates() throws OperationNotAllowedException{ 
	
		app.employeeLogoff();
		Employee employee = app.employeeLogin("fili");
		
		// Set the employee as project manager
		Project project = app.getProjects().get(1);
		project.setManager(employee);
		
		// Select the task to test
		Task task = project.getActivities().get(0).getTasks().get(1);
		
		// * END DATE
		// Test the end date, making sure that the it is null
		Calendar StartDate = new GregorianCalendar(2013, Calendar.APRIL, 22);
		task.setStartDate(StartDate);
		assertEquals(task.getEndDate(), null);
		
		// Try to change the developer
		try {		
			task.assignDeveloper(employee);
			fail("An exception should have been thrown. No dates in the task.");
		}
		catch(OperationNotAllowedException e) {
			assertEquals(Error.ASSIGN_DEVELOPER_ERROR_DATE, e.getError());
		}
		
		// Check that the previous employee still as developer
		assertEquals(task.getDeveloper(), null);
		
		// * START DATE
		task = project.getActivities().get(0).getTasks().get(0);
		// Test the start date, making sure that the it is null
		Calendar EndDate = new GregorianCalendar(2013, Calendar.APRIL, 26);
		task.setEndDate(EndDate);
		assertEquals(task.getStartDate(), null);
		
		// Try to change the developer
		try {		
			task.assignDeveloper(employee);
			fail("An exception should have been thrown. No dates in the task.");
		}
		catch(OperationNotAllowedException e) {
			assertEquals(Error.ASSIGN_DEVELOPER_ERROR_DATE, e.getError());
		}
		
		// Check that the previous employee still as developer
		assertEquals(task.getDeveloper(), null);
	}

	/** 
	 * Tests when an employee is assigned as developer of a task but he has already 20 task in at least one of that task's week (Alternative Scenario C).
	 * <ol>
	 *  <li> The employee has to login with the username.
	 *  <li> The employee tries to assigned an employee to a task.
	 *  <li> The system notifies the employee and doesn't save the operation.
	 * </ol>
	 * @author Filipe Silva
	 * @throws OperationNotAllowedException 
	 */
	@Test
	public void testAddEmployeeToTaskMoreThan20Tasks() throws OperationNotAllowedException{ 
	
		app.employeeLogoff();
		Employee employee = app.employeeLogin("fili");
		
		// Set the employee as project manager
		Project project = app.getProjects().get(1);
		project.setManager(employee);
		
		// Select the task to test
		Activity activity = project.getActivities().get(0);
		Task task = activity.getTasks().get(0);
		
		// Complete the date information in the task
		Calendar StartDate = new GregorianCalendar(2013, Calendar.APRIL, 15);
		Calendar EndDate = new GregorianCalendar(2013, Calendar.APRIL, 26);
		
		task.setStartDate(StartDate);
		task.setEndDate(EndDate);
		
		// Make sure and check that an employee has no tasks
		List<Task> tasks = new ArrayList<Task>();
		employee.setTasks(tasks);
		assertEquals(employee.getTasks().size(),0);
		
		// Create and Add 20 Tasks to the employee
		Task taskAux = null;
		
		for(int i = 0; i<20; i++) {
			taskAux = app.createTask(activity, "Test Add Employee To Task " + i);
			taskAux.setStartDate(StartDate);
			taskAux.setEndDate(EndDate);
			taskAux.assignDeveloper(employee);
			assertEquals(taskAux.getDeveloper(), employee);
		}
		
		assertEquals(employee.getTasks().size(), 20);
		
		// Try to change the developer
		try {		
			task.assignDeveloper(employee);
			fail("An exception should have been thrown. The employee is not a project Maager.");
		}
		catch(OperationNotAllowedException e) {
			assertEquals(Error.ASSIGN_DEVELOPER_ERROR_TASKSNUMBER, e.getError());
		}
		
		// Check that the employee still has 20 tasks
		assertEquals(employee.getTasks().size(), 20);
	}
	
	/** 
	 * Tests when an employee is assigned as developer of a task.
	 * <ol>
	 *  <li> The employee has to login with the username.
	 *  <li> The employee is assigned to a task.
	 * </ol>
	 * @author Filipe Silva
	 * @throws OperationNotAllowedException 
	 */
	@Test
	public void testAddEmployeeToTaskUnavailability() throws OperationNotAllowedException{ 
	
		app.employeeLogoff();
		Employee employee = app.employeeLogin("fili");
		
		// Set the employee as project manager
		Project project = app.getProjects().get(1);
		project.setManager(employee);
		
		// Select the task to test
		Task task = project.getActivities().get(0).getTasks().get(0);
		
		// Complete the date information in the task
		Calendar StartDate = new GregorianCalendar(2013, Calendar.APRIL, 22);
		Calendar EndDate = new GregorianCalendar(2013, Calendar.APRIL, 26);
		
		task.setStartDate(StartDate);
		task.setEndDate(EndDate);
		
		// Add an unavailability to the employee
		app.createUnavailability(employee, StartDate, EndDate, "Unavailability 1 - Test");
		
		try {		
			task.assignDeveloper(employee);
			fail("An exception should have been thrown. The employee has an unavailability.");
		}
		catch(OperationNotAllowedException e) {
			assertEquals(Error.ASSIGN_DEVELOPER_ERROR_UNAVAILABILITY, e.getError());
		}
		
		// Check that the employee still has no tasks
		assertEquals(employee.getTasks().size(), 0);
	}
	
	/** 
	 * Tests when an employee is assigned as developer of a task without start and end date (Alternative Scenario B).
	 * <ol>
	 *  <li> The employee has to login with the username.
	 *  <li> The employee tries to assigned an employee to a task.
	 *  <li> The system notifies the employee and doesn't save the operation.
	 * </ol>
	 * @author Filipe Silva
	 * @throws OperationNotAllowedException 
	 */
	@Test
	public void testAddEmployeeToTaskBeingAnHelper() throws OperationNotAllowedException{ 
	
		app.employeeLogoff();
		Employee employee = app.employeeLogin("fili");
		
		// Set the employee as project manager
		Project project = app.getProjects().get(1);
		project.setManager(employee);
		
		// Select the task to test
		Task task = project.getActivities().get(0).getTasks().get(1);
		
		// Complete the date information in the task
		Calendar StartDate = new GregorianCalendar(2013, Calendar.APRIL, 22);
		Calendar EndDate = new GregorianCalendar(2013, Calendar.APRIL, 26);
		
		task.setStartDate(StartDate);
		task.setEndDate(EndDate);
		
		// Assign employee as a helper
		task.assignHelper(employee, 2);
		
		// Try to change the developer
		try {		
			task.assignDeveloper(employee);
			fail("An exception should have been thrown. The employee is an helper.");
		}
		catch(OperationNotAllowedException e) {
			assertEquals(Error.ASSIGN_DEVELOPER_ERROR_HELPER, e.getError());
		}
		
		// Check that the previous employee still as developer
		assertEquals(task.getDeveloper(), null);
		
	}

}
