package dtu.projman.ui.dialog;

import java.io.BufferedReader;
import java.io.PrintWriter;

import dtu.projman.app.ProjManApp;
import dtu.projman.app.Project;
import dtu.projman.ui.ProjManUI;

public abstract class EditProjectDialog extends Dialog {

	private Project project;
	
	public EditProjectDialog(ProjManUI ui, ProjManApp app, BufferedReader in,
			PrintWriter out, Project project) {
		super(ui, app, in, out);
		setProject(project);
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

}
