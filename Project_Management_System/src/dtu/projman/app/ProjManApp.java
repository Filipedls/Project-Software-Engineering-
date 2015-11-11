package dtu.projman.app;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/*
 * @author Atakan Kaya
 */
public class ProjManApp {
	
	private Employee employeeLoggedIn;

	private List<Employee> employees = new ArrayList<Employee>();
	
	private List<Project> projects = new ArrayList<Project>();
	
	private int idNumber = 0;
	
	/*
	 * @author Atakan Kaya
	 */
	public List<Employee> getEmployees() {
		List<Employee> employeesUnModifiable = Collections.unmodifiableList(employees);
				
		return employeesUnModifiable;
	}

	
	/*
	 * @author Atakan Kaya
	 */
	public Employee getEmployeeByUsername(String username) {
		
		for (Employee emp : employees) {
			if (emp.getUsername().equals(username) ) {
				return emp;
			}
		}
		
		return null;
	}
	
	/*
	 * @author Atakan Kaya
	 */
	public List<Employee> getEmployeesByFullname(String fullname) {		
		List<Employee> emps = new ArrayList<Employee>();
		
		for (Employee emp : employees) {
			if (emp.getUsername().equals(fullname) ) {
				emps.add(emp);
			}
		}
		
		return emps;
	}
	
	// @author Filipe Silva
	public void registerEmployee(Employee emp) throws OperationNotAllowedException {
		if( getEmployeeByUsername(emp.getUsername())!=null ) {
			throw new OperationNotAllowedException(Error.USERNAME_EXISTS);
		}
		
		employees.add(emp);
		emp.setApp(this);
	}

	public List<Project> getProjects() {
		return projects;
	}
	
	/*
	 * @author Filipe Silva
	 */
	public Project createProject(String name, ProjectType type, String customerName) throws OperationNotAllowedException {
		if(employeeLoggedIn == null) {
			throw new OperationNotAllowedException(Error.NO_EMPLOYEE_LOGGED_IN);
		}
		
		Project project = new Project(name, type, customerName);
		
		project.setId(generateId());
		project.setApp(this);
		projects.add(project);
	
		return project;
	}

	/*
	 * @author Marc Thomsen
	 */
	public Activity createActivity(Project project, String name) throws OperationNotAllowedException {
		if(employeeLoggedIn == null) {
			throw new OperationNotAllowedException(Error.NO_EMPLOYEE_LOGGED_IN);
		}
		
		if (project==null || project.getApp()==null) {
			throw new OperationNotAllowedException(Error.PROJECT_NOT_REGISTERED);
		}
			
		Activity activity = new Activity(name);
		
		activity.setId(generateId());
		activity.setApp(this);
		project.addActivity(activity);
	
		return activity;
	}

	/*
	 * @author Atakan Kaya
	 */
	public Task createTask(Activity activity, String name) throws OperationNotAllowedException {
		if(employeeLoggedIn == null) {
			throw new OperationNotAllowedException(Error.NO_EMPLOYEE_LOGGED_IN);
		}
		
		if (activity==null || activity.getApp()==null) {
			throw new OperationNotAllowedException(Error.ACTIVITY_NOT_REGISTERED);
		}
			
		Task task = new Task(name, TaskType.MAIN);
		
		task.setId(generateId());
		task.setApp(this);
		activity.addTask(task);
	
		return task;	
	}

	/*
	 * @author Atakan Kaya
	 */
	public Project getProjectById(int id) {
		for (Project project : projects) {
			if (project.getId()==id) {
				return project;
			}
		}
		return null;
	}
	
	/*
	 * @author Atakan Kaya
	 */
	public Activity getActivityById(int id) {
		for (Project project : projects) {
			for (Activity activity : project.getActivities()) {
				if (activity.getId()==id) {
					return activity;
				}
			}
		}
		return null;
	}
	/*
	 * @author Atakan Kaya
	 */
	public Task getTaskById(int id) {
		for (Project project : projects) {
			for (Activity activity : project.getActivities()) {
				for (Task task : activity.getTasks()) {
					if (task.getId()==id) {
						return task;
					}
				}
			}
		}
		return null;
	}
	
	// @author Filipe Silva
	public Unavailability getUnavailabilityById(int id) {
		for (Employee employee : this.getEmployees()) {
			for (Unavailability unavail : employee.getUnavailabilities()) {
				if (unavail.getId()==id) {
					return unavail;
				}
			}
		}
			
		return null;
	}
	/*
	 * @author Marc Thomsen
	 */
	public Employee getEmployeeLoggedIn() {
		return employeeLoggedIn;
	}

	/*
	 * @author Marc Thomsen
	 */
	public Employee employeeLogin(String username) throws OperationNotAllowedException {
		if (employeeLoggedIn!=null) {
			throw new OperationNotAllowedException(Error.AN_EMPLOYEE_ALREADY_LOGGED_IN);
		}
		
		if (username==null || username.length()==0 || username.length()>4) {
			throw new OperationNotAllowedException(Error.USERNAME_ERROR);
		}
		
		employeeLoggedIn = this.getEmployeeByUsername(username);
		return employeeLoggedIn;
	}
	/*
	 * @author Marc Thomsen
	 */	
	public void employeeLogoff() {
		employeeLoggedIn = null;
	}
	
	private int generateId() {
		return ++idNumber;
	}
	
	// @author Filipe Silva
	public Unavailability createUnavailability(Employee employee, Calendar startDate, Calendar endDate, String description) throws OperationNotAllowedException {
		Unavailability unavailability = new Unavailability(employee, startDate, endDate, description);
		
		for(Task task: employee.getTasks()){
			if(!(task.getStartDate().after(endDate) || task.getEndDate().before(startDate))) {
				throw new OperationNotAllowedException(Error.EMPLOYEE_NOT_AVAILABLE);
			}	
		}
		
		for(Unavailability unavail: employee.getUnavailabilities()){
			if(!(unavail.getStartDate().after(endDate) || unavail.getEndDate().before(startDate))) {
				throw new OperationNotAllowedException(Error.EMPLOYEE_NOT_AVAILABLE);
			}	
		}
		
		unavailability.setId(generateId());
		employee.addUnavailability(unavailability);
		return unavailability;
	}
	/*
	 * @author Marc Thomsen
	 */
	public List<Project> getProjectsManagedByEmployeeLoggedIn() {
		List<Project> projectsOfManager = new ArrayList<Project>();
		
		for (Project project : projects) {
			if (project.getManager().equals(getEmployeeLoggedIn())) {
				projectsOfManager.add(project);
			}
		}
		
		return projectsOfManager;
	}
 }
