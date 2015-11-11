package dtu.projman.ui.dialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import dtu.projman.app.OperationNotAllowedException;
import dtu.projman.app.ProjManApp;
import dtu.projman.app.Project;
import dtu.projman.ui.ProjManUI;
import dtu.projman.ui.screen.Screen;

public class CreateActivityDialog extends Dialog {

	private Project project;
	
	public CreateActivityDialog(ProjManUI ui, ProjManApp app,
			BufferedReader in, PrintWriter out, Project project) {
		super(ui, app, in, out);
		setProject(project);
	}

	@Override
	public Screen runDialog() throws IOException {
		String activityName = null;
		
		getOut().println("Enter activity name: ");
		activityName = getUi().readInput(getIn());

		try {
			getApp().createActivity(getProject(), activityName);
		} catch (OperationNotAllowedException e) {
			getOut().println(e.getErrorDescription());
		}
		
		return null;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

}
