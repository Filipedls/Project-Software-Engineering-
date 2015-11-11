package dtu.projman.app;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

/*
 * @author Atakan Kaya
 */
public class TestEditTaskDates {
	
	ProjManApp app = new ProjManApp();
	Employee emp;
	Project project;
	Activity activity;
	Task task;
	
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
		activity.setStartDate(new GregorianCalendar(2013, Calendar.APRIL, 1));
		activity.setEndDate(new GregorianCalendar(2013, Calendar.APRIL, 26));
		
		// Create task
		task = app.createTask(activity, "Test this functionality");	
	}

	@Test
	public void testStartEarlierThanProject() throws OperationNotAllowedException {
		Calendar newStartDate = new GregorianCalendar(2013, Calendar.FEBRUARY, 25);
		
		assertFalse(task.isStartDateLegalForProject(newStartDate));
		
		task.setStartDate(newStartDate);
		
		// Check that task is edited
		assertEquals(newStartDate, task.getStartDate());
		assertEquals(newStartDate, app.getTaskById(task.getId()).getStartDate());		

		// Check if start date of the activity is overridden by the new start date
		assertEquals(newStartDate, activity.getStartDate());
		assertEquals(newStartDate, app.getActivityById(activity.getId()).getStartDate());
		
		// Check if start date of the project is overridden by the new start date
		assertEquals(newStartDate, project.getStartDate());
		assertEquals(newStartDate, app.getProjectById(project.getId()).getStartDate());
	}

	@Test
	public void testEndLaterThanProject() throws OperationNotAllowedException {
		Calendar newEndDate = new GregorianCalendar(2013, Calendar.MAY, 31);
		
		assertFalse(task.isEndDateLegalForProject(newEndDate));
		
		task.setEndDate(newEndDate);
		
		// Check that task is edited
		assertEquals(newEndDate, task.getEndDate());
		assertEquals(newEndDate, app.getTaskById(task.getId()).getEndDate());		

		// Check if end date of the activity is overridden by the new end date
		assertEquals(newEndDate, activity.getEndDate());
		assertEquals(newEndDate, app.getActivityById(activity.getId()).getEndDate());
		
		// Check if end date of the project is overridden by the new end date
		assertEquals(newEndDate, project.getEndDate());
		assertEquals(newEndDate, app.getProjectById(project.getId()).getEndDate());
	}

	@Test
	public void testStartEarlierThanActivity() throws OperationNotAllowedException {
		Calendar newStartDate = new GregorianCalendar(2013, Calendar.MARCH, 18);
		
		assertFalse(task.isStartDateLegalForActivity(newStartDate));
		
		task.setStartDate(newStartDate);
		
		// Check that task is edited
		assertEquals(newStartDate, task.getStartDate());
		assertEquals(newStartDate, app.getTaskById(task.getId()).getStartDate());		

		// Check if start date of the activity is overridden by the new start date
		assertEquals(newStartDate, activity.getStartDate());
		assertEquals(newStartDate, app.getActivityById(activity.getId()).getStartDate());
	}

	@Test
	public void testEndLaterThanActivity() throws OperationNotAllowedException {
		Calendar newEndDate = new GregorianCalendar(2013, Calendar.MAY, 17);
		
		assertFalse(task.isEndDateLegalForActivity(newEndDate));
		
		task.setEndDate(newEndDate);
		
		// Check that task is edited
		assertEquals(newEndDate, task.getEndDate());
		assertEquals(newEndDate, app.getTaskById(task.getId()).getEndDate());		

		// Check if end date of the activity is overridden by the new end date
		assertEquals(newEndDate, activity.getEndDate());
		assertEquals(newEndDate, app.getActivityById(activity.getId()).getEndDate());
	}
}
