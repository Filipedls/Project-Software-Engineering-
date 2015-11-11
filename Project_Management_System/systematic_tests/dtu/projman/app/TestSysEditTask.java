package dtu.projman.app;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

/*
 * @author Atakan Kaya
 */
public class TestSysEditTask {

	ProjManApp app;
	Project project;
	Activity activity;
	Task task;
	
	@Before
	public void setUp() throws Exception {
		app = new ProjManApp();
		
		Employee emp = new Employee("Atakan Kaya", "kaya", "kayaatakan@gmail.com");
		app.registerEmployee(emp);
		app.employeeLogin("kaya");
		
		project = app.createProject("TestingProject", ProjectType.INTERNAL, "");
		
		activity = app.createActivity(project, "Activity - 1");
		
		task = app.createTask(activity, "New Task");
	}

	@Test
	public void testDatasetA1() {
		String name = null;
		
		String oldName = task.getName();
		try {
			task.setName(name);
			fail("NAME_ERROR should have been thrown.");
		} catch (OperationNotAllowedException e) {
			assertEquals(Error.NAME_ERROR, e.getError());
		}
		
		assertEquals(oldName, task.getName());
	}

	@Test
	public void testDatasetA2() {
		String name = "";
		
		String oldName = task.getName();
		try {
			task.setName(name);
			fail("NAME_ERROR should have been thrown.");
		} catch (OperationNotAllowedException e) {
			assertEquals(Error.NAME_ERROR, e.getError());
		}
		
		assertEquals(oldName, task.getName());
	}
	
	@Test
	public void testDatasetA3() throws OperationNotAllowedException {
		String name = "Changed Name";
		
		task.setName(name);
		
		assertEquals(name, task.getName());
	}
	
	@Test
	public void testDatasetB() throws OperationNotAllowedException {
		String description = null;
		
		task.setDescription(description);
		
		assertEquals(description, task.getDescription());
	}
	
	@Test
	public void testDatasetC1() {
		double estimated_no_hours = -5;
		
		double oldEstimated_no_hours = task.getEstimated_no_hours();
		try {
			task.setEstimated_no_hours(estimated_no_hours);
			fail("ESTIMATED_NO_HOURS_ACCURACY_ERROR should have been thrown.");
		} catch (OperationNotAllowedException e) {
			assertEquals(Error.ESTIMATED_NO_HOURS_ACCURACY_ERROR, e.getError());
		}
		
		assertTrue(oldEstimated_no_hours == task.getEstimated_no_hours());
	}
	
	@Test
	public void testDatasetC2() {
		double estimated_no_hours = 10.2;
		
		double oldEstimated_no_hours = task.getEstimated_no_hours();
		try {
			task.setEstimated_no_hours(estimated_no_hours);
			fail("ESTIMATED_NO_HOURS_ACCURACY_ERROR should have been thrown.");
		} catch (OperationNotAllowedException e) {
			assertEquals(Error.ESTIMATED_NO_HOURS_ACCURACY_ERROR, e.getError());
		}
		
		assertTrue(oldEstimated_no_hours == task.getEstimated_no_hours());
	}
	
	@Test
	public void testDatasetC3() throws OperationNotAllowedException {
		double estimated_no_hours = 10.5;
		
		task.setEstimated_no_hours(estimated_no_hours);
		
		assertTrue(estimated_no_hours == task.getEstimated_no_hours());
	}
	
	@Test
	public void testDatasetD1() {
		double worked_no_hours = -5;
		
		double oldWorked_no_hours = task.getWorked_no_hours();
		try {
			task.setWorked_no_hours(worked_no_hours);
			fail("WORKED_NO_HOURS_ACCURACY_ERROR should have been thrown.");
		} catch (OperationNotAllowedException e) {
			assertEquals(Error.WORKED_NO_HOURS_ACCURACY_ERROR, e.getError());
		}
		
		assertTrue(oldWorked_no_hours == task.getWorked_no_hours());
	}
	
