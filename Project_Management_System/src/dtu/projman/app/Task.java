package dtu.projman.app;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/*
 * @author Atakan Kaya
 */
public class Task extends Base{

	private Project project;
	private Activity activity;
	
	private TaskType type;
	private double estimated_no_hours = 0;
	private double worked_no_hours = 0;
	
	private Employee developer;
	private List<Employee> helpers = new ArrayList<Employee>();
	
	public Task(String name, TaskType type) throws OperationNotAllowedException {
		super(name);
		
		if (type == null) {
			throw new OperationNotAllowedException(Error.TASK_TYPE_ERROR);
		}

		this.type = type;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) throws OperationNotAllowedException {
		checkEmployeeLoggedIn();
		
		this.project = project;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) throws OperationNotAllowedException {
		checkEmployeeLoggedIn();
		
		this.activity = activity;
	}

	/*
	 * @author Atakan Kaya
	 */
	public Employee getDeveloper(){
		return developer;
	}

	// @author Marianne Louis-Hansen
	public void assignDeveloper(Employee developer) throws OperationNotAllowedException{
		checkEmployeeLoggedIn();
		
		developer.checkEmployeeIsInApp();
		
		int tasksOverlapped = 0;
		
		if (startDate==null || endDate==null) {
			throw new OperationNotAllowedException(Error.ASSIGN_DEVELOPER_ERROR_DATE);
		}
		
		for(Task task: developer.getTasks()) {
			if(!(task.getStartDate().after(endDate) || task.getEndDate().before(startDate))) {
				tasksOverlapped++;
			}
		}
		
		if (tasksOverlapped >= 20) {
			throw new OperationNotAllowedException(Error.ASSIGN_DEVELOPER_ERROR_TASKSNUMBER);
		}
		
		for(Unavailability unavail: developer.getUnavailabilities()) {
			if(!(unavail.getStartDate().after(endDate) || unavail.getEndDate().before(startDate))) {
				throw new OperationNotAllowedException(Error.ASSIGN_DEVELOPER_ERROR_UNAVAILABILITY);
			}
		}
		
		for(Employee helper: helpers){
			if(developer==helper) {
				throw new OperationNotAllowedException(Error.ASSIGN_DEVELOPER_ERROR_HELPER);
			}
		}
		
		this.developer = developer;
		developer.addTask(this);
	}

	/*
	 * @author Marc Thomsen
	 */
	public List<Employee> getHelpers() {
		return helpers;
	}

	/*
	 * @author Marc Thomsen
	 */
	public void assignHelper(Employee helper, double helper_estimated_no_hours) throws OperationNotAllowedException {
		checkEmployeeLoggedIn();
		
		helper.checkEmployeeIsInApp();
		
		if (startDate==null || endDate==null) {
			throw new OperationNotAllowedException(Error.ASSIGN_DEVELOPER_ERROR_DATE);
		}
		
		if (helper == developer) {
			throw new OperationNotAllowedException(Error.ASSIGN_HELPER_DEVELOPER);
		}
		
		for(Employee employee: helpers){
			if(helper==employee) {
				throw new OperationNotAllowedException(Error.ASSIGN_DEVELOPER_ALREADY_AS_HELPER);
			}
		}
	
		helpers.add(helper);
		
		Task helpedTask = new Task(this.getName(), TaskType.HELPED);
		helpedTask.setProject(this.getProject());
		helpedTask.setActivity(this.getActivity());
		helpedTask.setApp(app);
		helpedTask.setDescription(this.getDescription());
		helpedTask.setState(this.getState());
		helpedTask.setEstimated_no_hours(helper_estimated_no_hours);
		helpedTask.setStartDate(this.getStartDate());
		helpedTask.setEndDate(this.getEndDate());
		
		helper.addTask(helpedTask);
		activity.addTask(helpedTask);
	}
	
	public TaskType getType() {
		return type;
	}
	
	/*
	 * @author Atakan Kaya
	 */
	public double getEstimated_no_hours() {
		return estimated_no_hours;
	}
	
	/*
	 * @author Atakan Kaya
	 */
	public void setEstimated_no_hours(double estimated_no_hours) throws OperationNotAllowedException {
		checkEmployeeLoggedIn();

		if (!checkHalfHourAccuracy(estimated_no_hours)) {
			throw new OperationNotAllowedException(Error.ESTIMATED_NO_HOURS_ACCURACY_ERROR);
		}	

		this.estimated_no_hours = estimated_no_hours;
	}

	/*
	 * @author Atakan Kaya
	 */
	public double getWorked_no_hours() {
		return worked_no_hours;
	}

	/*
	 * @author Atakan Kaya
	 */
	public void setWorked_no_hours(double worked_no_hours) throws OperationNotAllowedException {
		checkEmployeeLoggedIn();
		
		if (!checkHalfHourAccuracy(worked_no_hours)) {
			throw new OperationNotAllowedException(Error.WORKED_NO_HOURS_ACCURACY_ERROR);
		}

		this.worked_no_hours = worked_no_hours;
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
	public boolean isStartDateLegalForActivity(Calendar newStartDate) {
		if (getActivity().getStartDate()==null) {
			return true;
		}
		
		if (newStartDate.before(getActivity().getStartDate())) {
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
	
	/*
	 * @author Atakan Kaya
	 */
	public boolean isEndDateLegalForActivity(Calendar newEndDate) {
		if (getActivity().getEndDate()==null) {
			return true;
		}
		
		if (newEndDate.after(getActivity().getEndDate())) {
			return false;
		} else {
			return true;
		}		
	}	
	/*
	 * @author Atakan Kaya
	 */
	private boolean checkHalfHourAccuracy(double value){
		return ( value>=0 && (value == Math.ceil(value) || value+0.5 == Math.ceil(value)) );
	}
	
	// @author Filipe Silva
	@Override
	public void setState(State state) throws OperationNotAllowedException {
		super.setState(state);
		
		if (state==State.ONGOING){
			project.setState(State.ONGOING);
			activity.setState(State.ONGOING);
		}
	}
	
	// @author Filipe Silva
	public boolean doesStateNeedOverrideForProject(State newState){
		if(newState==State.ONGOING) {
			if(project.getState()==State.NOTSTARTED) {
				return true;
			}
		}
		
		return false;
	}
	
	// @author Filipe Silva
	public boolean doesStateNeedOverrideForActivity(State newState){
		if(newState==State.ONGOING) {
			if(activity.getState()==State.NOTSTARTED) {
				return true;
			}
		}
		
		return false;
	}
}
