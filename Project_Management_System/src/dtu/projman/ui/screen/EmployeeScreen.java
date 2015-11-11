package dtu.projman.ui.screen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;

import dtu.projman.app.Employee;
import dtu.projman.app.ProjManApp;
import dtu.projman.app.Task;
import dtu.projman.app.Unavailability;
import dtu.projman.ui.ProjManUI;
import dtu.projman.ui.dialog.EditEmployeeEmailDialog;
import dtu.projman.ui.dialog.EditEmployeeNameDialog;
import dtu.projman.ui.dialog.EditEmployeeUsernameDialog;
import dtu.projman.ui.dialog.EmployeeWorkloadDialog;
import dtu.projman.ui.dialog.RegisterUnavailabilityDialog;
import dtu.projman.ui.dialog.SelectTaskDialog;

public class EmployeeScreen extends Screen {

	Employee employee;
	
	public EmployeeScreen(ProjManUI ui, ProjManApp app, Employee employee) {
		super(ui, app);
		setEmployee(employee);
	}
	
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	@Override
	public void printMenu(PrintWriter out) throws IOException {
		out.println();
		out.println("EMPLOYEE SCREEN");
		out.println("Employee selected: ");
		out.println("Employee name: " + employee.getFullname());
		out.println("Employee username: " + employee.getUsername());
		out.println("Employee email address: " + employee.getEmail());
		out.println("1-	Edit full name");
		out.println("2-	Edit user name");
		out.println("3-	Edit email address");
		out.println("4-	List employee’s tasks");
		out.println("5-	List employee’s unavailabilities");
		out.println("6-	See employee’s workload");
		out.println("7-	Select task");
		out.println("8-	Register unavailability");
		out.println("9-	Back");
	}

	@Override
	public boolean processInput(String input, BufferedReader in, PrintWriter out)
			throws IOException {

		int code = getSelection(input, out);
		
		Screen nextScreen = null;
		switch (code) {
		case 1:
			new EditEmployeeNameDialog(getUi(), getApp(), in, out, employee).runDialog();
			break;
		case 2:
			new EditEmployeeUsernameDialog(getUi(), getApp(), in, out, employee).runDialog();
			break;
		case 3:
			new EditEmployeeEmailDialog(getUi(), getApp(), in, out, employee).runDialog();
			break;
		case 4:			
			listTasksOfEmployee(out);
			break;
		case 5:
			listUnavailabilities(out);
			break;
		case 6: 
			EmployeeWorkloadDialog ewDialog = new EmployeeWorkloadDialog(getUi(), getApp(), in, out);
			ewDialog.runDialog();
			ewDialog.listWorkloadForAnEmployee(employee);
			break;
		case 7: 
			nextScreen = new SelectTaskDialog(getUi(), getApp(), in, out).runDialog();
			if (nextScreen!=null) {
				getUi().setScreen(nextScreen);
			}
			break;
		case 8:
			new RegisterUnavailabilityDialog(getUi(), getApp(), in, out, employee).runDialog();
			break;
		case 9: 
			getUi().setScreen(new MainScreen(getUi(), getApp()));
			break;
		default:
			out.println("Please enter a valid element number to choose an operation.");
			break;
		}
		
		return false;
	
	}
	
	private void listUnavailabilities(PrintWriter out) {
		out.println("Start Date\tEnd Date\tReason");
		
		if (employee.getUnavailabilities().isEmpty()) {
			out.println("No unavailabilities.");
		} else {
			for (Unavailability unavail : employee.getUnavailabilities()) {
				out.println(unavail.getStartDate().get(Calendar.DAY_OF_MONTH)
						+ "/" + (unavail.getStartDate().get(Calendar.MONTH) + 1)
						+ "/" + unavail.getStartDate().get(Calendar.YEAR)
						+ "\t"
						+ unavail.getEndDate().get(Calendar.DAY_OF_MONTH)
						+ "/" + (unavail.getEndDate().get(Calendar.MONTH) + 1)
						+ "/" + unavail.getEndDate().get(Calendar.YEAR)
						+ "\t"
						+ unavail.getDescription() );
			}
		}
	}

	private void listTasksOfEmployee(PrintWriter out) {
		List<Task> tasks = employee.getTasks();
		if (tasks.isEmpty()) {
			out.println("No tasks found.");
		} else {
			out.println("Id \t " +
					"Name \t " +
					"Activity \t" +
					"Project \t" +
					"Estimated No Hours \t" +
					"Worked No Hours \t");
			for (Task task : tasks) {
				out.println(task.getId() + "\t" 
							+ task.getName() + "\t"
							+ task.getActivity().getName() + "\t"
							+ task.getProject().getName() + "\t"
							+ task.getEstimated_no_hours() + "\t"
							+ task.getWorked_no_hours() + "\t");
			}
		}
	}

}
