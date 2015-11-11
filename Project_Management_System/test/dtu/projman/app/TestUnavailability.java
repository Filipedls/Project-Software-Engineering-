package dtu.projman.app;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Test;
/*
 * @author Filipe Silva
 */
public class TestUnavailability extends SampleDataSetup {

	/** 
	 * Tests the scenario when an employee wants to create an Unavailability.
	 * <ol>
	 *  <li> The employee has to login with the username.
	 *  <li> The employee adds an unavailability.
	 * </ol>
	 * @author Filipe Silva
	 * @throws OperationNotAllowedException 
	 */
	@Test
	public void testUnavailability() throws OperationNotAllowedException {
		// 1. Do the login
		Employee employee = app.employeeLogin("fili");

		// 2. Check that there are no unavailabilities
		assertEquals(employee.getUnavailabilities().size(),0);
		
		// 3. Add a new unavailability
		Calendar startDate = new GregorianCalendar(2013, Calendar.MAY, 6);
		Calendar endDate = new GregorianCalendar(2013, Calendar.MAY, 10);
		Unavailability unavailability = app.createUnavailability(employee, startDate, endDate, "Unavailability 1");
		
		// 4. Check the number of unavailabilities and if it's the right unavailability
		assertEquals(employee.getUnavailabilities().size(),1);
		assertEquals(app.getUnavailabilityById(unavailability.getId()),unavailability);
		
	}
	
	/** 
	 * Tests the scenario when an employee wants to create an Unavailability with a worng input (Alternative Scenario A).
	 * <ol>
	 *  <li> The employee has to login with the username.
	 *  <li> The employee tries to add an unavailability, but an exception is raised.
	 * </ol>
	 * @author Filipe Silva
	 * @throws OperationNotAllowedException 
	 */
	@Test
	public void testUnavailabilityWrongInput() throws OperationNotAllowedException {
		// 1. Do the login
		Employee employee = app.employeeLogin("fili");

		// 2. Check that there are no unavailabilities
		assertEquals(employee.getUnavailabilities().size(),0);
		
		// * Start Date it's not a Monday
		// Add a new unavailability
		Calendar startDate = new GregorianCalendar(2013, Calendar.MAY, 7);
		Calendar endDate = new GregorianCalendar(2013, Calendar.MAY, 10);
		try {		
			app.createUnavailability(employee, startDate, endDate, "Unavailability 1");
			fail("An exception should have been thrown since 7 May 2013 is not monday and cannot be a start date for the project.");
		}
		catch(OperationNotAllowedException e) {
			assertEquals(Error.START_DATE_NOT_MONDAY, e.getError());
		}
		
		
		// Check the number of unavailabilities.
		assertEquals(employee.getUnavailabilities().size(),0);
		
		// * End Date it's not a Friday
		// Add a new unavailability
		endDate = new GregorianCalendar(2013, Calendar.MAY, 9);
		startDate = new GregorianCalendar(2013, Calendar.MAY, 6);
		try {		
			app.createUnavailability(employee, startDate, endDate, "Unavailability 1");
			fail("An exception should have been thrown since 9 May 2013 is not monday and cannot be a start date for the project.");
		}
		catch(OperationNotAllowedException e) {
			assertEquals(Error.END_DATE_NOT_FRIDAY, e.getError());
		}
		
		// Check the number of unavailabilities.
		assertEquals(employee.getUnavailabilities().size(),0);
		
		// * End Date is before than the start date
		// Add a new unavailability
		endDate = new GregorianCalendar(2013, Calendar.MAY, 3);
		try {		
			app.createUnavailability(employee, startDate, endDate, "Unavailability 1");
			fail("An exception should have been thrown since 9 May 2013 is not monday and cannot be a start date for the project.");
		}
		catch(OperationNotAllowedException e) {
			assertEquals(Error.START_DATE_ERROR, e.getError());
		}
		
		// Check the number of unavailabilities.
		assertEquals(employee.getUnavailabilities().size(),0);

	}
	
	/** 
	 * Tests the scenario when an employee wants to create an Unavailability with a task for that period (Alternative Scenario B).
	 * <ol>
	 *  <li> The employee has to login with the username.
	 *  <li> The employee tries to add an unavailability, but an exception is raised.
	 * </ol>
	 * @author Filipe Silva
	 * @throws OperationNotAllowedException 
	 */
	@Test
	public void testUnavailabilityTaskAssigned() throws OperationNotAllowedException {
		// 1. Do the login
		Employee employee = app.employeeLogin("fili");

		// 2. Check that there are no unavailabilities
		assertEquals(employee.getUnavailabilities().size(),0);
		
		// Set the date of a task.
		Task task = app.getProjects().get(0).getActivities().get(0).getTasks().get(0);
		Calendar startDate = new GregorianCalendar(2013, Calendar.MAY, 6);
		Calendar endDate = new GregorianCalendar(2013, Calendar.MAY, 10);
		task.setStartDate(startDate);
		task.setEndDate(endDate);
		
		// Set the task to the employee
		employee.addTask(task);

		// Try to add a new unavailability
		try {		
			app.createUnavailability(employee, startDate, endDate, "Unavailability 1");
			fail("An exception should have been thrown since employee is assigned a task at that period.");
		}
		catch(OperationNotAllowedException e) {
			assertEquals(Error.EMPLOYEE_NOT_AVAILABLE, e.getError());
		}
		
		
		// Check the number of unavailabilities.
		assertEquals(employee.getUnavailabilities().size(),0);
		
	}
	
	/** 
	 * Tests the scenario when an employee wants to create an Unavailability with another unavailability for that period (Alternative Scenario C).
	 * <ol>
	 *  <li> The employee has to login with the username.
	 *  <li> The employee tries to add an unavailability, but an exception is raised.
	 * </ol>
	 * @author Filipe Silva
	 * @throws OperationNotAllowedException 
	 */
	@Test
	public void testUnavailabilityUnavailabilityAssigned() throws OperationNotAllowedException {
		// 1. Do the login
		Employee employee = app.employeeLogin("fili");

		// 2. Check that there are no unavailabilities
		assertEquals(employee.getUnavailabilities().size(),0);
		
		// Create the first unavailability
		Calendar startDate = new GregorianCalendar(2013, Calendar.MAY, 6);
		Calendar endDate = new GregorianCalendar(2013, Calendar.MAY, 10);
		app.createUnavailability(employee, startDate, endDate, "Unavailability 1");

		// Try to add a new unavailability
		try {		
			app.createUnavailability(employee, startDate, endDate, "Unavailability 1");
			fail("An exception should have been thrown since employee is assigned a task at that period.");
		}
		catch(OperationNotAllowedException e) {
			assertEquals(Error.EMPLOYEE_NOT_AVAILABLE, e.getError());
		}
		
		
		// Check the number of unavailabilities.
		assertEquals(employee.getUnavailabilities().size(),1);
		
	}


}
