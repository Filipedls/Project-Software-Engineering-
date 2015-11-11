package dtu.projman.ui.dialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import dtu.projman.app.OperationNotAllowedException;
import dtu.projman.app.ProjManApp;
import dtu.projman.app.Project;
import dtu.projman.app.ProjectType;
import dtu.projman.ui.ProjManUI;
import dtu.projman.ui.screen.Screen;

public class EditProjectCustomerNameDialog extends EditProjectDialog {

	public EditProjectCustomerNameDialog(ProjManUI ui, ProjManApp app,
			BufferedReader in, PrintWriter out, Project project) {
		super(ui, app, in, out, project);
	}

	@Override
	public Screen runDialog() throws IOException {
		if (getProject().getType()==ProjectType.INTERNAL) {
			getOut().println("Project type is internal. First change the project type.");
			return null;
		}
		getOut().println("Enter new customer name: ");
		String newCustomerName = getUi().readInput(getIn());
		
		try {
			getProject().setCustomerName(newCustomerName);
		} catch (OperationNotAllowedException e) {
			getOut().println(e.getErrorDescription());
			return null;
		}
		
		getOut().println("Changes are saved.");
		return null;
	}

}
