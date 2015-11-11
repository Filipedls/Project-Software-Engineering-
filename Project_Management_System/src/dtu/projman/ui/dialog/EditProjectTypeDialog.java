package dtu.projman.ui.dialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import dtu.projman.app.OperationNotAllowedException;
import dtu.projman.app.ProjManApp;
import dtu.projman.app.Project;
import dtu.projman.app.ProjectType;
import dtu.projman.ui.ProjManUI;
import dtu.projman.ui.screen.Screen;

public class EditProjectTypeDialog extends EditProjectDialog {

	public EditProjectTypeDialog(ProjManUI ui, ProjManApp app,
			BufferedReader in, PrintWriter out, Project project) {
		super(ui, app, in, out, project);
	}

	@Override
	public Screen runDialog() throws IOException {
		getOut().println("Choose new type: ");
		getOut().println("1- Internal");
		getOut().println("2- External");
		int newType;
		
		while(true) {
			try {
				newType = Integer.parseInt(getUi().readInput(getIn()));
				if (newType!=1 && newType!=2) {
					getOut().println("Please enter a valid element number to choose project type.");
					continue;
				}
			}
			catch(NumberFormatException e) {
				getOut().println("Please enter a valid element number to choose project type.");
				continue;
			}
			break;
		}
		
		try {
			getProject().setType((newType==1) ? ProjectType.INTERNAL : ProjectType.EXTERNAL);
		} catch (OperationNotAllowedException e) {
			getOut().println(e.getErrorDescription());
			return null;
		}
		
		getOut().println("Changes are saved.");
		
		return null;
	}

}
