package dtu.projman.ui.dialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import dtu.projman.app.Activity;
import dtu.projman.app.OperationNotAllowedException;
import dtu.projman.app.ProjManApp;
import dtu.projman.ui.ProjManUI;
import dtu.projman.ui.screen.Screen;

public class EditActivityStartDateDialog extends EditActivityDialog {

	public EditActivityStartDateDialog(ProjManUI ui, ProjManApp app,
			BufferedReader in, PrintWriter out, Activity activity) {
		super(ui, app, in, out, activity);
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
				getOut().println("Invalid date, enter again:");
				continue;
			}
		} while(true);
			
		if (!getActivity().isStartDateLegalForProject(newStartDate)) {
			getOut().println("New start date of the activity is ealier than the start date of its project. "); 
			getOut().println("Changing it will overwrite the start date of the project.");
			getOut().println("Do you want to continue? (y/n)");
			if (askYesNoQuestion()==false) {
				return null;
			}
		}
		
		try {
			getActivity().setStartDate(newStartDate);
		} catch (OperationNotAllowedException e) {
			getOut().println(e.getErrorDescription());
			return null;
		}
		
		getOut().println("Changes are saved.");
		return null;
	}


}
