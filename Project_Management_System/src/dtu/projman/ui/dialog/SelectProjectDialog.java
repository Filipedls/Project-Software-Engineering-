package dtu.projman.ui.dialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import dtu.projman.app.ProjManApp;
import dtu.projman.app.Project;
import dtu.projman.ui.ProjManUI;
import dtu.projman.ui.screen.ProjectScreen;
import dtu.projman.ui.screen.Screen;

public class SelectProjectDialog extends Dialog {

	public SelectProjectDialog(ProjManUI ui, ProjManApp app, BufferedReader in,
			PrintWriter out) {
		super(ui, app, in, out);
	}

	@Override
	public Screen runDialog() throws IOException {
		int id = 0; 
		getOut().println("Enter project id: ");
		do {
			try {
				id = Integer.parseInt(getUi().readInput(getIn()));
				break;
			}
			catch (NumberFormatException e) {
				getOut().println("Invalid data: Enter an integer");
				continue;
			}
		} while(true);
		
		Project project = getApp().getProjectById(id);
		if (project == null) {
			getOut().println("Project with id number " + id + " does not exist.");
		} else {
			return new ProjectScreen(getUi(), getApp(), project);
		}
		
		return null;
	}

}
