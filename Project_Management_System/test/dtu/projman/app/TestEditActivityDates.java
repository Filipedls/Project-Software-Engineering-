package dtu.projman.app;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

/*
 * @author Atakan Kaya
 */
public class TestEditActivityDates {

	ProjManApp app = new ProjManApp();
	Employee emp;
	Project project;
	Activity activity;
	Task task1, task2;
	
	@Before
	public void setUp() throws Exception {
		// Create employee		
		emp = new Employee("Filipe Silva", "filp", "filipesilva@gmail.com");
		
		// Register employees
		app.registerEmployee(emp);
		
		app.employeeLogin("filp");
		
		// Create project
		project = app.createProject("Project 1 - Test", ProjectType.EXTERNAL, "Microsoft");
		project.setStartDate(new GregorianCalendar(2013, Calendar.MARCH, 4));
		project.setEndDate(new GregorianCalendar(2013, Calendar.MAY, 24));
		
		// Create activity
		activity = app.createActivity(project, "Design");
		
		// Create tasks
		task1 = app.createTask(activity, "Test this functionality");
		task2 = app.createTask(activity, "Implement this functionality");
		
		task1.setStartDate(new GregorianCalendar(2013, Calendar.APRIL, 1));
		task1.setEndDate(new GregorianCalendar(2013, Calendar.MAY, 3));
		task2.setStartDate(new GregorianCalendar(2013, Calendar.APRIL, 15));
		task2.setEndDate(new GregorianCalendar(2013, Calendar.APRIL, 19));
	}

	@Test
	public void testStartLaterThanTask() {
		Calendar oldStartDate = activity.getStartDate();
		Calendar newStartDate = new GregorianCalendar(2013, Calendar.APRIL, 8);
		try {		
			activity.setStartDate(newStartDate);
			fail("An exception should have been thrown since 7 April 2013 is later than the start date of one of its tasks.");
		}
		catch(OperationNotAllowedException e) {
			assertEquals("Start date of the activity cannot be later than the start date of any of its tasks.", e.getMessage());
		}
		
		// Check that activity is not edited
		assertEquals(oldStartDate, activity.getStartDate());
		assertEquals(oldStartDate, app.getActivityById(activity.getId()).getStartDate());
	}
	
	@Test
	public void testEndEarlierThanTask() {
		Calendar oldEndDate = activity.getEndDate();
		Calendar newEndDate = new GregorianCalendar(2013, Calendar.APRIL, 26);
		try {		
			activity.setEndDate(newEndDate);
			fail("An exception should have been thrown since 26 April 2013 is earlier than the end date of one of its tasks.");
		}
		catch(OperationNotAllowedException e) {
			assertEquals("End date of the activity cannot be earlier than the end date of any of its tasks.", e.getMessage());
		}
		
		// Check that activity is not edited
		assertEquals(oldEndDate, activity.getEndDate());
		assertEquals(oldEndDate, app.getActivityById(activity.getId()).getEndDate());
	}
	
	@Test
	public void testStartEarlierThanProject() throws OperationNotAllowedException {
		Calendar newStartDate = new GregorianCalendar(2013, Calendar.FEBRUARY, 25);
		
		assertFalse(activity.isStartDateLegalForProject(newStartDate));
		
		activity.setStartDate(newStartDate);
		
		// Check that activity is edited
		assertEquals(newStartDate, activity.getStartDate());
		assertEquals(newStartDate, app.getActivityById(activity.getId()).getStartDate());		
		
		// Check if start date of the project is overridden by the new start date
		assertEquals(newStartDate, project.getStartDate());
		assertEquals(newStartDate, app.getProjectById(project.getId()).getStartDate());
	}

	@Test
	public void testEndLaterThanProject() throws OperationNotAllowedException {
		Calendar newEndDate = new GregorianCalendar(2013, Calendar.MAY, 31);
		
		assertFalse(activity.isEndDateLegalForProject(newEndDate));
		
		activity.setEndDate(newEndDate);
		
		// Check that activity is edited
		assertEquals(newEndDate, activity.getEndDate());
		assertEquals(newEndDate, app.getActivityById(activity.getId()).getEndDate());		
		
		// Check if end date of the project is overridden by the new end date
		assertEquals(newEndDate, project.getEndDate());
		assertEquals(newEndDate, app.getProjectById(project.getId()).getEndDate());
	}
}
