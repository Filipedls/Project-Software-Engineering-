package dtu.projman.app;

import static org.junit.Assert.*;

import org.junit.Test;

/*
 * @author Filipe Silva
 */
public class TestSysStateFunc extends SampleDataSetup{

	/** 
	 * Systematic Tests for State Functionality
	 * State of a Project
	 *
	 * @author Filipe Silva
	 */
	@Test
	public void StateProject() throws OperationNotAllowedException {
		// Employee login and Check employee logged in
		app.employeeLogin("fili");
		assertEquals("fili", app.getEmployeeLoggedIn().getUsername());
		
		// Selection of the 1st project for test.
		Project proj = app.getProjects().get(0);
		
		// Set the states to State.FINISHED, to test the NOTSTARTED state
		proj.setState(State.FINISHED);
		assertEquals(State.FINISHED,proj.getState());
		
		for (Activity activity : proj.getActivities()) {
			activity.setState(State.FINISHED);
			assertEquals(State.FINISHED, activity.getState());
			for (Task task : activity.getTasks()){
				task.setState(State.FINISHED);
				assertEquals(State.FINISHED, task.getState());
			}
		}
		
		// * Input data set A1 (State.NOTSTARTED)
		// Change the project state
		proj.setState(State.NOTSTARTED);
		
		// Check that the project state
		assertEquals(State.NOTSTARTED,proj.getState());
		
		// Check that activities and tasks of project have the same state
		for (Activity activity : proj.getActivities()) {
			assertEquals(State.NOTSTARTED, activity.getState());
			for (Task task : activity.getTasks()) {
				assertEquals(State.NOTSTARTED, task.getState());
			}
		}
		
		// * Input data set A2 (State.PAUSED)
		// Change the project state
		proj.setState(State.PAUSED);
		
		// Check that the project state
		assertEquals(State.PAUSED,proj.getState());
		
		// Check that activities and tasks of project have the same state
		for (Activity activity : proj.getActivities()) {
			assertEquals(State.PAUSED, activity.getState());
			for (Task task : activity.getTasks()) {
				assertEquals(State.PAUSED, task.getState());
			}
		}
		
		// * Input data set A3 (State.FINISHED)
		// Change the project state
		proj.setState(State.FINISHED);
		
		// Check that the project state
		assertEquals(State.FINISHED,proj.getState());
		
		// Check that activities and tasks of project have the same state
		for (Activity activity : proj.getActivities()) {
			assertEquals(State.FINISHED, activity.getState());
			for (Task task : activity.getTasks()) {
				assertEquals(State.FINISHED, task.getState());
			}
		}
		
		// * Input data set B (State.ONGOING)
		// Change the project state
		proj.setState(State.ONGOING);
		
		// Check that the project state
		assertEquals(State.ONGOING,proj.getState());
		
		// Check that activities and tasks of project have the previous state (FINISHED)
		for (Activity activity : proj.getActivities()) {
			assertEquals(State.FINISHED, activity.getState());
			for (Task task : activity.getTasks()) {
				assertEquals(State.FINISHED, task.getState());
			}
		}
	}
	
