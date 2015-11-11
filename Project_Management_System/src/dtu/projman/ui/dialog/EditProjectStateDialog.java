package dtu.projman.ui.dialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import dtu.projman.app.OperationNotAllowedException;
import dtu.projman.app.ProjManApp;
import dtu.projman.app.Project;
import dtu.projman.app.State;
import dtu.projman.ui.ProjManUI;
import dtu.projman.ui.screen.Screen;

public class EditProjectStateDialog extends EditProjectDialog {

	public EditProjectStateDialog(ProjManUI ui, ProjManApp app,
			BufferedReader in, PrintWriter out, Project project) {
		super(ui, app, in, out, project);
	}

	@Override
	public Screen runDialog() throws IOException {
		getOut().println("Choose new state: ");
		getOut().println("1- Not started");
		getOut().println("2- Ongoing");
		getOut().println("3- Paused");
		getOut().println("4- Finished");
		int newState;
		
		while(true) {
			try {
				newState = Integer.parseInt(getUi().readInput(getIn()));
			}
			catch(NumberFormatException e) {
				getOut().println("Please enter a valid element number to choose getProject() state.");
				continue;
			}
			break;
		}
		
		try {
			switch (newState) {
			case 1:
				getProject().setState(State.NOTSTARTED);
				break;
			case 2:
				getProject().setState(State.ONGOING);
				break;
			case 3:
				getProject().setState(State.PAUSED);					
				break;
			case 4:
				getProject().setState(State.FINISHED);					
				break;
			default:
				getOut().println("Please enter a valid element number to choose getProject() state.");
				break;
			}
		} catch (OperationNotAllowedException e) {
			getOut().println(e.getErrorDescription());
			return null;
		}
		
		getOut().println("Changes are saved.");
		return null;
	}

}
