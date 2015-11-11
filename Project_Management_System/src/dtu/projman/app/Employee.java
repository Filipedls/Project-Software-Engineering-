package dtu.projman.app;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/*
 * @author Filipe Silva
 */
public class Employee {

	private String fullname;
	private String username;
	private String email;
	
	private List<Task> tasks = new ArrayList<Task>();
	private List<Unavailability> unavailList = new ArrayList<Unavailability>();
	
	private ProjManApp app;

	public Employee(String fullname, String username, String email) throws OperationNotAllowedException {
		setFullname(fullname);
		setUsername(username);
		setEmail(email);
	}

	public String getFullname() {
		return fullname;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public void setFullname(String fullname) throws OperationNotAllowedException {
		if( fullname == null || fullname.equals("") ) {
			throw new OperationNotAllowedException(Error.EMPTY_FIELD);
		}
		
		this.fullname = fullname;
	}

	public void setUsername(String username) throws OperationNotAllowedException {
		if (username==null || username.length()==0 || username.length()>4) {
			throw new OperationNotAllowedException(Error.USERNAME_ERROR);
		}
		
		this.username = username;
	}

	public void setEmail(String email) throws OperationNotAllowedException {
		if( email==null || email.equals("")) {
			throw new OperationNotAllowedException(Error.EMPTY_FIELD);
		}
		
		this.email = email;
	}
	
	public ProjManApp getApp() {
		return app;
	}
	
	public void setApp(ProjManApp app) {
		this.app = app;
	}
	
	protected void addTask(Task taskToAdd) {
		tasks.add(taskToAdd);
	}
	
	/*
	 * @author Atakan Kaya
	 */
	public boolean checkAvailability(Calendar startDate, Calendar endDate) {
		for (Unavailability unavailability : unavailList) {
			if ( !endDate.after(unavailability.getStartDate()) 
					|| !startDate.before(unavailability.getEndDate()) ) {
				continue;
			} else {
				return false;
			}
		}
		return true;
	}

	/*
	 * @author Atakan Kaya
	 */
	public void checkEmployeeIsInApp() throws OperationNotAllowedException {
		if (app!=null) {
			for (Employee employee : app.getEmployees()) {
				if (this.equals(employee)) {
					return;
				}
			}
		}
		
		throw new OperationNotAllowedException(Error.EMPLOYEE_NOT_REGISTERED);
	}

	/*
	 * @author Atakan Kaya
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
        if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}
	
		Employee employee = (Employee) obj;
		return this.getUsername().equals(employee.getUsername());
	}
	
	// @author Filipe Silva
	public void addUnavailability(Unavailability unavailability) {
		
		unavailList.add(unavailability);
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public List<Unavailability> getUnavailabilities() {
		return unavailList;
	}
	
	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
	
	/*
	 * @author Atakan Kaya
	 */	
	public double getTotalEstimatedHoursOfWorkForWeek(Calendar startDate) throws OperationNotAllowedException {
		if (startDate.get(Calendar.DAY_OF_WEEK)!=Calendar.MONDAY) {
			throw new OperationNotAllowedException(Error.START_DATE_NOT_MONDAY);
		}

		double total = 0;
		for (Task task : tasks) {
			total += task.getEstimated_no_hours();
		}
		
		return total;
	}
	
	/*
	 * @author Atakan Kaya
	 */
	public double getTotalWorkedHoursOfWorkForWeek(Calendar startDate) throws OperationNotAllowedException {
		if (startDate.get(Calendar.DAY_OF_WEEK)!=Calendar.MONDAY) {
			throw new OperationNotAllowedException(Error.START_DATE_NOT_MONDAY);
		}

		double total = 0;
		for (Task task : tasks) {
			total += task.getWorked_no_hours();
		}
		
		return total;		
	}
	
	/*
	 * @author Atakan Kaya
	 */
	public double getTotalRemainingHoursOfWorkForWeek(Calendar startDate) throws OperationNotAllowedException {
		if (startDate.get(Calendar.DAY_OF_WEEK)!=Calendar.MONDAY) {
			throw new OperationNotAllowedException(Error.START_DATE_NOT_MONDAY);
		}

		return getTotalEstimatedHoursOfWorkForWeek(startDate) - getTotalWorkedHoursOfWorkForWeek(startDate);
	}
}
