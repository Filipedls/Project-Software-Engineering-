package dtu.projman.app;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Test;
/*
 * @author Filipe Silva
 */
public class TestSysUnavailability extends SampleDataSetup{

	/** 
	 * Register Unavailability Systematic Test 
	 * Input data set A
	 * 
	 * @author Filipe Silva
	 * @throws OperationNotAllowedException 
	 */
	@Test
	public void TestUnavailabilityA() throws OperationNotAllowedException {
		// Do the login
		Employee employee = app.employeeLogin("fili");

		// Check that there are no unavailabilities
		assertEquals(employee.getUnavailabilities().size(),0);
		
		// * Input data set A1
		// Add one unavailability
		Calendar startDate = new GregorianCalendar(2013, Calendar.APRIL, 29);
		Calendar endDate = new GregorianCalendar(2013, Calendar.MAY, 3);
		app.createUnavailability(employee, startDate, endDate, "Unavailability 1");
		
		// Check the number of unavailabilities
		assertEquals(employee.getUnavailabilities().size(),1);
		
		// * Input data set A2
		// Add two unavailabilities
		startDate = new GregorianCalendar(2013, Calendar.MAY, 6);
		endDate = new GregorianCalendar(2013, Calendar.MAY, 10);
		app.createUnavailability(employee, startDate, endDate, "Unavailability 2");
		
		startDate = new GregorianCalendar(2013, Calendar.MAY, 13);
		endDate = new GregorianCalendar(2013, Calendar.MAY, 17);
		app.createUnavailability(employee, startDate, endDate, "Unavailability 3");
		
		// Check the number of unavailabilities
		assertEquals(employee.getUnavailabilities().size(),3);
		
	}
	
	/** 
	 * Register Unavailability Systematic Test 
	 * Input data set B - Start Date
	 * 
	 * @author Filipe Silva
	 * @throws OperationNotAllowedException 
	 */
	@Test
	public void TestUnavailabilityB() throws OperationNotAllowedException {
		// Do the login
		Employee employee = app.employeeLogin("fili");

		// Check that there are no unavailabilities
		assertEquals(employee.getUnavailabilities().size(),0);
		
		// * Input data set B1
		// Start date is not the Monday of a week (Sunday)
		Calendar startDate = new GregorianCalendar(2013, Calendar.MAY, 5);
		Calendar endDate = new GregorianCalendar(2013, Calendar.MAY, 10);
		
		// Try to add a new unavailability
		try {		
			app.createUnavailability(employee, startDate, endDate, "Unavailability 1");
			fail("An exception should have been thrown since a date is wrong");
		}
		catch(OperationNotAllowedException e) {
			assertEquals(Error.START_DATE_NOT_MONDAY, e.getError());
		}
		
		// Check the number of unavailabilities
		assertEquals(employee.getUnavailabilities().size(),0);
		
		// * Input data set B2
		// Start date is not the Monday of a week (Tuesday)
		startDate = new GregorianCalendar(2013, Calendar.MAY, 7);
		endDate = new GregorianCalendar(2013, Calendar.MAY, 10);
		
		// Try to add a new unavailability
		try {		
			app.createUnavailability(employee, startDate, endDate, "Unavailability 1");
			fail("An exception should have been thrown since a date is wrong");
		}
		catch(OperationNotAllowedException e) {
			assertEquals(Error.START_DATE_NOT_MONDAY, e.getError());
		}
		
		// Check the number of unavailabilities
		assertEquals(employee.getUnavailabilities().size(),0);

	}
	
	/** 
	 * Register Unavailability Systematic Test 
	 * Input data set C - End Date
	 * 
	 * @author Filipe Silva
	 * @throws OperationNotAllowedException 
	 */
	@Test
	public void TestUnavailabilityC() throws OperationNotAllowedException {
		// Do the login
		Employee employee = app.employeeLogin("fili");

		// Check that there are no unavailabilities
		assertEquals(employee.getUnavailabilities().size(),0);
		
		// * Input data set C1
		// End date is not the Monday of a week (Saturday)
		Calendar startDate = new GregorianCalendar(2013, Calendar.MAY, 6);
		Calendar endDate = new GregorianCalendar(2013, Calendar.MAY, 11);
		
		// Try to add a new unavailability
		try {		
			app.createUnavailability(employee, startDate, endDate, "Unavailability 1");
			fail("An exception should have been thrown since a date is wrong");
		}
		catch(OperationNotAllowedException e) {
			assertEquals(Error.END_DATE_NOT_FRIDAY, e.getError());
		}
		
		// Check the number of unavailabilities
		assertEquals(employee.getUnavailabilities().size(),0);
		
		// * Input data set C2
		// End date is not a Friday (Thursday)
		startDate = new GregorianCalendar(2013, Calendar.MAY, 6);
		endDate = new GregorianCalendar(2013, Calendar.MAY, 9);
		
		// Try to add a new unavailability
		try {		
			app.createUnavailability(employee, startDate, endDate, "Unavailability 1");
			fail("An exception should have been thrown since a date is wrong.");
		}
		catch(OperationNotAllowedException e) {
			assertEquals(Error.END_DATE_NOT_FRIDAY, e.getError());
		}
		
		// Check the number of unavailabilities
		assertEquals(employee.getUnavailabilities().size(),0);

	}
	
