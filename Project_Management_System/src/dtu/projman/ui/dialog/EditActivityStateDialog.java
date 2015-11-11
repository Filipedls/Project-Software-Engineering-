package dtu.projman.ui.dialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import dtu.projman.app.Activity;
import dtu.projman.app.OperationNotAllowedException;
import dtu.projman.app.ProjManApp;
import dtu.projman.app.State;
import dtu.projman.ui.ProjManUI;
import dtu.projman.ui.screen.Screen;

public class EditActivityStateDialog extends EditActivityDialog {

	public EditActivityStateDialog(ProjManUI ui, ProjManApp app,
			BufferedReader in, PrintWriter out, Activity activity) {
		super(ui, app, in, out, activity);
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
				getOut().println("Please enter a valid element number to choose getActivity() state.");
				continue;
			}
			break;
		}
		
		try {
			switch (newState) {
			case 1:
				getActivity().setState(State.NOTSTARTED);
				break;
			case 2:
				getActivity().setState(State.ONGOING);
				break;
			case 3:
				getActivity().setState(State.PAUSED);					
				break;
			case 4:
				getActivity().setState(State.FINISHED);					
				break;
			default:
				getOut().println("Please enter a valid element number to choose getActivity() state.");
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
