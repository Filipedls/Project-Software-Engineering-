package dtu.projman.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import dtu.projman.app.ProjManApp;
import dtu.projman.ui.screen.LoginScreen;
import dtu.projman.ui.screen.Screen;


/*
 * @author ALL THE USER INTERFACE COMPONENTS WERE IMPLEMENTED BY Atakan Kaya 
 */
public class ProjManUI {

	private Screen screen;
	private ProjManApp app = new ProjManApp();

	public ProjManUI() {
		setScreen(new LoginScreen(this, app));
	}

	public void printMenu(PrintWriter out) throws IOException {
		screen.printMenu(out);
	}

	public boolean processInput(String input, BufferedReader in, PrintWriter out) throws IOException {
		return screen.processInput(input, in, out);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out, true);
		ProjManUI ui = new ProjManUI();
		ui.basicLoop(in, out);
	}

	public void basicLoop(BufferedReader in, PrintWriter out)
			throws IOException {
		String selection;
		do {
			printMenu(out);
			selection = readInput(in);
		} while (!processInput(selection, in, out));
	}

	public void setScreen(Screen screen) {
		this.screen = screen;
	}

	Screen getScreen() {
		return screen;
	}

	ProjManApp getProjManApp() {
		return app;
	}

	public String readInput(BufferedReader in) throws IOException {
		return in.readLine();
	}
}
