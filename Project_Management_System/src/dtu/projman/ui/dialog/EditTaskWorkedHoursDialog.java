package dtu.projman.ui.dialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import dtu.projman.app.OperationNotAllowedException;
import dtu.projman.app.ProjManApp;
import dtu.projman.app.Task;
import dtu.projman.ui.ProjManUI;
import dtu.projman.ui.screen.Screen;

public class EditTaskWorkedHoursDialog extends EditTaskDialog {

	public EditTaskWorkedHoursDialog(ProjManUI ui, ProjManApp app,
			BufferedReader in, PrintWriter out, Task task) {
		super(ui, app, in, out, task);
	}

	@Override
	public Screen runDialog() throws IOException {
		getOut().println("Enter new worked number of hours: ");
		
		double new_worked_no_hours;
		while(true) {				
			try {
				new_worked_no_hours = Double.parseDouble(getUi().readInput(getIn()));
			} catch (NumberFormatException e) {
				getOut().println("Enter a valid number");
				continue;
			}
			break;
		}	
		
		try {
			getTask().setWorked_no_hours(new_worked_no_hours);
		} catch (OperationNotAllowedException e) {
			getOut().println(e.getErrorDescription());
			return null;
		}
		
		getOut().println("Changes are saved.");
		return null;
	}

}
