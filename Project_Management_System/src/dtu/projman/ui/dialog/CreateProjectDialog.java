package dtu.projman.ui.dialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import dtu.projman.app.OperationNotAllowedException;
import dtu.projman.app.ProjManApp;
import dtu.projman.app.ProjectType;
import dtu.projman.ui.ProjManUI;
import dtu.projman.ui.screen.Screen;

public class CreateProjectDialog extends Dialog {

	public CreateProjectDialog(ProjManUI ui, ProjManApp app, BufferedReader in,
			PrintWriter out) {
		super(ui, app, in, out);
	}

	@Override
	public Screen runDialog() throws IOException {
		String projectName, customerName = null;
		int type;
		
		getOut().println("Enter project name: ");
		projectName = getUi().readInput(getIn());

		getOut().println("Choose project type: ");
		getOut().println("1- Internal");
		getOut().println("2- External");
		
		while(true) {
			try {
				type = Integer.parseInt(getUi().readInput(getIn()));
				if (type!=1 && type!=2) {
					getOut().println("Please enter a valid element number to choose project type:");
					continue;
				}
						
			}
			catch(NumberFormatException e) {
				getOut().println("Please enter a valid element number to choose project type: ");
				continue;
			}
			break;
		}
		
		if (type == 1) {
			customerName = "Software House A/S";
		} else if (type==2) {
			getOut().println("Enter customer name: ");
			customerName = getUi().readInput(getIn());
		}

		try {
			getApp().createProject(projectName, (type==1) ? ProjectType.INTERNAL: ProjectType.EXTERNAL, customerName);
			getOut().println("Project created.");
		} catch (OperationNotAllowedException e) {
			getOut().println(e.getErrorDescription());
		}
		
		return null;
	}

}
