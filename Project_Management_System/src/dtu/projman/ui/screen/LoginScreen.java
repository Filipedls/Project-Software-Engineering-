package dtu.projman.ui.screen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import dtu.projman.app.ProjManApp;
import dtu.projman.ui.ProjManUI;
import dtu.projman.ui.dialog.LoginDialog;
import dtu.projman.ui.dialog.RegisterEmployeeDialog;

public class LoginScreen extends Screen {

	public LoginScreen(ProjManUI ui, ProjManApp app) {
		super(ui, app);
	}
	
	@Override
	public void printMenu(PrintWriter out) throws IOException {
		out.println();
		out.println("LOGIN SCREEN");
		out.println("1-	Login");
		out.println("2-	Register employee");
		out.println("3-	Exit");
	}

	@Override
	public boolean processInput(String input, BufferedReader in, PrintWriter out)
			throws IOException {
		int code = getSelection(input, out);
		
		switch (code) {
		case 1:
			Screen nextScreen = new LoginDialog(getUi(), getApp(), in, out).runDialog();
			if (nextScreen!=null) {
				getUi().setScreen(nextScreen);
			}
			break;
		case 2:
			new RegisterEmployeeDialog(getUi(), getApp(), in, out).runDialog();
			break;
		case 3:
			out.println("Closing the program.");
			return true;
		default:
			out.println("Please enter a valid element number to choose an operation.");
			break;
		}
		
		return false;
	}

}
