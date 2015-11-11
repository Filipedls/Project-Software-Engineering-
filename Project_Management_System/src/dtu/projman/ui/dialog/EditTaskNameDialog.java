package dtu.projman.ui.dialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import dtu.projman.app.OperationNotAllowedException;
import dtu.projman.app.ProjManApp;
import dtu.projman.app.Task;
import dtu.projman.ui.ProjManUI;
import dtu.projman.ui.screen.Screen;

public class EditTaskNameDialog extends EditTaskDialog {

	public EditTaskNameDialog(ProjManUI ui, ProjManApp app, BufferedReader in,
			PrintWriter out, Task task) {
		super(ui, app, in, out, task);
	}

	@Override
	public Screen runDialog() throws IOException {
		getOut().println("Enter new name: ");
		String newName = getUi().readInput(getIn());
		
		try {
			getTask().setName(newName);
		} catch (OperationNotAllowedException e) {
			getOut().println(e.getErrorDescription());
			return null;
		}
		
		getOut().println("Changes are saved.");
		return null;
	}

}
