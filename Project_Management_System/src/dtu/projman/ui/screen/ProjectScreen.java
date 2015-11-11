package dtu.projman.ui.screen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;

import dtu.projman.app.Activity;
import dtu.projman.app.ProjManApp;
import dtu.projman.app.Project;
import dtu.projman.ui.ProjManUI;
import dtu.projman.ui.dialog.AssignManagerDialog;
import dtu.projman.ui.dialog.CreateActivityDialog;
import dtu.projman.ui.dialog.EditProjectCustomerNameDialog;
import dtu.projman.ui.dialog.EditProjectDescriptionDialog;
import dtu.projman.ui.dialog.EditProjectEndDateDialog;
import dtu.projman.ui.dialog.EditProjectNameDialog;
import dtu.projman.ui.dialog.EditProjectStartDateDialog;
import dtu.projman.ui.dialog.EditProjectStateDialog;
import dtu.projman.ui.dialog.EditProjectTypeDialog;
import dtu.projman.ui.dialog.SelectActivityDialog;

public class ProjectScreen extends Screen {

	Project project; 
	
	public ProjectScreen(ProjManUI ui, ProjManApp app, Project project) {
		super(ui, app);
		setProject(project);
	}

	private void setProject(Project project) {
		this.project = project;
	}
	
	@Override
	public void printMenu(PrintWriter out) throws IOException {
		out.println();
		out.println("PROJECT SCREEN");
		out.println("Project selected: ");
		out.println("Id: " + project.getId());
		out.println("Name: " + project.getName());
		out.println();
		out.println("1-	See project's details");
		out.println("2-	Edit name");
		out.println("3-	Edit type");
		out.println("4-	Edit customer name");
		out.println("5-	Edit description");
		out.println("6-	Edit start date");
		out.println("7-	Edit end date");
		out.println("8-	Edit state");
		out.println("9-	Assign manager");
		out.println("10-	List activities");
		out.println("11-	Create activity");
		out.println("12-	Select activity");
		out.println("13-	Back");
	
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
			new EditProjectNameDialog(getUi(), getApp(), in, out, project).runDialog();
			break;
		case 3:
			new EditProjectTypeDialog(getUi(), getApp(), in, out, project).runDialog();
			break;
		case 4:
			new EditProjectCustomerNameDialog(getUi(), getApp(), in, out, project).runDialog();
			break;
		case 5:
			new EditProjectDescriptionDialog(getUi(), getApp(), in, out, project).runDialog();
			break;
		case 6:
			new EditProjectStartDateDialog(getUi(), getApp(), in, out, project).runDialog();
			break;
		case 7:
			new EditProjectEndDateDialog(getUi(), getApp(), in, out, project).runDialog();
			break;
		case 8:
			new EditProjectStateDialog(getUi(), getApp(), in, out, project).runDialog();
			break;
		case 9: 
			new AssignManagerDialog(getUi(), getApp(), in, out, project).runDialog();
			break;
		case 10: 
			listActivities(out);
			break;
		case 11: 
			new CreateActivityDialog(getUi(), getApp(), in, out, project).runDialog();
			break;
		case 12: 
			nextScreen = new SelectActivityDialog(getUi(), getApp(), in, out).runDialog();			
			if (nextScreen!=null) {
				getUi().setScreen(nextScreen);
			}
			break;			
		case 13: 
			getUi().setScreen(new MainScreen(getUi(), getApp()));
			break;
		default:
			out.println("Please enter a valid element number to choose an operation.");
			break;
		}
		
		return false;
	}
	
	private void printDetails(PrintWriter out) {
		out.println("---Project Details---");
		
		out.println("Id: " + project.getId());
		
		out.println("Name: " + project.getName());
		
		if (project.getDescription()==null) {
			out.println("Description: ");
		} else {
			out.println("Description: " + project.getDescription() );
		}
		
		out.println("State: " + project.getState());
		
		if (project.getStartDate()==null) {
			out.println("Start Date: <not set>");
		} else {
			out.println("Start Date: " + project.getStartDate().get(Calendar.DAY_OF_MONTH)
				+ "/" + (project.getStartDate().get(Calendar.MONTH) + 1)
				+ "/" + project.getStartDate().get(Calendar.YEAR));
		}

		if (project.getEndDate()==null) {
			out.println("End Date: <not set>");
		} else {
			out.println("End Date: " + project.getEndDate().get(Calendar.DAY_OF_MONTH)
				+ "/" + (project.getEndDate().get(Calendar.MONTH) + 1)
				+ "/" + project.getEndDate().get(Calendar.YEAR));
		}
		
		out.println("Type: " + project.getType());
		
		out.println("Customer name: " + project.getCustomerName());
		
		if (project.getManager()==null) {
			out.println("Manager: <not set>");
		} else {
			out.println("Manager: " + project.getManager().getFullname());
		}		
	}

	
	private void listActivities(PrintWriter out) {
		List<Activity> activities = project.getActivities();
		if (activities.isEmpty()) {
			out.println("No activities found.");
		} else {
			out.println("Activity id \t Activity name");
			for (Activity activity : activities) {
				out.println(activity.getId() + "\t" + activity.getName());
			}
		}
	}

}