	@Test
	public void testDatasetD2() {
		double worked_no_hours = 10.2;
		
		double oldWorked_no_hours = task.getWorked_no_hours();
		try {
			task.setWorked_no_hours(worked_no_hours);
			fail("WORKED_NO_HOURS_ACCURACY_ERROR should have been thrown.");
		} catch (OperationNotAllowedException e) {
			assertEquals(Error.WORKED_NO_HOURS_ACCURACY_ERROR, e.getError());
		}
		
		assertTrue(oldWorked_no_hours == task.getWorked_no_hours());
	}
	
	@Test
	public void testDatasetD3() throws OperationNotAllowedException {
		double worked_no_hours = 10.5;
		
		task.setWorked_no_hours(worked_no_hours);
		
		assertTrue(worked_no_hours == task.getWorked_no_hours());
	}

	@Test
	public void testDatasetE1() {
		Calendar startDate = new GregorianCalendar(2013,Calendar.MAY,1);
		
		Calendar oldStartDate = task.getStartDate();
		try {
			task.setStartDate(startDate);
			fail("START_DATE_NOT_MONDAY should have been thrown.");
		} catch (OperationNotAllowedException e) {
			assertEquals(Error.START_DATE_NOT_MONDAY, e.getError());
		}
		
		assertTrue(oldStartDate == task.getStartDate());
	}
	
	@Test
	public void testDatasetE2() throws OperationNotAllowedException {
		Calendar startDate = new GregorianCalendar(2013,Calendar.APRIL,29);
		
		Calendar endDate = new GregorianCalendar(2013,Calendar.APRIL,26);
		
		task.setEndDate(endDate);
		assertEquals(endDate, task.getEndDate());
		
		Calendar oldStartDate = task.getStartDate();
		try {
			task.setStartDate(startDate);
			fail("START_DATE_ERROR should have been thrown.");
		} catch (OperationNotAllowedException e) {
			assertEquals(Error.START_DATE_ERROR, e.getError());
		}
		
		assertTrue(oldStartDate == task.getStartDate());
	}	

	@Test
	public void testDatasetE3() throws OperationNotAllowedException {
		Calendar startDate = new GregorianCalendar(2013,Calendar.APRIL,29);
		
		Calendar projectStartDate = new GregorianCalendar(2013,Calendar.MAY,6);
		
		project.setStartDate(projectStartDate);
		assertEquals(projectStartDate, project.getStartDate());
				
		assertFalse(task.isStartDateLegalForProject(startDate));
	}	
	
	@Test
	public void testDatasetE4() throws OperationNotAllowedException {
		Calendar startDate = new GregorianCalendar(2013,Calendar.APRIL,29);
		
		Calendar projectStartDate = new GregorianCalendar(2013,Calendar.MAY,6);
		
		project.setStartDate(projectStartDate);
		assertEquals(projectStartDate, project.getStartDate());
			
		assertFalse(task.isStartDateLegalForProject(startDate));
		
		task.setStartDate(startDate);
		
		assertEquals(startDate, project.getStartDate());
		assertEquals(startDate, activity.getStartDate());
	}	
	
	@Test
	public void testDatasetE5() throws OperationNotAllowedException {
		Calendar startDate = new GregorianCalendar(2013,Calendar.APRIL,29);
		
		Calendar activityStartDate = new GregorianCalendar(2013,Calendar.MAY,6);
		
		activity.setStartDate(activityStartDate);
		assertEquals(activityStartDate, activity.getStartDate());
		
		assertFalse(task.isStartDateLegalForActivity(startDate));
	}	
	
	@Test
	public void testDatasetE6() throws OperationNotAllowedException {
		Calendar startDate = new GregorianCalendar(2013,Calendar.APRIL,29);
		
		Calendar activityStartDate = new GregorianCalendar(2013,Calendar.MAY,6);
		
		Calendar oldProjectStartDate = project.getStartDate();
		
		activity.setStartDate(activityStartDate);
		assertEquals(activityStartDate, activity.getStartDate());
			
		assertFalse(task.isStartDateLegalForActivity(startDate));
		
		task.setStartDate(startDate);
		
		assertEquals(oldProjectStartDate, project.getStartDate());
		assertEquals(startDate, activity.getStartDate());
	}	
	
