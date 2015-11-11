package dtu.projman.ui.dialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import dtu.projman.app.ProjManApp;
import dtu.projman.app.Task;
import dtu.projman.ui.ProjManUI;

public abstract class EditTaskDialog extends Dialog {

	private Task task;
	
	public EditTaskDialog(ProjManUI ui, ProjManApp app, BufferedReader in,
			PrintWriter out, Task task) {
		super(ui, app, in, out);
		setTask(task);
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}
	
	protected boolean askYesNoQuestion() throws IOException {
		while (true) {
			String response = getUi().readInput(getIn());
			if (response.equals("n")) {
				return false;
			} else if (response.equals("y")) {
				return true;
			} else {
				getOut().println("Please enter y for Yes, n for No");
				continue;
			}
		}
	}


}
