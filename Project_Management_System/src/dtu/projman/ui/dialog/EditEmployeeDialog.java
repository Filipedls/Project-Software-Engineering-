package dtu.projman.ui.dialog;

import java.io.BufferedReader;
import java.io.PrintWriter;

import dtu.projman.app.Employee;
import dtu.projman.app.ProjManApp;
import dtu.projman.ui.ProjManUI;

public abstract class EditEmployeeDialog extends Dialog {

	private Employee employee;
	
	public EditEmployeeDialog(ProjManUI ui, ProjManApp app, BufferedReader in,
			PrintWriter out, Employee employee) {
		super(ui, app, in, out);
		setEmployee(employee);
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}



}
