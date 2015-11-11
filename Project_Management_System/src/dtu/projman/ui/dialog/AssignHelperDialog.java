package dtu.projman.ui.dialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import dtu.projman.app.OperationNotAllowedException;
import dtu.projman.app.ProjManApp;
import dtu.projman.app.Task;
import dtu.projman.ui.ProjManUI;
import dtu.projman.ui.screen.Screen;

public class AssignHelperDialog extends EditTaskDialog {

	public AssignHelperDialog(ProjManUI ui, ProjManApp app, BufferedReader in,
			PrintWriter out, Task task) {
		super(ui, app, in, out, task);
	}

	@Override
	public Screen runDialog() throws IOException {
		getOut().println("Enter helper's user name: ");
		String helperUserName = getUi().readInput(getIn());
		
		getOut().println("Enter helper's estimated number of work hours: ");

		double helper_estimated_no_hours;
		while(true) {				
			try {
				helper_estimated_no_hours = Double.parseDouble(getUi().readInput(getIn()));
			} catch (NumberFormatException e) {
				getOut().println("Enter a valid number");
				continue;
			}
			break;
		}
		
		try {
			getTask().assignHelper(getApp().getEmployeeByUsername(helperUserName), helper_estimated_no_hours);
		} catch (OperationNotAllowedException e) {
			getOut().println(e.getErrorDescription());
			return null;
		}
		
		getOut().println("Changes are saved.");
		return null;
	}

}
