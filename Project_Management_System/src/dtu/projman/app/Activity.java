package dtu.projman.app;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/*
 * @author Atakan Kaya
 */
public class Activity extends Base{

	private Project project;
	private List<Task> tasks = new ArrayList<Task>();
	

	public Activity(String name) throws OperationNotAllowedException {
		super(name);
	}

	public Project getProject() {
		return project;
	}

	/*
	 * @author Atakan Kaya
	 */
	public void setProject(Project project) throws OperationNotAllowedException {
		checkEmployeeLoggedIn();
		
		project.checkProjectIsInApp();
		
		this.project = project;
	}
	
	/*
	 * @author Atakan Kaya
	 */
	public List<Task> getTasks() {
		return tasks;
	}

	/*
	 * @author Atakan Kaya
	 */
	public List<Task> getMainTasks() {
		List<Task> mainTasks = new ArrayList<Task>();
		
		for (Task task : tasks) {
			if (task.getType()==TaskType.MAIN) {
				mainTasks.add(task);
			}
		}
		
		return mainTasks;
	}

	/*
	 * @author Atakan Kaya
	 */
	public List<Task> getHelpedTasks() {
		List<Task> helpedTasks = new ArrayList<Task>();
		
		for (Task task : tasks) {
			if (task.getType()==TaskType.HELPED) {
				helpedTasks.add(task);
			}
		}
		
		return helpedTasks;
	}
	
	/*
	 * @author Atakan Kaya
	 */
	public void addTask(Task task) throws OperationNotAllowedException {
		checkEmployeeLoggedIn();
		
		tasks.add(task);
		task.setActivity(this);
		task.setProject(this.getProject());
	}

	/*
	 * @author Atakan Kaya
	 */
	@Override
	public void setState(State state) throws OperationNotAllowedException {
		super.setState(state);
		
		if (state==State.NOTSTARTED || state==State.PAUSED || state==State.FINISHED) {
			for (Task task : tasks) {
				task.setState(state);
			}
		}
		
		if (state==State.ONGOING) {
			project.setState(State.ONGOING);
		}
	}

	/*
	 * @author Atakan Kaya
	 */
	protected void checkStartDateLegal(Calendar newStartDate) throws OperationNotAllowedException {
		for (Task task : tasks) {
			if (task.getStartDate()==null) {
				continue;
			}
			if (newStartDate.after(task.getStartDate())) {
				throw new OperationNotAllowedException(Error.ILLEGAL_ACTIVITY_START_DATE);
			}
		}
	}
	
	/*
	 * @author Atakan Kaya
	 */
	protected void checkEndDateLegal(Calendar newEndDate) throws OperationNotAllowedException {
		for (Task task : tasks) {
			if (task.getEndDate()==null) {
				continue;
			}
			if (newEndDate.before(task.getEndDate())) {
				throw new OperationNotAllowedException(Error.ILLEGAL_ACTIVITY_END_DATE);
			}
		}
	}

	/*
	 * @author Atakan Kaya
	 */
	public boolean isStartDateLegalForProject(Calendar newStartDate) {
		if (getProject().getStartDate()==null) {
			return true;
		}
		
		if (newStartDate.before(getProject().getStartDate())) {
			return false;
		} else {
			return true;
		}
	}
	
	/*
	 * @author Atakan Kaya
	 */
	public boolean isEndDateLegalForProject(Calendar newEndDate) {
		if (getProject().getEndDate()==null) {
			return true;
		}
		
		if (newEndDate.after(getProject().getEndDate())) {
			return false;
		} else {
			return true;
		}		
	}	
}
