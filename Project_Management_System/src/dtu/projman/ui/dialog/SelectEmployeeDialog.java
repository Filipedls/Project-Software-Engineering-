package dtu.projman.ui.dialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import dtu.projman.app.Employee;
import dtu.projman.app.ProjManApp;
import dtu.projman.ui.ProjManUI;
import dtu.projman.ui.screen.EmployeeScreen;
import dtu.projman.ui.screen.Screen;

public class SelectEmployeeDialog extends Dialog {

	public SelectEmployeeDialog(ProjManUI ui, ProjManApp app,
			BufferedReader in, PrintWriter out) {
		super(ui, app, in, out);
	}

	@Override
	public Screen runDialog() throws IOException {
		getOut().println("Enter employee username: ");
		String username = getUi().readInput(getIn());
		
		Employee employee = getApp().getEmployeeByUsername(username);
		if (employee == null) {
			getOut().println("Employee with username " + username + " does not exist.");
		} else {
			return new EmployeeScreen(getUi(), getApp(), employee);
		}
		
		return null;
	}

}