	/** 
	 * Systematic Tests for State Functionality
	 * State of an Activity
	 *
	 * @author Filipe Silva
	 */
	@Test
	public void StateActvity() throws OperationNotAllowedException {
		// Employee login and Check employee logged in
		app.employeeLogin("fili");
		assertEquals("fili", app.getEmployeeLoggedIn().getUsername());
		
		// Selection of the 1st activity of the first project for test.
		Project proj = app.getProjects().get(0);
		Activity act = proj.getActivities().get(0);
		
		// Set the states to State.FINISHED, to test the NOTSTARTED state
		proj.setState(State.FINISHED);
		assertEquals(State.FINISHED,proj.getState());
		
		for (Activity activity : proj.getActivities()) {
			activity.setState(State.FINISHED);
			assertEquals(State.FINISHED, activity.getState());
			for (Task task : activity.getTasks()){
				task.setState(State.FINISHED);
				assertEquals(State.FINISHED, task.getState());
			}
		}
		
		// * Input data set A1 (State.NOTSTARTED)
		// Change the activity state
		act.setState(State.NOTSTARTED);
		
		// Check the activity state
		assertEquals(State.NOTSTARTED,act.getState());
		
		// Check that tasks of activity have the same state
		for (Task task : act.getTasks()) {
			assertEquals(State.NOTSTARTED, task.getState());
		}

		// * Input data set A2 (State.PAUSED)
		// Change the project state
		act.setState(State.PAUSED);
		
		// Check that the project state
		assertEquals(State.PAUSED,act.getState());
		
		// Check that tasks of activity have the same state
		for (Task task : act.getTasks()) {
			assertEquals(State.PAUSED, task.getState());
		}
		
		// * Input data set A3 (State.FINISHED)
		// Change the project state
		act.setState(State.FINISHED);
		
		// Check that the project state
		assertEquals(State.FINISHED,act.getState());
		
		// Check that tasks of activity have the same state
		for (Task task : act.getTasks()) {
			assertEquals(State.FINISHED, task.getState());
		}
		
		// * Input data set B1 (State.ONGOING)
		// Pre Condition: Change the project state to ONGOING
		proj.setState(State.ONGOING);
		assertEquals(State.ONGOING,proj.getState());
		
		// Change the activity state to ONGOING
		act.setState(State.ONGOING);
		assertEquals(State.ONGOING,act.getState());
		
		// Check that tasks of the activity have the previous state (FINISHED)
		for (Task task : act.getTasks()) {
			assertEquals(State.FINISHED, task.getState());
		}
		
		// Check the project state, should be the same
		assertEquals(State.ONGOING,proj.getState());
		
		// * Input data set C1 (State.ONGOING)
		// Pre condition: Project.state = State.NOTSTARTED
		proj.setState(State.NOTSTARTED);
		assertEquals(State.NOTSTARTED,proj.getState());
		
		// Change the activity state to ONGOING
		act.setState(State.ONGOING);
		assertEquals(State.ONGOING,act.getState());
		
		// Check that tasks of activity have the previous state (NOTSTARTED)
		for (Task task : act.getTasks()) {
			assertEquals(State.NOTSTARTED, task.getState());
		}
		
		// Check the project state changed to State.ONGOING
		assertEquals(State.ONGOING,proj.getState());

		// * Input data set C2 (State.ONGOING)
		// Pre condition: Project.state = State.PAUSED
		proj.setState(State.PAUSED);
		assertEquals(State.PAUSED,proj.getState());
		
		// Change the activity state to ONGOING
		act.setState(State.ONGOING);
		assertEquals(State.ONGOING,act.getState());
		
		// Check that tasks of activity have the previous state (PAUSED)
		for (Task task : act.getTasks()) {
			assertEquals(State.PAUSED, task.getState());
		}
		
		// Check the project state changed to State.ONGOING
		assertEquals(State.ONGOING,proj.getState());

		// * Input data set C3 (State.ONGOING)
		// Pre condition: Project.state = State.FINISHED
		proj.setState(State.FINISHED);
		assertEquals(State.FINISHED,proj.getState());
		
		// Change the activity state to ONGOING
		act.setState(State.ONGOING);
		assertEquals(State.ONGOING,act.getState());
		
		// Check that tasks of activity have the previous state (FINISHED)
		for (Task task : act.getTasks()) {
			assertEquals(State.FINISHED, task.getState());
		}
		
		// Check the project state changed to State.ONGOING
		assertEquals(State.ONGOING,proj.getState());
	}
	

