package dtu.projman.ui.dialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import dtu.projman.app.OperationNotAllowedException;
import dtu.projman.app.ProjManApp;
import dtu.projman.ui.ProjManUI;
import dtu.projman.ui.screen.MainScreen;
import dtu.projman.ui.screen.Screen;

public class LoginDialog extends Dialog {

	public LoginDialog(ProjManUI ui, ProjManApp app, BufferedReader in, PrintWriter out) {
		super(ui, app, in, out);
	}

	@Override
	public Screen runDialog() throws IOException {
		getOut().println("Enter user name: ");
		String username = getUi().readInput(getIn());
		
		try {
			if (getApp().employeeLogin(username)!=null) {
				getOut().println("Login successful.");
				return new MainScreen(getUi(), getApp());
			} else {
				getOut().println("Wrong username");
			}
		} catch (OperationNotAllowedException e) {
			getOut().println(e.getErrorDescription());
		}
		return null;
	}

}
