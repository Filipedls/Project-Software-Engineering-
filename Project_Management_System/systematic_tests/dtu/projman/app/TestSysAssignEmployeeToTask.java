package dtu.projman.app;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

/*
 * @author Marc Thomsen
 */
public class TestSysAssignEmployeeToTask {

	ProjManApp app = new ProjManApp();
	Project project;
	Activity activity; 
	Task task; 
	Employee employee1;
	Calendar StartDate = new GregorianCalendar(2013, Calendar.MAY, 6);
	Calendar EndDate = new GregorianCalendar(2013, Calendar.MAY, 10);
	
	@Before
	public void setUp() throws Exception {
		
		employee1 = new Employee("Marc Thomsen", "mama", "marcmthomsen@gmail.com");
		app.registerEmployee(employee1);
		
		app.employeeLogin("mama");
		project = app.createProject("test", ProjectType.EXTERNAL, "Microsoft");
		activity = app.createActivity(project, "test_activity");
		//activity = project.getActivities().get(1);
		task = app.createTask(activity, "test_task");
		//task = project.getActivities().get(0).getTasks().get(1);
		task.setStartDate(StartDate);
		task.setEndDate(EndDate);
		app.employeeLogoff();
	}
	
	// The assigned employee is unavailable
	@Test
	public void testInputDatasetA() throws OperationNotAllowedException {
		
		app.employeeLogin("mama"); 

		Calendar StartDate = new GregorianCalendar(2013, Calendar.MAY, 6);
		Calendar EndDate = new GregorianCalendar(2013, Calendar.MAY, 10);

		app.createUnavailability(employee1, StartDate, EndDate, "Unavailable");

		try {
			task.assignDeveloper(employee1);
			fail("ASSIGN_DEVELOPER_ERROR_UNAVAILABILITY should have been thrown");
		} catch (OperationNotAllowedException e) {
			assertEquals(Error.ASSIGN_DEVELOPER_ERROR_UNAVAILABILITY, e.getError());
		}
	}

	// The assigned employee is available but is a helper
	@Test
	public void testInputDatasetB() throws OperationNotAllowedException {

		employee1 = app.employeeLogin("mama"); 
		
		Employee employee2 = new Employee("Jane Doe", "jdoe", "jane@doe.com");
		app.registerEmployee(employee2);

		task.assignHelper(employee2, 2);

		Calendar StartDate = new GregorianCalendar(2013, Calendar.MAY, 13);
		Calendar EndDate = new GregorianCalendar(2013, Calendar.MAY, 17);

		app.createUnavailability(employee2, StartDate, EndDate, "Available for the task");

		try {
			task.assignDeveloper(employee2);
			fail("EMPLOYEE_NOT_AVAILABLE should have been thrown.");
		} catch (OperationNotAllowedException e) {
			assertEquals(Error.ASSIGN_DEVELOPER_ERROR_HELPER, e.getError());
			
		}
		
	}		
		
	// The assigned employee is available, tasks = 20, employee is already a helper
	@Test
	public void testInputDatasetC1() throws OperationNotAllowedException {
		
		employee1 = app.employeeLogin("mama"); 
	
		Employee employee3 = new Employee("Mary Doe", "mdoe", "mary@doe.com");
		app.registerEmployee(employee3);
		
		Calendar newStartDate = new GregorianCalendar(2013, Calendar.MAY, 27);
		Calendar newEndDate = new GregorianCalendar(2013, Calendar.MAY, 31);
		
		app.createUnavailability(employee3, newStartDate, newEndDate, "Available for the task");
		
		Task task = null;
		
		for(int i = 0; i < 19; i++) {
			task = app.createTask(activity, "Test Add Employee To Task " + i);
			task.setStartDate(StartDate);
			task.setEndDate(EndDate);
			task.assignDeveloper(employee3);
			assertEquals(task.getDeveloper(), employee3);
		}
		Task newTask = app.createTask(activity, "test3");
		newTask.setStartDate(StartDate);
		newTask.setEndDate(EndDate);
		
		newTask.assignHelper(employee3, 2);
		assertEquals(employee3.getTasks().size(), 20);
		
			
		
		try {
			newTask.assignDeveloper(employee3);
			fail("ASSIGN_DEVELOPER_ERROR_TASKSNUMBER should have been thrown.");
		} catch (OperationNotAllowedException e) {
			assertEquals(Error.ASSIGN_DEVELOPER_ERROR_TASKSNUMBER, e.getError());
		}
	}
	