	/** 
	 * Systematic Tests for State Functionality
	 * State of a Task
	 *
	 * @author Filipe Silva
	 */
	@Test
	public void StateTask() throws OperationNotAllowedException {
		// Employee login and Check employee logged in
		app.employeeLogin("fili");
		assertEquals("fili", app.getEmployeeLoggedIn().getUsername());
		
		// Selection of the 1st task of the first activity for test.
		Project proj = app.getProjects().get(0);
		Activity act = proj.getActivities().get(0);
		Task task = act.getTasks().get(0);
		
		// Set the states to State.FINISHED, to test the NOTSTARTED state
		proj.setState(State.FINISHED);
		assertEquals(State.FINISHED,proj.getState());
		
		for (Activity activity : proj.getActivities()) {
			activity.setState(State.FINISHED);
			assertEquals(State.FINISHED, activity.getState());
			for (Task taskAux : activity.getTasks()){
				taskAux.setState(State.FINISHED);
				assertEquals(State.FINISHED, taskAux.getState());
			}
		}
		
		// * Input data set A1 (State.NOTSTARTED)
		// Change the task state
		task.setState(State.NOTSTARTED);
		
		// Check the task state
		assertEquals(State.NOTSTARTED,task.getState());
		
		// Check that activity and project have the same state as previous
		assertEquals(State.FINISHED,act.getState());
		assertEquals(State.FINISHED,proj.getState());

		// * Input data set A2 (State.PAUSED)
		// Change the task state
		task.setState(State.PAUSED);
		
		// Check the task state
		assertEquals(State.PAUSED,task.getState());
		
		// Check that activity and project have the same state as previous
		assertEquals(State.FINISHED,act.getState());
		assertEquals(State.FINISHED,proj.getState());
		
		// * Input data set A3 (State.FINISHED)
		// Change the task state
		task.setState(State.FINISHED);
		
		// Check the task state
		assertEquals(State.FINISHED,task.getState());
		
		// Check that activity and project have the same state as previous
		assertEquals(State.FINISHED,act.getState());
		assertEquals(State.FINISHED,proj.getState());
		
		// * Input data set A4 (State.ONGOING)
		// Pre condition: Project.state and Activity.state= State.ONGOING
		proj.setState(State.ONGOING);
		assertEquals(State.ONGOING,proj.getState());
		act.setState(State.ONGOING);
		assertEquals(State.ONGOING,act.getState());
		
		// Change the task state
		task.setState(State.ONGOING);
		
		// Check the task state
		assertEquals(State.ONGOING,task.getState());
		
		// Check that activity and project have the same state as previous
		assertEquals(State.ONGOING,act.getState());
		assertEquals(State.ONGOING,proj.getState());
		
		// * Input data set B1 (State.ONGOING)
		// Pre Condition: Change the project state to NOTSTARTED
		proj.setState(State.NOTSTARTED);
		assertEquals(State.NOTSTARTED,proj.getState());
		
		// Change the task state to ONGOING
		task.setState(State.ONGOING);
		assertEquals(State.ONGOING,task.getState());
		
		// Check that the project's state has changed to ONGOING
		assertEquals(State.ONGOING, proj.getState());
		
		// * Input data set B2 (State.ONGOING)
		// Pre Condition: Change the project state to PAUSED
		proj.setState(State.PAUSED);
		assertEquals(State.PAUSED,proj.getState());
		
		// Change the task state to ONGOING
		task.setState(State.ONGOING);
		assertEquals(State.ONGOING,task.getState());
		
		// Check that the project's state has changed to ONGOING
		assertEquals(State.ONGOING, proj.getState());
				
		// * Input data set B3 (State.ONGOING)
		// Pre Condition: Change the project state to FINISHED
		proj.setState(State.FINISHED);
		assertEquals(State.FINISHED,proj.getState());
		
		// Change the task state to ONGOING
		task.setState(State.ONGOING);
		assertEquals(State.ONGOING,task.getState());
		
		// Check that the project's state has changed to ONGOING
		assertEquals(State.ONGOING, proj.getState());
		
		// * Input data set C1 (State.ONGOING)
		// Pre Condition: Change the activity state to NOTSTARTED
		act.setState(State.NOTSTARTED);
		assertEquals(State.NOTSTARTED,act.getState());
		
		// Change the task state to ONGOING
		task.setState(State.ONGOING);
		assertEquals(State.ONGOING,task.getState());
		
		// Check that the activity's state has changed to ONGOING
		assertEquals(State.ONGOING, act.getState());
		
		// * Input data set C2 (State.ONGOING)
		// Pre Condition: Change the activity state to PAUSED
		act.setState(State.PAUSED);
		assertEquals(State.PAUSED,act.getState());
		
		// Change the task state to ONGOING
		task.setState(State.ONGOING);
		assertEquals(State.ONGOING,task.getState());
		
		// Check that the activity's state has changed to ONGOING
		assertEquals(State.ONGOING, act.getState());
				
		// * Input data set C3 (State.ONGOING)
		// Pre Condition: Change the activity state to FINISHED
		act.setState(State.FINISHED);
		assertEquals(State.FINISHED,act.getState());
		
		// Change the task state to ONGOING
		task.setState(State.ONGOING);
		assertEquals(State.ONGOING,task.getState());
		
		// Check that the project's state has changed to ONGOING
		assertEquals(State.ONGOING, act.getState());
		
		
	}
}
