package dtu.projman.app;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/*
 * @author Marianne Louis-Hansen
 */
public class TestSysAssignManager {
	
	ProjManApp app = new ProjManApp();
	Project project;
	Employee emp1;
	Employee emp2;
	Employee emp3;
	
	
	@Before
	public void setUp() throws Exception {
		
		emp1 = new Employee("Jane Doe", "jdoe", "jane@doe.com");
		app.registerEmployee(emp1);
		
		emp2 = new Employee("Mary Doe", "mdoe", "mary@doe.com");
		app.registerEmployee(emp2);
		app.employeeLogin("mdoe");
		
		project = app.createProject("project", ProjectType.EXTERNAL , "customerName");
		
		app.employeeLogoff();
	}
	
	//Test of username = null
	@Test
	public void TestInputDataSetA1() throws OperationNotAllowedException  {	
		app.employeeLogin("jdoe");
		try {
			project.setManager(null);
			fail("NAME_ERROR should have been thrown");
		} catch (OperationNotAllowedException e){
			assertEquals(Error.NAME_ERROR, e.getError());
		}
		assertNull(project.getManager());
	}	
	
	//Test of employee not registered 
	@Test
	public void TestInputDataA2() throws OperationNotAllowedException {
		app.employeeLogin("jdoe");
		Employee emp3 = new Employee("John Doe", "jodo", "john@doe.com");
		try{
			project.setManager(emp3);
			fail("EMPLOYEE_NOT_REGISTERED error should have been thrown");
		} catch (OperationNotAllowedException e){
			assertEquals(Error.EMPLOYEE_NOT_REGISTERED, e.getError());
		}
		assertNull(project.getManager());	
	}
	
	//Test of valid username, no previous project manager assigned
	@Test
	public void TestInputDataA3() throws OperationNotAllowedException {
		app.employeeLogin("mdoe");
		project.setManager(emp2);
		assertEquals(emp2, project.getManager());
		app.employeeLogoff();
	}
	//Test of valid username, overwriting previous project manager
	@Test
	public void TestInputDataA4() throws OperationNotAllowedException {
		app.employeeLogin("mdoe");
		project.setManager(emp2);
		assertEquals(emp2, project.getManager());
		project.setManager(emp1);
		assertEquals(emp1, project.getManager());
	}
	
}


