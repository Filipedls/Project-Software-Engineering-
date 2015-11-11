package dtu.projman.ui.screen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import dtu.projman.app.ProjManApp;
import dtu.projman.ui.ProjManUI;

public abstract class Screen {

	private ProjManUI ui;
	private ProjManApp app;
	
	public Screen(ProjManUI ui, ProjManApp app) {
		setUi(ui);
		setApp(app);
	}
	
	public ProjManUI getUi() {
		return ui;
	}

	public void setUi(ProjManUI ui) {
		this.ui = ui;
	}
	
	public ProjManApp getApp() {
		return app;
	}

	public void setApp(ProjManApp app) {
		this.app = app;
	}

	public abstract void printMenu(PrintWriter out) throws IOException ;

	public abstract boolean processInput(String input, BufferedReader in, PrintWriter out) throws IOException ;
	
	public int getSelection(String input, PrintWriter out) {
		int code;
		try {
			code = Integer.parseInt(input);			
		} catch (NumberFormatException e) {
			out.println("Please enter a valid element number to choose an operation.");
			return 0;
		}
		return code;
	}
}
