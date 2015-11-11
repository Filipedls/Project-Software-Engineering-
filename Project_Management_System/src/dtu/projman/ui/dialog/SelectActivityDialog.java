package dtu.projman.ui.dialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import dtu.projman.app.Activity;
import dtu.projman.app.ProjManApp;
import dtu.projman.ui.ProjManUI;
import dtu.projman.ui.screen.ActivityScreen;
import dtu.projman.ui.screen.Screen;

public class SelectActivityDialog extends Dialog {

	public SelectActivityDialog(ProjManUI ui, ProjManApp app,
			BufferedReader in, PrintWriter out) {
		super(ui, app, in, out);
	}

	@Override
	public Screen runDialog() throws IOException {
		int id = 0; 
		getOut().println("Enter activity id: ");
		try {
			id = Integer.parseInt(getUi().readInput(getIn()));
		}
		catch (NumberFormatException e) {
			getOut().println("Invalid data: Enter an integer");
		}
		
		Activity activity = getApp().getActivityById(id);
		if (activity == null) {
			getOut().println("Activity with id number " + id + " does not exist.");
		} else {
			return new ActivityScreen(getUi(), getApp(), activity);
		}

		return null;
	}

}
