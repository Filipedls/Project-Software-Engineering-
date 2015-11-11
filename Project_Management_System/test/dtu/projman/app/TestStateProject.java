package dtu.projman.app;

import static org.junit.Assert.*;

import org.junit.Test;
/*
 * @author Filipe Silva
 */
public class TestStateProject extends SampleDataSetup {

	/** 
	 * Tests the scenario when an employee wants to change a project to the state ONGOING.
	 * <ol>
	 *  <li> The employee has to login with the username.
	 *  <li> The project state is changed by calling setProjectState from the Project class.
	 * </ol>
	 * @author Filipe Silva
	 */
	@Test
	public void testStateProjectONGOING() throws OperationNotAllowedException {
		// Employee login and Check employee logged in
		app.employeeLogin("fili");
		assertEquals("fili", app.getEmployeeLoggedIn().getUsername());
		
		// 2. Selection of the 1st project for test.
		Project proj = app.getProjects().get(0);
		
		// 3. Check that the project state is "Not started"
		assertEquals(State.NOTSTARTED,proj.getState());
		
		// 4. Change the project state
		proj.setState(State.ONGOING);
		
		// 5. Check that the project state is "ongoing"
		assertEquals(State.ONGOING,proj.getState());
	}
	
	/** 
	 * Tests the scenario when an employee wants to change a project to the state NOTSTARTED, PAUSED or FINISHED.
	 * <ol>
	 *  <li> The employee has to login with the username.
	 *  <li> The project state is changed by calling setProjectState from the Project class.
	 * </ol>
	 * @author Filipe Silva
	 */
	@Test
	public void testStateProjectRest() throws OperationNotAllowedException {
		// 1. Employee login and Check employee logged in
		app.employeeLogin("fili");
		assertEquals("fili", app.getEmployeeLoggedIn().getUsername());
		
		// 2. Selection of the 1st project for test.
		Project proj = app.getProjects().get(0);
		
		// 3. Check that the project state is "Not started"
		assertEquals(State.NOTSTARTED,proj.getState());
		
		// 4a. Change the project state to FINISHED
		proj.setState(State.FINISHED);
		
		// 4b. Check activites and tasks of it have state FINISHED
		assertEquals(State.FINISHED, proj.getState());
		for (Activity activity : proj.getActivities()) {
			assertEquals(State.FINISHED, activity.getState());
			for (Task task : activity.getTasks()) {
				assertEquals(State.FINISHED, task.getState());
			}
		}
		
		// 5a. Change the project state to PAUSED
		proj.setState(State.PAUSED);
		
		// 5b. Check activites and tasks of it have state PAUSED
		assertEquals(State.PAUSED, proj.getState());
		for (Activity activity : proj.getActivities()) {
			assertEquals(State.PAUSED, activity.getState());
			for (Task task : activity.getTasks()) {
				assertEquals(State.PAUSED, task.getState());
			}
		}

		// 6a. Change the project state to NOTSTARTED
		proj.setState(State.NOTSTARTED);
		
		// 6b. Check activites and tasks of it have state NOTSTARTED
		assertEquals(State.NOTSTARTED, proj.getState());
		for (Activity activity : proj.getActivities()) {
			assertEquals(State.NOTSTARTED, activity.getState());
			for (Task task : activity.getTasks()) {
				assertEquals(State.NOTSTARTED, task.getState());
			}
		}

		
	}
	
	
	/** 
	 * Tests the scenario when an employee wants to change a project state but it's not logged in.
	 * <ol>
	 *  <li> The employee hasn't logged in.
	 *  <li> The employee tries to change the project state but gets a message telling him that it's not allowed.
	 * </ol>
	 * @author Filipe Silva
	 */
	@Test
	public void testStateProjectLoggedOff() throws OperationNotAllowedException {
		
		Project proj = app.getProjects().get(0);
		
		// 1. Do the log off and check for it
		app.employeeLogoff();
		assertEquals(null,app.getEmployeeLoggedIn());
		
		// 2. Check that the project state is "not started"
		assertEquals(State.NOTSTARTED,proj.getState());
		
		// 3. Change the project state
		try{
			proj.setState(State.ONGOING);
			fail("OperationNotAllowedException exception should have been thrown; username not logged in");
		} catch (OperationNotAllowedException e) {
			// Check that the exception thrown has the correct error message and knows which operation failed.
			assertEquals(Error.NO_EMPLOYEE_LOGGED_IN, e.getError());
		}
		
		// 5. Check that the project activities and tasks states still "Not started"
		for(Activity act: proj.getActivities()){
			assertEquals(State.NOTSTARTED,act.getState());
			for(Task task: act.getTasks()){
				assertEquals(State.NOTSTARTED,task.getState());
			}
		}
		assertEquals(State.NOTSTARTED,proj.getState());
		
		// 6. Make sure that all the activities in the project are in the sate NOTSTARTED
		for(Activity act: proj.getActivities()){
			assertEquals(State.NOTSTARTED,act.getState());
			for(Task task: act.getTasks()){
				assertEquals(State.NOTSTARTED,task.getState());
			}
		}

	}
	
	/** 
	 * Tests the scenario when an employee wants to change a project state to null.
	 * <ol>
	 *  <li> The employee logged in.
	 *  <li> The employee tries to change the project state but gets a message telling him that it's not allowed.
	 * </ol>
	 * @author Filipe Silva
	 */
	@Test
	public void testStateProjectNull() throws OperationNotAllowedException {
		
		Project proj = app.getProjects().get(0);
		
		// 1. Do the log in and check for it
		app.employeeLogin("fili");
		
		// 2. Check that the project state is "not started"
		assertEquals(State.NOTSTARTED,proj.getState());
		
		// 3. Change the project state
		try{
			proj.setState(null);
			fail("OperationNotAllowedException exception should have been thrown; State null");
		} catch (OperationNotAllowedException e) {
			// Check that the exception thrown has the correct error message and knows which operation failed.
			assertEquals(Error.STATE_NULL, e.getError());
		}
		
		// 5. Check that the project activities and tasks states still NOTSTARTED
		for(Activity act: proj.getActivities()){
			assertEquals(State.NOTSTARTED,act.getState());
			for(Task task: act.getTasks()){
				assertEquals(State.NOTSTARTED,task.getState());
			}
		}
		assertEquals(State.NOTSTARTED,proj.getState());

	}
}
