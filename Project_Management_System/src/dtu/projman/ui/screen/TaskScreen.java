package dtu.projman.ui.screen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;

import dtu.projman.app.Employee;
import dtu.projman.app.ProjManApp;
import dtu.projman.app.Task;
import dtu.projman.ui.ProjManUI;
import dtu.projman.ui.dialog.AssignEmployeeDialog;
import dtu.projman.ui.dialog.AssignHelperDialog;
import dtu.projman.ui.dialog.EditTaskDescriptionDialog;
import dtu.projman.ui.dialog.EditTaskEndDateDialog;
import dtu.projman.ui.dialog.EditTaskEstHoursDialog;
import dtu.projman.ui.dialog.EditTaskNameDialog;
import dtu.projman.ui.dialog.EditTaskStartDateDialog;
import dtu.projman.ui.dialog.EditTaskStateDialog;
import dtu.projman.ui.dialog.EditTaskWorkedHoursDialog;

public class TaskScreen extends Screen {

	Task task;
	
	public TaskScreen(ProjManUI ui, ProjManApp app, Task task) {
		super(ui, app);
		setTask(task);
	}

	public void setTask(Task task) {
		this.task = task;
	}
	
	@Override
	public void printMenu(PrintWriter out) throws IOException {
		out.println();
		out.println("TASK SCREEN");
		out.println("Task selected: ");
		out.println("Id: " + task.getId());
		out.println("Name: " + task.getName());
		out.println();
		out.println("1-	See task's details");
		out.println("2-	Edit name");
		out.println("3-	Edit description");
		out.println("4-	Edit start date");
		out.println("5-	Edit end date");
		out.println("6-	Edit state");
		out.println("7-	Edit expected number of hours");
		out.println("8-	Edit worked number of hours");
		out.println("9-	Assign employee to the task");
		out.println("10-	Assign helper to the task");
		out.println("11-	List helpers");
		out.println("12-	Back");
	}

	@Override
	public boolean processInput(String input, BufferedReader in, PrintWriter out)
			throws IOException {
		int code;
		try {
			code = Integer.parseInt(input);			
		} catch (NumberFormatException e) {
			out.println("Please enter a valid element number to choose an operation.");
			return false;
		}
		
		switch (code) {
		case 1: 
			printDetails(out);
			break;
		case 2:
			new EditTaskNameDialog(getUi(), getApp(), in, out, task).runDialog();
			break;
		case 3:
			new EditTaskDescriptionDialog(getUi(), getApp(), in, out, task).runDialog();
			break;
		case 4:
			new EditTaskStartDateDialog(getUi(), getApp(), in, out, task).runDialog();
			break;
		case 5:
			new EditTaskEndDateDialog(getUi(), getApp(), in, out, task).runDialog();
			break;
		case 6:
			new EditTaskStateDialog(getUi(), getApp(), in, out, task).runDialog();
			break;
		case 7: 
			new EditTaskEstHoursDialog(getUi(), getApp(), in, out, task).runDialog();
			break;
		case 8:
			new EditTaskWorkedHoursDialog(getUi(), getApp(), in, out, task).runDialog();
			break;
		case 9: 
			new AssignEmployeeDialog(getUi(), getApp(), in, out, task).runDialog();
			break;
		case 10: 
			new AssignHelperDialog(getUi(), getApp(), in, out, task).runDialog();
			break;
		case 11: 
			listHelpers(out);
			break;
		case 12: 
			getUi().setScreen(new ActivityScreen(getUi(), getApp(), task.getActivity()));			
			break;
		default:
			out.println("Please enter a valid element number to choose an operation.");
			break;
		}
		
		return false;	
	}

	private void printDetails(PrintWriter out) {
		out.println("---Task Details---");
		
		out.println("Id: " + task.getId());
		
		out.println("Name: " + task.getName());
		
		if (task.getDescription()==null) {
			out.println("Description: ");
		} else {
			out.println("Description: " + task.getDescription() );
		}
		
		out.println("State: " + task.getState());
		
		if (task.getStartDate()==null) {
			out.println("Start Date: <not set>");
		} else {
			out.println("Start Date: " + task.getStartDate().get(Calendar.DAY_OF_MONTH)
				+ "/" + (task.getStartDate().get(Calendar.MONTH) + 1)
				+ "/" + task.getStartDate().get(Calendar.YEAR));
		}

		if (task.getEndDate()==null) {
			out.println("End Date: <not set>");
		} else {
			out.println("End Date: " + task.getEndDate().get(Calendar.DAY_OF_MONTH)
				+ "/" + (task.getEndDate().get(Calendar.MONTH) + 1)
				+ "/" + task.getEndDate().get(Calendar.YEAR));
		}

		out.println("Project: " + task.getProject().getName() );
		
		out.println("Activity: " + task.getActivity().getName());
		
		if (task.getDeveloper()==null) {
			out.println("Developer: <Not set>");
		} else {
			out.println("Developer: " + task.getDeveloper().getFullname() );
		}
		
		if (task.getType()==null) {
			out.println("Type: <Not set>");
		} else {
			out.println("Type: " + task.getType() );
		}
		
		out.println("Estimated Number of Hours: " + task.getEstimated_no_hours());
		
		out.println("Worked Number of Hours: " + task.getWorked_no_hours());

		out.println("Helpers: ");
		
		if (task.getHelpers().isEmpty()) {
			out.println("No helpers.");
		} else {
			for(Employee helper : task.getHelpers()) {
				out.println("Helper name: " + helper.getFullname());
			}
		}
	}

	private void listHelpers(PrintWriter out) {
		List<Employee> helpers = task.getHelpers();
		if (helpers.isEmpty()) {
			out.println("No helper found for this task.");
		} else {
			out.println("User name \t Full name");
			for (Employee employee : helpers) {
				out.println(employee.getUsername() + "\t" + employee.getFullname());
			}
		}
	}

}