	// The assigned employee is available, tasks = 20, employee is not a helper
	@Test
	public void testInputDatasetC2() throws OperationNotAllowedException {
		
		employee1 = app.employeeLogin("mama"); 
		
		Employee employee2 = new Employee("Jane Doe", "jdoe", "jane@doe.com");
		app.registerEmployee(employee2);
		
		Calendar newStartDate = new GregorianCalendar(2013, Calendar.MAY, 13);
		Calendar newEndDate = new GregorianCalendar(2013, Calendar.MAY, 17);
		
		app.createUnavailability(employee1, newStartDate, newEndDate, "Available for the task");
		
		Task task = null;
		
		for(int i = 0; i < 20; i++) {
			task = app.createTask(activity, "Test Add Employee To Task " + i);
			task.setStartDate(StartDate);
			task.setEndDate(EndDate);
			task.assignDeveloper(employee1);
			assertEquals(task.getDeveloper(), employee1);
		}
		
		assertEquals(employee1.getTasks().size(), 20);	
		
		try {
			task.assignDeveloper(employee1);
			fail("ASSIGN_DEVELOPER_ERROR_TASKSNUMBER should have been thrown.");
		} catch (OperationNotAllowedException e) {
			assertEquals(Error.ASSIGN_DEVELOPER_ERROR_TASKSNUMBER, e.getError());
		}
	}	

	// The assigned employee is available, tasks < 20, employee is already a helper
	@Test
	public void testInputDatasetD1() throws OperationNotAllowedException {
		
		employee1 = app.employeeLogin("mama"); 
		
		Employee employee2 = new Employee("Jane Doe", "jdoe", "jane@doe.com");
		app.registerEmployee(employee2);
		
		Calendar newStartDate = new GregorianCalendar(2013, Calendar.MAY, 20);
		Calendar newEndDate = new GregorianCalendar(2013, Calendar.MAY, 24);
		
		app.createUnavailability(employee1, newStartDate, newEndDate, "Available for the task");
		
		Task task = null;
		
		for(int i = 0; i < 18; i++) {
			task = app.createTask(activity, "Test Add Employee To Task " + i);
			task.setStartDate(StartDate);
			task.setEndDate(EndDate);
			task.assignDeveloper(employee1);
			assertEquals(task.getDeveloper(), employee1);
		}
		
		assertEquals(employee1.getTasks().size(), 18);	
		Task newTask = app.createTask(activity , "Test again");
		newTask.setEndDate(EndDate);
		newTask.setStartDate(StartDate);
		newTask.assignHelper(employee1, 2);
		
		try {
			newTask.assignDeveloper(employee1);
			fail("ASSIGN_DEVELOPER_ERROR_HELPER should have been thrown.");
		} catch (OperationNotAllowedException e) {
			assertEquals(Error.ASSIGN_DEVELOPER_ERROR_HELPER, e.getError());
		}
	}		
	
	// The assigned employee is available, tasks < 20, employee is not a helper
	@Test
	public void testInputDatasetD2() throws OperationNotAllowedException {
		
		Employee employee1 = app.employeeLogin("mama"); 
		
		Employee employee2 = new Employee("Jane Doe", "jdoe", "jane@doe.com");
		app.registerEmployee(employee2);
		
		Calendar newStartDate = new GregorianCalendar(2013, Calendar.MAY, 13);
		Calendar newEndDate = new GregorianCalendar(2013, Calendar.MAY, 17);
		
		app.createUnavailability(employee1, newStartDate, newEndDate, "Available for the task");
		
		Task task = null;
		for(int i = 0; i < 19; i++) {
			task = app.createTask(activity, "Test Add Employee To Task " + i);
			task.setStartDate(StartDate);
			task.setEndDate(EndDate);
			task.assignDeveloper(employee1);
			assertEquals(task.getDeveloper(), employee1);
		}
		assertEquals(employee1.getTasks().size(), 19);	
		
		task.assignDeveloper(employee1);	
		assertEquals(employee1.getTasks().size(), 20);
		assertEquals(employee1, task.getDeveloper());
	}
}