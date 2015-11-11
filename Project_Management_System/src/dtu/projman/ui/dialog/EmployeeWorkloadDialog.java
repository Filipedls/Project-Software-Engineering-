package dtu.projman.ui.dialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import dtu.projman.app.Employee;
import dtu.projman.app.OperationNotAllowedException;
import dtu.projman.app.ProjManApp;
import dtu.projman.ui.ProjManUI;
import dtu.projman.ui.screen.Screen;

public class EmployeeWorkloadDialog extends Dialog {

	private Calendar startDate;
	
	public EmployeeWorkloadDialog(ProjManUI ui, ProjManApp app,
			BufferedReader in, PrintWriter out) {
		super(ui, app, in, out);
	}

	@Override
	public Screen runDialog() throws IOException {
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
		startDate = new GregorianCalendar();
		do {
			getOut().println("Enter start date to choose week (dd/mm/yyyy): ");
			try {
				startDate.setTime(sdf1.parse(getUi().readInput(getIn())));
				break;
			} catch (ParseException e1) {
				getOut().println("Invalid date");
				continue;
			}
		} while(true);	
	return null;
	}
	
	public void listWorkloadForAnEmployee(Employee employee) {
		try {
			double totalEstimated = employee.getTotalEstimatedHoursOfWorkForWeek(startDate);
			double totalWorked = employee.getTotalWorkedHoursOfWorkForWeek(startDate);
			double totalRemaining = employee.getTotalRemainingHoursOfWorkForWeek(startDate);
			
			getOut().println("Employee Name \t Total Estimated Hours \t Total Worked Hours \t Total Remaining Hours");
			getOut().println(employee.getFullname() + "\t"
							+ totalEstimated + "\t"
							+ totalWorked + "\t"
							+ totalRemaining);
		} catch (OperationNotAllowedException e) {
			getOut().println(e.getErrorDescription());
		}

	}
	public void listWorkloadForAllEmployees() {
		List<Employee> employees = getApp().getEmployees();
		
		if (employees.isEmpty()) {
			return;
		}
		
		for (Employee employee : employees) {
			try {
				double totalEstimated = employee.getTotalEstimatedHoursOfWorkForWeek(startDate);
				double totalWorked = employee.getTotalWorkedHoursOfWorkForWeek(startDate);
				double totalRemaining = employee.getTotalRemainingHoursOfWorkForWeek(startDate);
		
				getOut().println("Employee Name \t Total Estimated Hours \t Total Worked Hours \t Total Remaining Hours");
				getOut().println(employee.getFullname() + "\t"
								+ totalEstimated + "\t"
								+ totalWorked + "\t"
								+ totalRemaining);
			} catch (OperationNotAllowedException e) {
				getOut().println(e.getErrorDescription());
			}
		}
	}
	
}
