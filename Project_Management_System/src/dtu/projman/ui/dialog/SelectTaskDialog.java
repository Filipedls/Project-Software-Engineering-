package dtu.projman.ui.dialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import dtu.projman.app.ProjManApp;
import dtu.projman.app.Task;
import dtu.projman.ui.ProjManUI;
import dtu.projman.ui.screen.Screen;
import dtu.projman.ui.screen.TaskScreen;

public class SelectTaskDialog extends Dialog {

	public SelectTaskDialog(ProjManUI ui, ProjManApp app, BufferedReader in,
			PrintWriter out) {
		super(ui, app, in, out);
	}

	@Override
	public Screen runDialog() throws IOException {
		int id = 0; 
		getOut().println("Enter task id: ");
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
		
		Task task = getApp().getTaskById(id);
		if (task == null) {
			getOut().println("Task with id number " + id + " does not exist.");
		} else {
			return new TaskScreen(getUi(), getApp(), task);
		}

		return null;
	}

}