	@Test
	public void testDatasetE7() throws OperationNotAllowedException {
		Calendar startDate = new GregorianCalendar(2013,Calendar.APRIL,22);
		
		task.setStartDate(startDate);
		
		assertEquals(startDate, task.getStartDate());
	}		
	
	@Test
	public void testDatasetF1() {
		Calendar endDate = new GregorianCalendar(2013,Calendar.APRIL,15);
		
		Calendar oldEndDate = task.getEndDate();
		try {
			task.setEndDate(endDate);
			fail("END_DATE_NOT_FRIDAY should have been thrown.");
		} catch (OperationNotAllowedException e) {
			assertEquals(Error.END_DATE_NOT_FRIDAY, e.getError());
		}
		
		assertTrue(oldEndDate == task.getEndDate());
	}
	
	@Test
	public void testDatasetF2() throws OperationNotAllowedException {
		Calendar endDate = new GregorianCalendar(2013,Calendar.MAY,3);
		
		Calendar startDate = new GregorianCalendar(2013,Calendar.MAY,6);
		
		task.setStartDate(startDate);
		assertEquals(startDate, task.getStartDate());
		
		Calendar oldStartDate = task.getStartDate();
		try {
			task.setEndDate(endDate);
			fail("END_DATE_ERROR should have been thrown.");
		} catch (OperationNotAllowedException e) {
			assertEquals(Error.END_DATE_ERROR, e.getError());
		}
		
		assertTrue(oldStartDate == task.getStartDate());
	}	

	@Test
	public void testDatasetF3() throws OperationNotAllowedException {
		Calendar endDate = new GregorianCalendar(2013,Calendar.MAY,3);
		
		Calendar projectEndDate = new GregorianCalendar(2013,Calendar.APRIL,19);
		
		project.setEndDate(projectEndDate);
		assertEquals(projectEndDate, project.getEndDate());
				
		assertFalse(task.isEndDateLegalForProject(endDate));
	}	
	
	@Test
	public void testDatasetF4() throws OperationNotAllowedException {
		Calendar endDate = new GregorianCalendar(2013,Calendar.MAY,3);
		
		Calendar projectEndDate = new GregorianCalendar(2013,Calendar.APRIL,19);
		
		project.setEndDate(projectEndDate);
		assertEquals(projectEndDate, project.getEndDate());
			
		assertFalse(task.isEndDateLegalForProject(endDate));
		
		task.setEndDate(endDate);
		
		assertEquals(endDate, project.getEndDate());
		assertEquals(endDate, activity.getEndDate());
	}	
	
	@Test
	public void testDatasetF5() throws OperationNotAllowedException {
		Calendar endDate = new GregorianCalendar(2013,Calendar.MAY,3);
		
		Calendar activityEndDate = new GregorianCalendar(2013,Calendar.APRIL,19);
		
		activity.setEndDate(activityEndDate);
		assertEquals(activityEndDate, activity.getEndDate());
		
		assertFalse(task.isEndDateLegalForActivity(endDate));
	}	
	
	@Test
	public void testDatasetF6() throws OperationNotAllowedException {
		Calendar endDate = new GregorianCalendar(2013,Calendar.MAY,3);
		
		Calendar activityEndDate = new GregorianCalendar(2013,Calendar.APRIL,19);
		
		Calendar oldProjectEndDate = project.getEndDate();
		
		activity.setEndDate(activityEndDate);
		assertEquals(activityEndDate, activity.getEndDate());
			
		assertFalse(task.isEndDateLegalForActivity(endDate));
		
		task.setEndDate(endDate);
		
		assertEquals(oldProjectEndDate, project.getEndDate());
		assertEquals(endDate, activity.getEndDate());
	}	
	
	@Test
	public void testDatasetF7() throws OperationNotAllowedException {
		Calendar endDate = new GregorianCalendar(2013,Calendar.MAY,31);
		
		task.setEndDate(endDate);
		
		assertEquals(endDate, task.getEndDate());
	}	
}
