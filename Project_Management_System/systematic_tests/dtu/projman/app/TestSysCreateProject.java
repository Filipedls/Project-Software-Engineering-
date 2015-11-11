package dtu.projman.app;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/*
 * @author Atakan Kaya
 */
public class TestSysCreateProject {

	ProjManApp app;
	
	@Before
	public void setUp() throws Exception {
		app = new ProjManApp();
		
		Employee employee = new Employee("Atakan Kaya", "kaya", "kayaatakan@gmail.com");
		app.registerEmployee(employee);
		
		app.employeeLogin("kaya");
	}

	@Test
	public void testInputDatasetA1() {
		String name = null;
		ProjectType type = ProjectType.EXTERNAL;
		String customerName = "Microsoft";

		Project project = null;
		try {
			 project = app.createProject(name, type, customerName);
			fail("NAME_ERROR should have been thrown.");
		} catch (OperationNotAllowedException e) {
			assertEquals(Error.NAME_ERROR, e.getError());
		}

		assertNull(project);
	}

	@Test
	public void testInputDatasetA2() {
		String name = "";
		ProjectType type = ProjectType.EXTERNAL;
		String customerName = "Microsoft";
		
		Project project = null;
		try {
			 project = app.createProject(name, type, customerName);
			fail("NAME_ERROR should have been thrown.");
		} catch (OperationNotAllowedException e) {
			assertEquals(Error.NAME_ERROR, e.getError());
		}

		assertNull(project);
	}
	
	@Test
	public void testInputDatasetB() {
		String name = "ProjMan";
		ProjectType type = null;
		String customerName = "Microsoft";
		
		Project project = null;
		try {
			 project = app.createProject(name, type, customerName);
			fail("PROJECT_TYPE_ERROR should have been thrown.");
		} catch (OperationNotAllowedException e) {
			assertEquals(Error.PROJECT_TYPE_ERROR, e.getError());
		}

		assertNull(project);
	}
	
	@Test
	public void testInputDatasetC1() throws OperationNotAllowedException {
		String name = "ProjMan";
		ProjectType type = ProjectType.INTERNAL;
		String customerName = "";
		
		Project project = app.createProject(name, type, customerName);
		assertNotNull(project);
		assertEquals(name, project.getName());
		assertEquals(type, project.getType());
		assertEquals("Software House A/S", project.getCustomerName());
	}
	
	@Test
	public void testInputDatasetC2() {
		String name = "ProjMan";
		ProjectType type = ProjectType.EXTERNAL;
		String customerName = null;
		
		Project project = null;
		try {
			 project = app.createProject(name, type, customerName);
			fail("CUSTOMER_NAME_ERROR should have been thrown.");
		} catch (OperationNotAllowedException e) {
			assertEquals(Error.CUSTOMER_NAME_ERROR, e.getError());
		}

		assertNull(project);
	}
	
	@Test
	public void testInputDatasetC3() {
		String name = "ProjMan";
		ProjectType type = ProjectType.EXTERNAL;
		String customerName = "";
		
		Project project = null;
		try {
			 project = app.createProject(name, type, customerName);
			fail("CUSTOMER_NAME_ERROR should have been thrown.");
		} catch (OperationNotAllowedException e) {
			assertEquals(Error.CUSTOMER_NAME_ERROR, e.getError());
		}

		assertNull(project);
	}
	
	@Test
	public void testInputDatasetD1() {
		String name = "";
		ProjectType type = null;
		String customerName = "Microsoft";
		
		Project project = null;
		try {
			 project = app.createProject(name, type, customerName);
			fail("NAME_ERROR should have been thrown.");
		} catch (OperationNotAllowedException e) {
			assertEquals(Error.NAME_ERROR, e.getError());
		}

		assertNull(project);
	}
	
	@Test
	public void testInputDatasetD2() {
		String name = "";
		ProjectType type = ProjectType.EXTERNAL;
		String customerName = "";
		
		Project project = null;
		try {
			 project = app.createProject(name, type, customerName);
			fail("NAME_ERROR should have been thrown.");
		} catch (OperationNotAllowedException e) {
			assertEquals(Error.NAME_ERROR, e.getError());
		}

		assertNull(project);
	
	}
	
	@Test
	public void testInputDatasetD3() {
		String name = "ProjMan";
		ProjectType type = null;
		String customerName = "";
		
		Project project = null;
		try {
			 project = app.createProject(name, type, customerName);
			fail("PROJECT_TYPE_ERROR should have been thrown.");
		} catch (OperationNotAllowedException e) {
			assertEquals(Error.PROJECT_TYPE_ERROR, e.getError());
		}

		assertNull(project);
	}
	
	@Test
	public void testInputDatasetE() {
		String name = "";
		ProjectType type = null;
		String customerName = "";
		
		Project project = null;
		try {
			 project = app.createProject(name, type, customerName);
			fail("NAME_ERROR should have been thrown.");
		} catch (OperationNotAllowedException e) {
			assertEquals(Error.NAME_ERROR, e.getError());
		}

		assertNull(project);
	}
	
	@Test
	public void testInputDatasetF1() throws OperationNotAllowedException {
		String name = "ProjMan";
		ProjectType type = ProjectType.INTERNAL;
		String customerName = "Microsoft";
		
		Project project = app.createProject(name, type, customerName);
		assertNotNull(project);
		assertEquals(name, project.getName());
		assertEquals(type, project.getType());
		assertEquals("Software House A/S", project.getCustomerName());
	}
	
	@Test
	public void testInputDatasetF2() throws OperationNotAllowedException {
		String name = "ProjMan";
		ProjectType type = ProjectType.EXTERNAL;
		String customerName = "Microsoft";
			
		Project project = app.createProject(name, type, customerName);
		assertNotNull(project);
		assertEquals(name, project.getName());
		assertEquals(type, project.getType());
		assertEquals(customerName, project.getCustomerName());
	}
}
