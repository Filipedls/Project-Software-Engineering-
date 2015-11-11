package dtu.projman.ui.dialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import dtu.projman.app.Activity;
import dtu.projman.app.ProjManApp;
import dtu.projman.ui.ProjManUI;

public abstract class EditActivityDialog extends Dialog {

	private Activity activity;
	
	public EditActivityDialog(ProjManUI ui, ProjManApp app, BufferedReader in,
			PrintWriter out, Activity activity) {
		super(ui, app, in, out);
		setActivity(activity);
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
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
