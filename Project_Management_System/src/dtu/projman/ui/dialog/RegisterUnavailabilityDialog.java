package dtu.projman.ui.dialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import dtu.projman.app.Employee;
import dtu.projman.app.OperationNotAllowedException;
import dtu.projman.app.ProjManApp;
import dtu.projman.ui.ProjManUI;
import dtu.projman.ui.screen.Screen;

public class RegisterUnavailabilityDialog extends Dialog {

	private Employee employee;
	
	public RegisterUnavailabilityDialog(ProjManUI ui, ProjManApp app,
			BufferedReader in, PrintWriter out, Employee employee) {
		super(ui, app, in, out);
		this.employee = employee;
	}

	@Override
	public Screen runDialog() throws IOException {
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy"), 
				 		sdf2 = new SimpleDateFormat("dd/MM/yyyy");
		
		Calendar startDate = new GregorianCalendar();
		getOut().println("Enter start date (dd/mm/yyyy): ");
		try {
			startDate.setTime(sdf1.parse(getUi().readInput(getIn())));
		} catch (ParseException e1) {
			getOut().println("Invalid date");
			return null;
		}
					
		Calendar endDate = new GregorianCalendar();
		getOut().println("Enter end date (dd/mm/yyyy): ");
		try {
			endDate.setTime(sdf2.parse(getUi().readInput(getIn())));
		} catch (ParseException e1) {
			getOut().println("Invalid date");
			return null;
		}
		
		getOut().println("Enter reason: ");
		String description = getUi().readInput(getIn());
		
		try {
			getApp().createUnavailability(employee, startDate, endDate, description);
		} catch (OperationNotAllowedException e) {
			getOut().println(e.getErrorDescription());
		}
		
		getOut().println("Changes are saved.");

		return null;
	}

}
