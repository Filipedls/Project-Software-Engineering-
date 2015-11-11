package dtu.projman.ui.dialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import dtu.projman.app.Activity;
import dtu.projman.app.OperationNotAllowedException;
import dtu.projman.app.ProjManApp;
import dtu.projman.ui.ProjManUI;
import dtu.projman.ui.screen.Screen;

public class CreateTaskDialog extends Dialog {

	private Activity activity;
	
	public CreateTaskDialog(ProjManUI ui, ProjManApp app, BufferedReader in,
			PrintWriter out, Activity activity) {
		super(ui, app, in, out);
		setActivity(activity);
	}

	@Override
	public Screen runDialog() throws IOException {
		String taskName = null;
		
		getOut().println("Enter task name: ");
		taskName = getUi().readInput(getIn());

		try {
			getApp().createTask(getActivity(), taskName);
		} catch (OperationNotAllowedException e) {
			getOut().println(e.getErrorDescription());
		}

		return null;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

}
