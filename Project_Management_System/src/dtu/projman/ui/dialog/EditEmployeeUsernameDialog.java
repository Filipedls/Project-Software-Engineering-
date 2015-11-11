package dtu.projman.ui.dialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import dtu.projman.app.Employee;
import dtu.projman.app.OperationNotAllowedException;
import dtu.projman.app.ProjManApp;
import dtu.projman.ui.ProjManUI;
import dtu.projman.ui.screen.Screen;

public class EditEmployeeUsernameDialog extends EditEmployeeDialog {

	public EditEmployeeUsernameDialog(ProjManUI ui, ProjManApp app,
			BufferedReader in, PrintWriter out, Employee employee) {
		super(ui, app, in, out, employee);
	}

	@Override
	public Screen runDialog() throws IOException {
		getOut().println("Enter new user name: ");
		String newUserName = getUi().readInput(getIn());
		
		try {
			getEmployee().setUsername(newUserName);
		} catch (OperationNotAllowedException e) {
			getOut().println(e.getErrorDescription());
			return null;
		}
		
		getOut().println("Changes are saved.");
		
		return null;
	}

}
