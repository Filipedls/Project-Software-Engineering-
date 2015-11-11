package dtu.projman.app;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/*
 * @author Filipe Silva
 */
public class Project extends Base{
	
	private ProjectType type;
	private String customerName;
	private Employee manager;
	private List<Activity> activities = new ArrayList<Activity>();
			
	public Project(String name, ProjectType type, String customerName) throws OperationNotAllowedException {
		super(name);
		
		if (type==null) {
			throw new OperationNotAllowedException(Error.PROJECT_TYPE_ERROR);
		}
				
		this.type = type;
		if (type == ProjectType.EXTERNAL) {
			this.customerName = customerName;
		} else {
			this.customerName = "Software House A/S";
		}
		
		if (type==ProjectType.EXTERNAL && (customerName==null || customerName.equals("")) ) {
			throw new OperationNotAllowedException(Error.CUSTOMER_NAME_ERROR);
		}

	}
		
	public ProjectType getType() {
		return type;
	}
	
	public void setType(ProjectType type) throws OperationNotAllowedException {
		checkEmployeeLoggedIn();
		if (type == null) {
			throw new OperationNotAllowedException(Error.PROJECT_TYPE_ERROR);
		} 
		this.type = type;
	}
	
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) throws OperationNotAllowedException {
		checkEmployeeLoggedIn();
		
		this.customerName = customerName;
	}

	public Employee getManager(){
		return manager;
	}
	
	
	/*
	 * @author Marianne Louis-Hansen
	 */
	public void setManager(Employee employee) throws OperationNotAllowedException{
		checkEmployeeLoggedIn();
		
		if (employee==null) {
			throw new OperationNotAllowedException(Error.NAME_ERROR);
		}
		
		employee.checkEmployeeIsInApp();
		
		this.manager = employee;
	}
	
	/*
	 * @author Filipe Silva
	 */
	@Override
	public void setState(State state) throws OperationNotAllowedException {
		super.setState(state);
		
		if (state==State.NOTSTARTED || state==State.PAUSED || state==State.FINISHED) {
			for (Activity activity : activities) {
				activity.setState(state);
				
				for (Task task : activity.getTasks()) {
					task.setState(state);
				}
			}
		}
	}

	public List<Activity> getActivities() {
		return activities;
	}

	// @author Filipe Silva
	public void addActivity(Activity activity) throws OperationNotAllowedException {
		checkEmployeeLoggedIn();
		
		activity.setProject(this);
		activities.add(activity);
	}	
		
	public boolean isManager(Employee employee){
		// This may not give the best result. We should check by employee id.
		return manager.equals(employee);
	}
	
	/*
	 * @author Atakan Kaya
	 */
	protected void checkProjectIsInApp() throws OperationNotAllowedException {
		for (Project project : app.getProjects()) {
			if (this.equals(project)) {
				return;
			}
		}
		
		throw new OperationNotAllowedException(Error.PROJECT_NOT_REGISTERED);
	}

	/*
	 * @author Atakan Kaya
	 */
	protected void checkStartDateLegal(Calendar newStartDate) throws OperationNotAllowedException {
		for (Activity activity : activities) {
			if (activity.getStartDate()==null) {
				continue;
			}
			if (newStartDate.after(activity.getStartDate())) {
				throw new OperationNotAllowedException(Error.ILLEGAL_PROJECT_START_DATE);
			}
		}
	}
	
	/*
	 * @author Atakan Kaya
	 */
	protected void checkEndDateLegal(Calendar newEndDate) throws OperationNotAllowedException {
		for (Activity activity : activities) {
			if (activity.getEndDate()==null) {
				continue;
			}
			if (newEndDate.before(activity.getEndDate())) {
				throw new OperationNotAllowedException(Error.ILLEGAL_PROJECT_END_DATE);
			}
		}
	}
}
