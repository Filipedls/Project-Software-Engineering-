package dtu.projman.app;

import static org.junit.Assert.*;

import org.junit.Test;


/*
 * @author Filipe Silva
 */
public class TestStateTask extends SampleDataSetup {

	/** 
	 * Tests the scenario when an employee wants to change the sate of a Task
	 * <ol>
	 *  <li> The employee has to login with the username.
	 *  <li> The task state is changed by calling setProjectState from the Project class.
	 * </ol>
	 * @author Filipe
	 */
	@Test
	public void StateTask() throws OperationNotAllowedException {
		// 1. Login and the check for it
		Employee emp = app.employeeLogin("fili");
		assertFalse(emp==null);
		
		// 2. Selection of the 1st project, the 1st activity and the first task to test.
		Project proj = app.getProjects().get(0);
		Activity act = proj.getActivities().get(0);
		Task task = act.getTasks().get(0);
		
		// 3. Check that the task state is NOTSTARTED
		assertEquals(State.NOTSTARTED,task.getState());
		
		// * State ONGOING
		// 4a. Change the task state
		task.setState(State.ONGOING);
		
		// 4b. Check that the task state is ONGOING
		assertEquals(State.ONGOING,task.getState());
		
		// * State FINISHED
		// 5a. Change the task state
		task.setState(State.FINISHED);
		
		// 5b. Check that the task state is FINISHED
		assertEquals(State.FINISHED, task.getState());
		
		// * State PAUSED
		// 6a. Change the task state
		task.setState(State.PAUSED);
		
		// 6b. Check that the task state is PAUSED
		assertEquals(State.PAUSED,task.getState());
		
		// * State NOTSTARTED
		// 7a. Change the task state
		task.setState(State.NOTSTARTED);
		
		// 7b. Check that the task state is NOTSTARTED
		assertEquals(State.NOTSTARTED,task.getState());
	}
	
	
	/** 
	 * Tests the scenario when an employee wants to change the sate of a Task but it's not logged in.
	 * <ol>
	 *  <li> The employee hasn't logged in.
	 *  <li> The employee tries to change the task state but gets a message telling him that it's not allowed.
	 * </ol>
	 * @author Filipe
	 */
	@Test
	public void testStateTaskLoggedOff() throws OperationNotAllowedException {
		
		Project proj = app.getProjects().get(0);
		Activity act = proj.getActivities().get(0);
		Task task = act.getTasks().get(0);
		
		// 1. Do the log off and check for it
		app.employeeLogoff();
		assertEquals(null,app.getEmployeeLoggedIn());
		
		// 2. Check that the task state is "not started"
		assertEquals(State.NOTSTARTED,task.getState());
		
		// 3. Try to change the task state
		try{
			task.setState(State.ONGOING);
			fail("OperationNotAllowedException exception should have been thrown; username not logged in");
		} catch (OperationNotAllowedException e) {
			// Check that the exception thrown has the correct error message and knows which operation failed.
			assertEquals(Error.NO_EMPLOYEE_LOGGED_IN,e.getError());
		}
		
		// 5. Check that the tasks states still "Not started"
		assertEquals(State.NOTSTARTED,task.getState());

	}
}