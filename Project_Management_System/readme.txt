USER GUIDE by Filipe Silva

PROGRAM: To start the program the user should run the file "ProjManUI.java". This file is in the "src" folder in the "dtu.projman.ui" package. To run the file in Eclipse press right button of the mouse on the file, then go to "Run As" and select "1 Java Aplication". Note that the user interface is the console. No GUI has been developed.

TESTS: Acceptance tests are put into folder "test". To run the test, press right button of the mouse on the folder, then go to "Run As" and select "3 JUnit Test".
Systematic tests are put into folder "systematic_tests". To run the test, press right button of the mouse on the folder, then go to "Run As" and select "3 JUnit Test".

QUICK WALKTHROUGH ON THE PROGRAM
The application starts with a 3 option menu:

LOGIN SCREEN
1-	Login
2-	Register employee
3-	Exit

If the user is not register, he should start by choosing the option 2 and then insert his full name, username and e-mail. After that the user is ready to use the system. For that he needs to, in the same menu, chooses the option 1 and type his username.
Note that registering employee option is put into this menu since there is no persistency layer. 
After the login the MAIN SCREEN menu will appear:

MAIN SCREEN
1-	List my tasks
2-	List projects that I am managing
3-	Register an unavailability for me
4-	List the workload of employees
5-	List all projects
6-	List all employees
7-	Create project
8-	Register employee
9-	Select project
10-	Select task
11-	Select employee
12-	Logout

* CREATION OF PROJECTS, ACTIVITIES AND TASKS
For the creation of a project an employee should select the option 7 in the MAIN SCREEN. After that it will be prompted to him the name and the type on the project. The type can be INTERNAL or EXTERNAL. If it's an external project the employee will need to type customer's name too. After that the employee should create an activity. For that he should select the option 9 and type the project's ID (in case of unknown project's ID the employee can check it in the option 5). The PROJECT SCREEN will appear:

PROJECT SCREEN
Project selected: 
Id: 1
Name: Project 1

1-	See project's details
2-	Edit name
3-	Edit type
4-	Edit customer name
5-	Edit description
6-	Edit start date
7-	Edit end date
8-	Edit state
9-	Assign manager
10-	List activities
11-	Create activity
12-	Select activity
13-	Back

In this screen the employee can edit and see all the characteristics of the project. One can create an activity by selection option 10 and entering the name . For selecting an activity the employee should select the option 11 and type the activity's ID (can be seen in the option 9). The ACTIVITY SCREEN will appear:

ACTIVITY SCREEN
Activity selected: 
Id: 2
Name: Activity 1

1-	See activity's details
2-	Edit name
3-	Edit description
4-	Edit start date
5-	Edit end date
6-	Edit state
7-	List tasks
8-	Create task
9-	Select task
10-	Back

Once again the employee can edit and see all the characteristics of the activity.
The last step is the creation of a task. For that the employee should select the option 7 and type the task's name. After that, select the option 8 and type the task's ID. The TASK SCREEN will appear:

TASK SCREEN
Task selected: 
Id: 3
Name: Task 1

1-	See task's details
2-	Edit name
3-	Edit description
4-	Edit start date
5-	Edit end date
6-	Edit state
7-	Edit expected number of hours
8-	Edit worked number of hours
9-	Assign employee to the task
10-	Assign helper to the task
11-	List helpers
12-	Back

In this screen you can edit and see all the characteristics of the Task.


* REGISTER UNAVAILABILITY

For that the employee should, in the MAIN SCREEN, select the option 3. After that he should type the start date, the end date and a reason.

* EMPLOYEE SCREEN

In the employee screen the employee can edit all his informations (options 1, 2 and 3), list all the assigned tasks (4) and unavailabilities (5) and check his workload.

EMPLOYEE SCREEN
Employee selected: 
Employee name: Filipe Silva
Employee username: fili
Employee email address: filipe.dls@gmail.com
1-	Edit full name
2-	Edit user name
3-	Edit email address
4-	List employee’s tasks
5-	List employee’s unavailabilities
6-	See employee’s workload
7-	Select task
8-	Register unavailability
9-	Back
