package dtu.projman.ui.screen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import dtu.projman.app.Employee;
import dtu.projman.app.ProjManApp;
import dtu.projman.app.Project;
import dtu.projman.app.Task;
import dtu.projman.ui.ProjManUI;
import dtu.projman.ui.dialog.CreateProjectDialog;
import dtu.projman.ui.dialog.EmployeeWorkloadDialog;
import dtu.projman.ui.dialog.RegisterEmployeeDialog;
import dtu.projman.ui.dialog.RegisterUnavailabilityDialog;
import dtu.projman.ui.dialog.SelectEmployeeDialog;
import dtu.projman.ui.dialog.SelectProjectDialog;
import dtu.projman.ui.dialog.SelectTaskDialog;

public class MainScreen extends Screen {

	public MainScreen(ProjManUI ui, ProjManApp app) {
		super(ui, app);
	}

	@Override
	public void printMenu(PrintWriter out) throws IOException {
		out.println();
		out.println("MAIN SCREEN");
		out.println("1-	List my tasks");
		out.println("2-	List projects that I am managing");
		out.println("3-	Register an unavailability for me");
		out.println("4-	List the workload of employees");
		out.println("5-	List all projects");
		out.println("6-	List all employees");
		out.println("7-	Create project");
		out.println("8-	Register employee");
		out.println("9-	Select project");
		out.println("10-	Select task");
		out.println("11-	Select employee");
		out.println("12-	Logout");
		
	}

	@Override
	public boolean processInput(String input, BufferedReader in, PrintWriter out)
			throws IOException {
		int code = getSelection(input, out);
		
		Screen nextScreen = null;
		
		switch (code) {
		case 1:
			listTasksOfEmployeeLoggedIn(out);
			break;
		case 2:
			listProjects(getApp().getProjectsManagedByEmployeeLoggedIn(), out);
			break;
		case 3:
			new RegisterUnavailabilityDialog(getUi(), getApp(), in, out, getApp().getEmployeeLoggedIn()).runDialog();
			break;
		case 4:
			EmployeeWorkloadDialog ewDialog = new EmployeeWorkloadDialog(getUi(), getApp(), in, out);
			ewDialog.runDialog();
			ewDialog.listWorkloadForAllEmployees();
			break;
		case 5:
			listProjects(getApp().getProjects(), out);
			break;
		case 6:
			listAllEmployees(out);			
			break;
		case 7:
			new CreateProjectDialog(getUi(), getApp(), in, out).runDialog();
			break;
		case 8:
			new RegisterEmployeeDialog(getUi(), getApp(), in, out).runDialog();
			break;
		case 9:
			nextScreen = new SelectProjectDialog(getUi(), getApp(), in, out).runDialog();
			if (nextScreen!=null) {
				getUi().setScreen(nextScreen);
			}
			break;
		case 10: 
			nextScreen = new SelectTaskDialog(getUi(), getApp(), in, out).runDialog();
			if (nextScreen!=null) {
				getUi().setScreen(nextScreen);
			}
			break;
		case 11:
			nextScreen = new SelectEmployeeDialog(getUi(), getApp(), in, out).runDialog();
			if (nextScreen!=null) {
				getUi().setScreen(nextScreen);
			}
			break;
		case 12:
			getApp().employeeLogoff();
			getUi().setScreen(new LoginScreen(getUi(), getApp()));
			break;
		default:
			out.println("Please enter a valid element number to choose an operation.");
			break;
		}

		return false;
	}

	private void listAllEmployees(PrintWriter out) {
		List<Employee> employees = getApp().getEmployees();
		if (employees.isEmpty()) {
			out.println("No employee found.");
		} else {
			out.println("Username \t Full Name  \t Email address");
			for (Employee employee : employees) {
				out.println(employee.getUsername() + "\t" + employee.getFullname() + "\t"+ employee.getEmail() );
			}
		}
	}

	private void listProjects(List<Project> projects, PrintWriter out) {
		if (projects.isEmpty()) {
			out.println("No projects found.");
		} else {
			out.println("Project id \t Project name");
			for (Project project : projects) {
				out.println(project.getId() + "\t" + project.getName());
			}
		}
	}
	
	private void listTasksOfEmployeeLoggedIn(PrintWriter out) {
		List<Task> tasks = getApp().getEmployeeLoggedIn().getTasks();
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