	/** 
	 * Register Unavailability Systematic Test 
	 * Input data set D - End Date before Start Date
	 * 
	 * @author Filipe Silva
	 * @throws OperationNotAllowedException 
	 */
	@Test
	public void testUnavailabilityD() throws OperationNotAllowedException {
		// Do the login
		Employee employee = app.employeeLogin("fili");

		// Check that there are no unavailabilities
		assertEquals(employee.getUnavailabilities().size(),0);
		
		// * Input data set D1
		// End date is one week before the start date
		Calendar startDate = new GregorianCalendar(2013, Calendar.MAY, 13);
		Calendar endDate = new GregorianCalendar(2013, Calendar.MAY, 6);
		
		// Try to add a new unavailability
		try {		
			app.createUnavailability(employee, startDate, endDate, "Unavailability 1");
			fail("An exception should have been thrown since a date is wrong (End date before start date).");
		}
		catch(OperationNotAllowedException e) {
			assertEquals(Error.START_DATE_ERROR, e.getError());
		}
		
		// Check the number of unavailabilities
		assertEquals(employee.getUnavailabilities().size(),0);
		
		// * Input data set D2
		// End date is one week before the start date
		startDate = new GregorianCalendar(2013, Calendar.MAY, 13);
		endDate = new GregorianCalendar(2013, Calendar.APRIL, 29);
		
		// Try to add a new unavailability
		try {		
			app.createUnavailability(employee, startDate, endDate, "Unavailability 1");
			fail("An exception should have been thrown since a date is wrong (End date before start date).");
		}
		catch(OperationNotAllowedException e) {
			assertEquals(Error.START_DATE_ERROR, e.getError());
		}
		
		// Check the number of unavailabilities
		assertEquals(employee.getUnavailabilities().size(),0);

	}
	
	/** 
	 * Register Unavailability Systematic Test 
	 * Input data set E - Employee has tasks for the period of the unavailability
	 * 
	 * @author Filipe Silva
	 * @throws OperationNotAllowedException 
	 */
	@Test
	public void TestUnavailabilityE() throws OperationNotAllowedException {
		// Do the login
		Employee employee = app.employeeLogin("fili");

		// Check that there are no unavailabilities
		assertEquals(employee.getUnavailabilities().size(),0);
		
		// Set the date of a task.
		Task task = app.getProjects().get(0).getActivities().get(0).getTasks().get(0);
		Calendar startDate = new GregorianCalendar(2013, Calendar.MAY, 6);
		Calendar endDate = new GregorianCalendar(2013, Calendar.MAY, 10);
		task.setStartDate(startDate);
		task.setEndDate(endDate);
		
		// * Input data set E1
		// Employee has one task for the period of the unavailability
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
		
		// * Input data set E2
		// Employee has two tasks for the period of the unavailability
		
		// Set the date of a task.
		Task task2 = app.getProjects().get(0).getActivities().get(0).getTasks().get(0);
		startDate = new GregorianCalendar(2013, Calendar.MAY, 6);
		endDate = new GregorianCalendar(2013, Calendar.MAY, 10);
		task2.setStartDate(startDate);
		task2.setEndDate(endDate);
		
		// Set the task to the employee
		employee.addTask(task2);

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
	 * Register Unavailability Systematic Test 
	 * Input data set F - Employee has another unavailabilities for the period of the unavailability
	 * 
	 * @author Filipe Silva
	 * @throws OperationNotAllowedException 
	 */
	@Test
	public void TestUnavailabilityF() throws OperationNotAllowedException {
		// Do the login
		Employee employee = app.employeeLogin("fili");

		// Check that there are no unavailabilities
		assertEquals(employee.getUnavailabilities().size(),0);
		
		// Set the date of an unavailability
		Calendar startDate = new GregorianCalendar(2013, Calendar.MAY, 6);
		Calendar endDate = new GregorianCalendar(2013, Calendar.MAY, 10);
		app.createUnavailability(employee, startDate, endDate, "Unavailability 1");
		
		// * Input data set F1
		// Employee has one unavailability for the period of the unavailability

		// Try to add a new unavailability
		try {		
			app.createUnavailability(employee, startDate, endDate, "Unavailability 2");
			fail("An exception should have been thrown since employee is assigned an unavailability at that period.");
		}
		catch(OperationNotAllowedException e) {
			assertEquals(Error.EMPLOYEE_NOT_AVAILABLE, e.getError());
		}
		
		// Check the number of unavailabilities.
		assertEquals(employee.getUnavailabilities().size(),1);
		
		// * Input data set F2
		// Employee has two tasks for the period of the unavailability
		
		// Set the date of an unavailability
		startDate = new GregorianCalendar(2013, Calendar.APRIL, 29);
		endDate = new GregorianCalendar(2013, Calendar.MAY, 3);
		app.createUnavailability(employee, startDate, endDate, "Unavailability 3");
		
		// Change the end date for the unavailability get overlapped with the others two
		endDate = new GregorianCalendar(2013, Calendar.MAY, 10);

		// Try to add a new unavailability
		try {		
			app.createUnavailability(employee, startDate, endDate, "Unavailability 4");
			fail("An exception should have been thrown since employee is assigned a task at that period.");
		}
		catch(OperationNotAllowedException e) {
			assertEquals(Error.EMPLOYEE_NOT_AVAILABLE, e.getError());
		}
		
		// Check the number of unavailabilities.
		assertEquals(employee.getUnavailabilities().size(),2);	
	}
}
