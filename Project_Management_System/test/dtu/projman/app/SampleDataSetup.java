package dtu.projman.app;

import org.junit.Before;

/*
 * @author Filipe Silva
 */
public class SampleDataSetup {

	ProjManApp app = new ProjManApp();
	
	/**
	 * General Data Setup
	 * The setUp function adds employees, projects, activities and task to the Project Manager Application "app".
	 * @author Filipe Silva
	 */
	@Before
	public void setUp() throws Exception {
		// Auxiliary variables
		Employee emp;
		
		String fullname;
		String username;
		String email;
		String projectName;
		String activityName;
		String taskName;
		
		int i;
		
		// 1. Create the employees and register the employees
		fullname = "Filipe Silva";
		username = "fili";
		email = "filipe.dls@gmail.com";
		emp = new Employee(fullname, username, email);
		app.registerEmployee(emp);
		
		fullname = "Atakan Kaya";
		username = "atak";
		email = "kayaatakan@gmail.com";
		emp = new Employee(fullname, username, email);
		app.registerEmployee(emp);
		
		fullname = "Marc Thomsen";
		username = "marc";
		email = "marcthomsen88@gmail.com";
		emp = new Employee(fullname, username, email);
		app.registerEmployee(emp);
		
		fullname = "Marianne Louis-Hansen";
		username = "mari";
		email = "ml-h@hotmail.com";
		emp = new Employee(fullname, username, email);
		app.registerEmployee(emp);

		// 3. Login to system with this employee
		app.employeeLogin("fili");
		
		// 4. Create the projects and add them to the system
		for(i=1; i<=4; i++){
			projectName = "Project " + i;
			app.createProject(projectName, ProjectType.INTERNAL, "");
		}
		
		// 6. Create the activities for each project and add them.
		for(Project projLoop: app.getProjects()){
			for(i=1; i<=2; i++){
				activityName = "Activity " + i;
				app.createActivity(projLoop, activityName);
			}
		}
	
		// 7. Create the tasks for each activity and add them.
		for(Project projLoop: app.getProjects()){
			for(Activity activLoop: projLoop.getActivities()){
				for(i=1; i<=2; i++){
					taskName = "Task " + i;
					app.createTask(activLoop, taskName);
				}
			}
		}
		
		// 8. Log Off
		app.employeeLogoff();
		

	}


}
