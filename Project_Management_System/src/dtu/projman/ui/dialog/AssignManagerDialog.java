package dtu.projman.ui.dialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import dtu.projman.app.Employee;
import dtu.projman.app.OperationNotAllowedException;
import dtu.projman.app.ProjManApp;
import dtu.projman.app.Project;
import dtu.projman.ui.ProjManUI;
import dtu.projman.ui.screen.Screen;

public class AssignManagerDialog extends Dialog {

	private Project project;
	
	public AssignManagerDialog(ProjManUI ui, ProjManApp app, BufferedReader in,
			PrintWriter out, Project project) {
		super(ui, app, in, out);
		setProject(project);
	}

	@Override
	public Screen runDialog() throws IOException {
		if (getProject().getManager()!=null) {
			getOut().println("An employee has already been assigned as a manager. ");
			getOut().println("Continuing will overwrite the manager. ");
			getOut().println("Do you want to continue? (y/n)");
			
			if (!askYesNoQuestion()) {
				return null;
			}
		}
		
		getOut().println("Enter new manager's user name: ");
		String managerUserName = getUi().readInput(getIn());
		
		try {
			Employee newManager = getApp().getEmployeeByUsername(managerUserName);
			getProject().setManager(newManager);
		} catch (OperationNotAllowedException e) {
			getOut().println(e.getErrorDescription());
			return null;
		}
		
		getOut().println("Changes are saved.");
		return null;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
	
	protected boolean askYesNoQuestion() throws IOException {
		while (true) {
			String response = getUi().readInput(getIn());
			if (response.equals("n")) {
				return false;
			} else if (response.equals("y")) {
				return true;
			} else {
				getOut().println("Please enter y for Yes, n for No");
				continue;
			}
		}
	}

}
