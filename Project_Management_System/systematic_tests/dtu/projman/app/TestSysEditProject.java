package dtu.projman.app;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;
/*
 * @author Marianne Louis-Hansen
 */
public class TestSysEditProject {

	ProjManApp app = new ProjManApp();
	Project project;
	String oldName = "Project";
	ProjectType oldType = ProjectType.EXTERNAL;
	String oldCustomerName = "Microsoft";
	Employee emp;
	Calendar oldStartDate = new GregorianCalendar(2013, Calendar.MAY, 06);
	Calendar oldEndDate = new GregorianCalendar(2013, Calendar.MAY, 10);

	@Before
	public void setUp() throws Exception {

		emp = new Employee("Jane Doe", "jdo", "jane@doe.com");
		app.registerEmployee(emp);
		app.employeeLogin("jdo");
		project = app.createProject(oldName, oldType, oldCustomerName);
		project.setStartDate(oldStartDate);
		project.setEndDate(oldEndDate);
	}

	// Test for project name = null
	@Test
	public void TestInputDataSetA1() throws OperationNotAllowedException {
		String nullName = null;
		try {
			project.setName(nullName);
			fail("NAME_ERROR should have been thrown.");
		} catch (OperationNotAllowedException e) {
			assertEquals(Error.NAME_ERROR, e.getError());
		}
		assertEquals(oldName, project.getName());
	}

	// Test for project name = "";
	@Test
	public void TestInputDataSetA2() throws OperationNotAllowedException {
		String emptyName = "";
		try {
			project.setName(emptyName);
			fail("NAME_ERROR should have been thrown.");
		} catch (OperationNotAllowedException e) {
			assertEquals(Error.NAME_ERROR, e.getError());
		}
		assertEquals(oldName, project.getName());

	}

	// Test for valid name
	@Test
	public void TestInputDatasetA3() throws OperationNotAllowedException {
		String newName = "ProjMan";
		project.setName(newName);
		
		assertEquals(newName, project.getName());
	
	}
	//Test for invalid project type
	@Test
	public void TestInputDatasetB1() throws OperationNotAllowedException {
		ProjectType type = null;
		try {
			project.setType(type);
			fail("PROJECT_TYPE_ERROR should have been thrown.");
		} catch(OperationNotAllowedException e) {
			assertEquals(Error.PROJECT_TYPE_ERROR, e.getError());
		}
		assertEquals(oldType, project.getType());
	}
	
	//Test for valid project type
	@Test
	public void TestInputDataSetB2() throws OperationNotAllowedException {
		ProjectType type = ProjectType.INTERNAL;
		project.setType(type);
		assertEquals(type, project.getType());
	}
	
	//Test for invalid start date
	@Test
	public void TestInputDataSetC1() throws OperationNotAllowedException {
		Calendar newStartDate = new GregorianCalendar(2013, Calendar.MAY,07);
		try {
			project.setStartDate(newStartDate);
			fail("START_DATE_NOT_MONDAY should have been thrown");
		} catch (OperationNotAllowedException e) {
			assertEquals(Error.START_DATE_NOT_MONDAY, e.getError());
		}
		assertEquals(oldStartDate, project.getStartDate());
	}
	//Test for valid start date
	@Test
	public void TestInputDataSetC2() throws OperationNotAllowedException {
		Calendar newStartDate = new GregorianCalendar(2013, Calendar.APRIL,29);
		project.setStartDate(newStartDate);
		assertEquals(newStartDate, project.getStartDate());
	}
	//Test for invalid end date
	@Test
	public void TestInputDataSetD1() throws OperationNotAllowedException {
		Calendar newEndDate = new GregorianCalendar(2013, Calendar.MAY,12);
		try {
			project.setEndDate(newEndDate);
			fail("END_DATE_NOT_FRIDAY should have been thrown");	
		} catch (OperationNotAllowedException e){
			assertEquals(Error.END_DATE_NOT_FRIDAY, e.getError());
		}
		assertEquals(oldEndDate, project.getEndDate());
	}
	//Test for valid end date
	@Test
	public void TestInputDataSetD2() throws OperationNotAllowedException {
		Calendar newEndDate = new GregorianCalendar(2013, Calendar.MAY,10);
		project.setEndDate(newEndDate);
		assertEquals(newEndDate, project.getEndDate());
	}
	//Test for invalid project state
	@Test
	public void TestInputDataSetE1() throws OperationNotAllowedException {
		
	}
	 
}

