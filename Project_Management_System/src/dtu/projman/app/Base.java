package dtu.projman.app;

import java.util.Calendar;
import java.util.GregorianCalendar;

/*
 * @author Filipe Silva
 */
abstract public class Base {

	protected int id = 0;
	protected String name;
	protected String description;
	protected Calendar startDate;
	protected Calendar endDate;
	protected State state;
	protected ProjManApp app;
	
	public Base(String name) throws OperationNotAllowedException {
		if (name==null || name.equals("")) {
			throw new OperationNotAllowedException(Error.NAME_ERROR);
		}
			
		this.name = name;
		
		this.state = State.NOTSTARTED;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) throws OperationNotAllowedException {
		checkEmployeeLoggedIn();
		
		if (name==null || name.equals("")) {
			throw new OperationNotAllowedException(Error.NAME_ERROR);
		}
		
		this.name=name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) throws OperationNotAllowedException {
		checkEmployeeLoggedIn();
		
		this.description = description;
	}
	
	/*
	 * @author Atakan Kaya
	 */
	public Calendar getStartDate() {
		// Solve Encapsulation issues
		return startDate;
	}
	
	/*
	 * @author Atakan Kaya
	 */
	public void setStartDate(Calendar newStartDate) throws OperationNotAllowedException {
		checkEmployeeLoggedIn();
		
		if(endDate!=null && newStartDate.after(endDate)) {
			throw new OperationNotAllowedException(Error.START_DATE_ERROR);
		}
		
		if (newStartDate.get(Calendar.DAY_OF_WEEK)!=Calendar.MONDAY) {
			throw new OperationNotAllowedException(Error.START_DATE_NOT_MONDAY);
		}

		if (this instanceof Project) {
			((Project)this).checkStartDateLegal(newStartDate);
		} else if (this instanceof Activity) {
			Activity activity = (Activity)this;
			activity.checkStartDateLegal(newStartDate);
			if (!activity.isStartDateLegalForProject(newStartDate)) {
				activity.getProject().setStartDate(newStartDate);
			}
		}
		else if (this instanceof Task) {
			Task task = (Task)this;
			if (!task.isStartDateLegalForProject(newStartDate)) {
				task.getActivity().setStartDate(newStartDate);
				task.getProject().setStartDate(newStartDate);
			}
			if (!task.isStartDateLegalForActivity(newStartDate)) {
				task.getActivity().setStartDate(newStartDate);
			}
		}

		startDate = new GregorianCalendar(
				newStartDate.get(Calendar.YEAR),
				newStartDate.get(Calendar.MONTH), 
				newStartDate.get(Calendar.DATE));
	}
	
	/*
	 * @author Atakan Kaya
	 */
	public Calendar getEndDate() {
		// Solve Encapsulation issues
		return endDate;
	}
	
	/*
	 * @author Atakan Kaya
	 */
	public void setEndDate(Calendar newEndDate) throws OperationNotAllowedException {
		checkEmployeeLoggedIn();
		
		if(startDate!=null && newEndDate.before(startDate)) {
			throw new OperationNotAllowedException(Error.END_DATE_ERROR);
		}

		if (newEndDate.get(Calendar.DAY_OF_WEEK)!=Calendar.FRIDAY) {
			throw new OperationNotAllowedException(Error.END_DATE_NOT_FRIDAY);
		}
		
		if (this instanceof Project) {
			((Project)this).checkEndDateLegal(newEndDate);
		} else if (this instanceof Activity) {
			Activity activity = (Activity)this;
			activity.checkEndDateLegal(newEndDate);
			if (!activity.isEndDateLegalForProject(newEndDate)) {
				activity.getProject().setEndDate(newEndDate);
			}
		}
		else if (this instanceof Task) {
			Task task = (Task)this;
			if (!task.isEndDateLegalForProject(newEndDate)) {
				task.getActivity().setEndDate(newEndDate);
				task.getProject().setEndDate(newEndDate);
			}
			if (!task.isEndDateLegalForActivity(newEndDate)) {
				task.getActivity().setEndDate(newEndDate);
			}
		}
		
		// calendar.clear() ??? clear hours and minutes
		endDate = new GregorianCalendar(
				newEndDate.get(Calendar.YEAR),
				newEndDate.get(Calendar.MONTH), 
				newEndDate.get(Calendar.DATE));
	}
	
	public State getState() {
		return state;
	}
	
	// @author Filipe Silva
	public void setState(State newState) throws OperationNotAllowedException {
		checkEmployeeLoggedIn();
		
		if(newState==null) {
			throw new OperationNotAllowedException(Error.STATE_NULL);
		}
		
		state = newState;
	}
	
	public ProjManApp getApp() {
		return app;
	}
	
	public void setApp(ProjManApp app) {
		this.app = app;
	}

	/*
	 * @author Atakan Kaya
	 */
	protected void checkEmployeeLoggedIn() throws OperationNotAllowedException {
		if(app!=null && app.getEmployeeLoggedIn()==null) {
			throw new OperationNotAllowedException(Error.NO_EMPLOYEE_LOGGED_IN);
		}
	}
	
	/*
	 * @author Atakan Kaya
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
        if (obj == null ||
        	!( (this instanceof Project && obj instanceof Project)
        			|| (this instanceof Activity && obj instanceof Activity) 
        			|| (this instanceof Task && obj instanceof Task) ) ) {
			return false;
		}
	
		Base base = (Base) obj;
		return this.getId()==base.getId();
		
	}
}
