package dtu.projman.app;

import static org.junit.Assert.*;

import org.junit.Test;
/*
 * @author Filipe Silva
 */
public class TestStateActivity extends SampleDataSetup {

	/** 
	 * Tests the scenario when an employee wants to change the state of an activity to ONGOING.
	 * <ol>
	 *  <li> The employee has to login with the username.
	 *  <li> The activity state is changed by calling setActivityState from the Activity class.
	 *  <li> If the project of the activity is not in ONGOING it state will be changed.
	 * </ol>
	 * @author Filipe Silva
	 */
	@Test
	public void testStateActivityONGOING() throws OperationNotAllowedException {
		// 1. Login and the check for it
		Employee emp = app.employeeLogin("fili");
		assertFalse(emp==null);
		
		// 2. Selection of the 1st project and the 1st activity for test.
		Project proj = app.getProjects().get(0);
		Activity act = proj.getActivities().get(0);
		
		// 3. Check that the activity and the project state is "Not started"
		assertEquals(State.NOTSTARTED,act.getState());
		assertEquals(State.NOTSTARTED,proj.getState());
		
		// 4. Change the activity state
		act.setState(State.ONGOING);
		
		// 5. Check that the activity and project state is "ongoing"
		assertEquals(State.ONGOING,act.getState());
		assertEquals(State.ONGOING,proj.getState());
		
		// 6. Make sure that all the tasks are in the state NOTSTARTED
		for(Task task: act.getTasks()){
			assertEquals(State.NOTSTARTED,task.getState());
		}
	}
	
	/** 
	 * Tests the scenario when an employee wants to change the state of an activity to NOTSTARTED, PAUSED or FINISHED.
	 * <ol>
	 *  <li> The employee has to login with the username.
	 *  <li> The project state is changed by calling setProjectState from the Project class.
	 * </ol>
	 * @author Filipe
	 */
	@Test
	public void testStateProjectRest() throws OperationNotAllowedException {
		// 1. Employee login and Check employee logged in
		app.employeeLogin("fili");
		assertEquals("fili", app.getEmployeeLoggedIn().getUsername());
		
		// 2. Selection of the 1st activity for test.
		Project proj = app.getProjects().get(0);
		Activity act = proj.getActivities().get(0);
		
		// 3. Check that the activity state is "Not started"
		assertEquals(State.NOTSTARTED, act.getState());
		
		// * State FINISHED
		// 4a. Change the activity state to FINISHED
		act.setState(State.FINISHED);
		
		// 4b. Check activity and tasks of it have state FINISHED
		assertEquals(State.FINISHED, act.getState());
		for (Task task : act.getTasks()) {
			assertEquals(State.FINISHED, task.getState());
		}
		
		// * State PAUSED
		// 5a. Change the activity state to PAUSED
		act.setState(State.PAUSED);
		
		// 5b. Check activity and tasks of it have state PAUSED
		assertEquals(State.PAUSED, act.getState());
		for (Task task : act.getTasks()) {
			assertEquals(State.PAUSED, task.getState());
		}
		
		// * State NOTSTARTED
		// 6a. Change the project state to NOTSTARTED
		act.setState(State.NOTSTARTED);
		
		// 6b. Check activity and tasks of it have state NOTSTARTED
		assertEquals(State.NOTSTARTED, act.getState());
		for (Task task : act.getTasks()) {
			assertEquals(State.NOTSTARTED, task.getState());
		}
	}

	
	
	/** 
	 * Tests the scenario when an employee wants to change an activity state but it's not logged in.
	 * <ol>
	 *  <li> The employee hasn't logged in.
	 *  <li> The employee tries to change the activity state but gets a message telling him that it's not allowed.
	 * </ol>
	 * @author Filipe Silva
	 */
	@Test
	public void testStateActivityLoggedOff() throws OperationNotAllowedException {
		
		Project proj = app.getProjects().get(0);
		Activity act = proj.getActivities().get(0);
		
		// 1. Do the log off and check it
		app.employeeLogoff();
		assertNull(app.getEmployeeLoggedIn());
		
		// 2. Check that the activity state is "not started"
		assertEquals(State.NOTSTARTED,act.getState());
		
		// 3. Try to change the activity state
		try{
			act.setState(State.ONGOING);
			fail("OperationNotAllowedException exception should have been thrown; username not logged in");
		} catch (OperationNotAllowedException e) {
			// Check that the exception thrown has the correct error message and knows which operation failed.
			assertEquals(Error.NO_EMPLOYEE_LOGGED_IN,e.getError());
		}
		
		// 4. Check that the activity and tasks states still NOTSTARTED
		for(Task task: act.getTasks()){
			assertEquals(State.NOTSTARTED,task.getState());
		}
		assertEquals(State.NOTSTARTED,act.getState());

	}
}
