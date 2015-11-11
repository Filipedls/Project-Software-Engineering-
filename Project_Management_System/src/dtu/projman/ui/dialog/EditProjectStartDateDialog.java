package dtu.projman.ui.dialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import dtu.projman.app.OperationNotAllowedException;
import dtu.projman.app.ProjManApp;
import dtu.projman.app.Project;
import dtu.projman.ui.ProjManUI;
import dtu.projman.ui.screen.Screen;

public class EditProjectStartDateDialog extends EditProjectDialog {

	public EditProjectStartDateDialog(ProjManUI ui, ProjManApp app,
			BufferedReader in, PrintWriter out, Project project) {
		super(ui, app, in, out, project);
	}

	@Override
	public Screen runDialog() throws IOException {
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
		Calendar newStartDate = new GregorianCalendar();
		do {
			getOut().println("Enter new start date (dd/mm/yyyy): ");
			try {
				newStartDate.setTime(sdf1.parse(getUi().readInput(getIn())));
				break;
			} catch (ParseException e1) {
				getOut().println("Invalid date");
				continue;
			}
		} while(true);
					
		try {
			getProject().setStartDate(newStartDate);
		} catch (OperationNotAllowedException e) {
			getOut().println(e.getErrorDescription());
			return null;
		}
		
		getOut().println("Changes are saved.");
		return null;
	}

}
