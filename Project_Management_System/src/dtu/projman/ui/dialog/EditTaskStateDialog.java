package dtu.projman.ui.dialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import dtu.projman.app.OperationNotAllowedException;
import dtu.projman.app.ProjManApp;
import dtu.projman.app.State;
import dtu.projman.app.Task;
import dtu.projman.ui.ProjManUI;
import dtu.projman.ui.screen.Screen;

public class EditTaskStateDialog extends EditTaskDialog {

	public EditTaskStateDialog(ProjManUI ui, ProjManApp app, BufferedReader in,
			PrintWriter out, Task task) {
		super(ui, app, in, out, task);
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
				getOut().println("Please enter a valid element number to choose getTask() state.");
				continue;
			}
			break;
		}
		
		try {
			switch (newState) {
			case 1:
				getTask().setState(State.NOTSTARTED);
				break;
			case 2:
				getTask().setState(State.ONGOING);
				break;
			case 3:
				getTask().setState(State.PAUSED);					
				break;
			case 4:
				getTask().setState(State.FINISHED);					
				break;
			default:
				getOut().println("Please enter a valid element number to choose getTask() state.");
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
