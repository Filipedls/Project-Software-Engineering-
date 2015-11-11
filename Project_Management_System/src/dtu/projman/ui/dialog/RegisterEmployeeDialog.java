package dtu.projman.ui.dialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import dtu.projman.app.Employee;
import dtu.projman.app.OperationNotAllowedException;
import dtu.projman.app.ProjManApp;
import dtu.projman.ui.ProjManUI;
import dtu.projman.ui.screen.Screen;

public class RegisterEmployeeDialog extends Dialog {

	
	public RegisterEmployeeDialog(ProjManUI ui, ProjManApp app,
			BufferedReader in, PrintWriter out) {
		super(ui, app, in, out);
	}

	@Override
	public Screen runDialog() throws IOException {
		getOut().println("Enter full name: ");
		String fullname = getUi().readInput(getIn());
		
		getOut().println("Enter user name: ");
		String username = getUi().readInput(getIn());
		
		getOut().println("Enter email address: ");
		String email = getUi().readInput(getIn());
		
		try {
			Employee employee = new Employee(fullname, username, email);
			getApp().registerEmployee(employee);
			getOut().println("Employee added.");
		} catch (OperationNotAllowedException e) {
			getOut().println(e.getErrorDescription());
		}
		
		return null;
	}

}
