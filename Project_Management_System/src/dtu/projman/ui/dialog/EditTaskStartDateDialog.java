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
import dtu.projman.app.Task;
import dtu.projman.ui.ProjManUI;
import dtu.projman.ui.screen.Screen;

public class EditTaskStartDateDialog extends EditTaskDialog {

	public EditTaskStartDateDialog(ProjManUI ui, ProjManApp app,
			BufferedReader in, PrintWriter out, Task task) {
		super(ui, app, in, out, task);
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

		if (!getTask().isStartDateLegalForProject(newStartDate)) {
			getOut().println("New start date of the task is ealier than the start date of its project. ");
			getOut().println("Changing it will overwrite the start date of its project and its activity. ");
			getOut().println("Do you want to continue? (y/n)");
			if (askYesNoQuestion()==false) {
				return null;
			}
		}
		
		if (!getTask().isStartDateLegalForActivity(newStartDate)) {
			getOut().println("New start date of the task is ealier than the start date of its activity.");
			getOut().println("Changing it will overwrite the start date of the activity."); 
			getOut().println("Do you want to continue? (y/n)");
			if (askYesNoQuestion()==false) {
				return null;
			}
		}
		
		try {
			getTask().setStartDate(newStartDate);
		} catch (OperationNotAllowedException e) {
			getOut().println(e.getErrorDescription());
			return null;
		}
		
		getOut().println("Changes are saved.");
		return null;
	}

}
