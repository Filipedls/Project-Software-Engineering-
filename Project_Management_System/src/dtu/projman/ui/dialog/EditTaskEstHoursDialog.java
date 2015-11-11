package dtu.projman.ui.dialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import dtu.projman.app.OperationNotAllowedException;
import dtu.projman.app.ProjManApp;
import dtu.projman.app.Task;
import dtu.projman.ui.ProjManUI;
import dtu.projman.ui.screen.Screen;

public class EditTaskEstHoursDialog extends EditTaskDialog {

	public EditTaskEstHoursDialog(ProjManUI ui, ProjManApp app,
			BufferedReader in, PrintWriter out, Task task) {
		super(ui, app, in, out, task);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Screen runDialog() throws IOException {
		getOut().println("Enter new estimated number of hours: ");
		
		double new_estimated_no_hours;
		while(true) {				
			try {
				new_estimated_no_hours = Double.parseDouble(getUi().readInput(getIn()));
			} catch (NumberFormatException e) {
				getOut().println("Enter a valid number");
				continue;
			}
			break;
		}	
		
		try {
			getTask().setEstimated_no_hours(new_estimated_no_hours);
		} catch (OperationNotAllowedException e) {
			getOut().println(e.getErrorDescription());
			return null;
		}
		
		return null;
	}

}
