package dtu.projman.ui.dialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import dtu.projman.app.ProjManApp;
import dtu.projman.ui.ProjManUI;
import dtu.projman.ui.screen.Screen;

public abstract class Dialog {

	private ProjManUI ui;
	private ProjManApp app;
	private BufferedReader in;
	private PrintWriter out;
	
	public Dialog(ProjManUI ui, ProjManApp app, BufferedReader in, PrintWriter out) {
		setUi(ui);
		setApp(app);
		setIn(in);
		setOut(out);
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

	public BufferedReader getIn() {
		return in;
	}

	public void setIn(BufferedReader in) {
		this.in = in;
	}

	public PrintWriter getOut() {
		return out;
	}

	public void setOut(PrintWriter out) {
		this.out = out;
	}
	
	public abstract Screen runDialog() throws IOException ;
	
	
}
