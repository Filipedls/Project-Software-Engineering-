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

public class EditTaskEndDateDialog extends EditTaskDialog {

	public EditTaskEndDateDialog(ProjManUI ui, ProjManApp app,
			BufferedReader in, PrintWriter out, Task task) {
		super(ui, app, in, out, task);
	}

	@Override
	public Screen runDialog() throws IOException {
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
		Calendar newEndDate = new GregorianCalendar();
		do {
			getOut().println("Enter new end date (dd/mm/yyyy): ");
			try {
				newEndDate.setTime(sdf2.parse(getUi().readInput(getIn())));
				break;
			} catch (ParseException e1) {
				getOut().println("Invalid date");
				continue;
			}
		} while(true);
		
		if (!getTask().isEndDateLegalForProject(newEndDate)) {
			getOut().println("New end date of the task is later than the end date of its project."); 
			getOut().println("Changing it will overwrite the end date of its project and its activity.");
			getOut().println("Do you want to continue? (y/n)");
			if (askYesNoQuestion()==false) {
				return null;
			}
		}
		
		if (!getTask().isEndDateLegalForActivity(newEndDate)) {
			getOut().println("New end date of the task is later than the start date of its activity. ");
			getOut().println("Changing it will overwrite the end date of the activity. ");
			getOut().println("Do you want to continue? (y/n)");
			if (askYesNoQuestion()==false) {
				return null;
			}
		}
		
		try {
			getTask().setEndDate(newEndDate);
		} catch (OperationNotAllowedException e) {
			getOut().println(e.getErrorDescription());
			return null;
		}
		
		return null;
	}

}
