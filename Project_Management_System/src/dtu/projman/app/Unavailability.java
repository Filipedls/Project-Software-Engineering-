package dtu.projman.app;

import java.util.Calendar;
import java.util.GregorianCalendar;


/*
 * @author Filipe Silva
 */
public class Unavailability {
	
	private Calendar startDate;
	private Calendar endDate;
	private String description;
	private Employee employee;
	private int id = 0;

	public Unavailability(Employee employee, Calendar startDate, Calendar endDate, String description) throws OperationNotAllowedException{
		setDates(startDate, endDate);
		setDescription(description);
		setEmployee(employee);
	}

	public Calendar getStartDate() {
		return startDate;
	}

	public Calendar getEndDate() {
		return endDate;
	}
	
	private void setDates(Calendar startDate, Calendar endDate) throws OperationNotAllowedException {
		if (startDate==null || endDate==null) {
			throw new OperationNotAllowedException(Error.DATE_NOT_ASSIGNED);
		}
		
		if(startDate.after(endDate)) {
			throw new OperationNotAllowedException(Error.START_DATE_ERROR);
		}
		
		if (startDate.get(Calendar.DAY_OF_WEEK)!=Calendar.MONDAY) {
			throw new OperationNotAllowedException(Error.START_DATE_NOT_MONDAY);
		}
		
		if (endDate.get(Calendar.DAY_OF_WEEK)!=Calendar.FRIDAY) {
			throw new OperationNotAllowedException(Error.END_DATE_NOT_FRIDAY);
		}	
		
		this.startDate = new GregorianCalendar(
				startDate.get(Calendar.YEAR),
				startDate.get(Calendar.MONTH), 
				startDate.get(Calendar.DATE));
		this.endDate = new GregorianCalendar(
				endDate.get(Calendar.YEAR),
				endDate.get(Calendar.MONTH), 
				endDate.get(Calendar.DATE));
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Employee getEmployee() {
		return employee;
	}
	
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
}
