package dtu.projman.ui.screen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;

import dtu.projman.app.Activity;
import dtu.projman.app.ProjManApp;
import dtu.projman.app.Task;
import dtu.projman.ui.ProjManUI;
import dtu.projman.ui.dialog.CreateTaskDialog;
import dtu.projman.ui.dialog.EditActivityDescriptionDialog;
import dtu.projman.ui.dialog.EditActivityEndDateDialog;
import dtu.projman.ui.dialog.EditActivityNameDialog;
import dtu.projman.ui.dialog.EditActivityStartDateDialog;
import dtu.projman.ui.dialog.EditActivityStateDialog;
import dtu.projman.ui.dialog.SelectTaskDialog;

public class ActivityScreen extends Screen {

	Activity activity;
	
	public ActivityScreen(ProjManUI ui, ProjManApp app, Activity activity) {
		super(ui, app);
		setActivity(activity);
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}
	
	@Override
	public void printMenu(PrintWriter out) throws IOException {
		out.println();
		out.println("ACTIVITY SCREEN");
		out.println("Activity selected: ");
		out.println("Id: " + activity.getId());
		out.println("Name: " + activity.getName());
		out.println();
		out.println("1-	See activity's details");
		out.println("2-	Edit name");
		out.println("3-	Edit description");
		out.println("4-	Edit start date");
		out.println("5-	Edit end date");
		out.println("6-	Edit state");
		out.println("7-	List tasks");
		out.println("8-	Create task");
		out.println("9-	Select task");
		out.println("10-	Back");
	}

	@Override
	public boolean processInput(String input, BufferedReader in, PrintWriter out)
			throws IOException {

		int code = getSelection(input, out);
		
		Screen nextScreen = null;
		switch (code) {
		case 1: 
			printDetails(out);
			break;
		case 2:
			new EditActivityNameDialog(getUi(), getApp(), in, out, activity).runDialog();
			break;
		case 3:
			new EditActivityDescriptionDialog(getUi(), getApp(), in, out, activity).runDialog();
			break;
		case 4:
			new EditActivityStartDateDialog(getUi(), getApp(), in, out, activity).runDialog();
			break;
		case 5:
			new EditActivityEndDateDialog(getUi(), getApp(), in, out, activity).runDialog();
			break;
		case 6:
			new EditActivityStateDialog(getUi(), getApp(), in, out, activity).runDialog();
			break;
		case 7: 
			listTasks(out);
			break;
		case 8: 
			new CreateTaskDialog(getUi(), getApp(), in, out, activity).runDialog();
			break;
		case 9: 
			nextScreen = new SelectTaskDialog(getUi(), getApp(), in, out).runDialog();
			if (nextScreen!=null) {
				getUi().setScreen(nextScreen);
			}
			break;			
		case 10: 
			getUi().setScreen(new ProjectScreen(getUi(), getApp(), activity.getProject()));
			break;
		default:
			out.println("Please enter a valid element number to choose an operation.");
			break;
		}
		
		return false;
	}

	private void printDetails(PrintWriter out) {
		out.println("---Activity Details---");
		
		out.println("Id: " + activity.getId());
		
		out.println("Name: " + activity.getName());
		
		if (activity.getDescription()==null) {
			out.println("Description: ");
		} else {
			out.println("Description: " + activity.getDescription() );
		}
		
		out.println("State: " + activity.getState());
		
		if (activity.getStartDate()==null) {
			out.println("Start Date: <not set>");
		} else {
			out.println("Start Date: " + activity.getStartDate().get(Calendar.DAY_OF_MONTH)
				+ "/" + (activity.getStartDate().get(Calendar.MONTH) + 1)
				+ "/" + activity.getStartDate().get(Calendar.YEAR));
		}

		if (activity.getEndDate()==null) {
			out.println("End Date: <not set>");
		} else {
			out.println("End Date: " + activity.getEndDate().get(Calendar.DAY_OF_MONTH)
				+ "/" + (activity.getEndDate().get(Calendar.MONTH) + 1)
				+ "/" + activity.getEndDate().get(Calendar.YEAR));
		}
		
		out.println("Project: " + activity.getProject().getName());
	}
	
	private void listTasks(PrintWriter out) {
		List<Task> tasks = activity.getTasks();
		if (tasks.isEmpty()) {
			out.println("No tasks found.");
		} else {
			out.println("Task id \t Task name");
			for (Task task : tasks) {
				out.println(task.getId() + "\t" + task.getName());
			}
		}
	}

}
