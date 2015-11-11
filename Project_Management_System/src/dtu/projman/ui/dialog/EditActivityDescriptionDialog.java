package dtu.projman.ui.dialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import dtu.projman.app.Activity;
import dtu.projman.app.OperationNotAllowedException;
import dtu.projman.app.ProjManApp;
import dtu.projman.ui.ProjManUI;
import dtu.projman.ui.screen.Screen;

public class EditActivityDescriptionDialog extends EditActivityDialog {

	public EditActivityDescriptionDialog(ProjManUI ui, ProjManApp app,
			BufferedReader in, PrintWriter out, Activity activity) {
		super(ui, app, in, out, activity);
	}

	@Override
	public Screen runDialog() throws IOException {
		getOut().println("Enter new description: ");
		String newDescription= getUi().readInput(getIn());
		
		try {
			getActivity().setDescription(newDescription);
		} catch (OperationNotAllowedException e) {
			getOut().println(e.getErrorDescription());
			return null;
		}
		
		getOut().println("Changes are saved.");
		return null;
	}

}
