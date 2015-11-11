package dtu.projman.app;

/*
 * @author Atakan Kaya
 */
public enum Error {
	  NAME_ERROR(0, "Name should be provided."),
	  CUSTOMER_NAME_ERROR(1, "Customer name should be provided."),
	  USERNAME_ERROR(2, "Username should not be empty or should be less than 4 characters."),
	  USERNAME_EXISTS(3, "Register employee operation is not allowed since username already exists"),
	  DATE_NOT_ASSIGNED(4, "Start and end dates have to be assigned."),
	  START_DATE_ERROR(5, "Start date cannot be after end date."),
	  END_DATE_ERROR(6, "End date cannot be before start date."),
	  START_DATE_NOT_MONDAY(7, "Start date must be Monday."),
	  END_DATE_NOT_FRIDAY(8, "End date must be Friday"),
	  AN_EMPLOYEE_ALREADY_LOGGED_IN(9, "System login error: First logout."),
	  NO_EMPLOYEE_LOGGED_IN(10, "No employee has logged in."),
	  PROJECT_TYPE_ERROR(11, "Project type should be provided."),
	  TASK_TYPE_ERROR(12, "Task type should be provided."),
	  EMPLOYEE_NOT_REGISTERED(13, "the username does not correspond to a registered employee in the system."),
	  PROJECT_NOT_REGISTERED(14, "Project is not in app."),
	  ACTIVITY_NOT_REGISTERED(15, "Activity is not in app."),
	  ILLEGAL_PROJECT_START_DATE(16, "Start date of the project cannot be later than the start date of any of its activities."),
	  ILLEGAL_PROJECT_END_DATE(17, "End date of the project cannot be earlier than the end date of any of its activities."),
	  ILLEGAL_ACTIVITY_START_DATE(18, "Start date of the activity cannot be later than the start date of any of its tasks."),
	  ILLEGAL_ACTIVITY_END_DATE(19, "End date of the activity cannot be earlier than the end date of any of its tasks."),  
	  ASSIGN_DEVELOPER_ERROR_DATE(20, "In order to assign an employee to a task, first set the start and end date."),
	  ASSIGN_DEVELOPER_ERROR_TASKSNUMBER(21, "In order to assign an employee to a task, he has to have less that 20 tasks for that period."),
	  ASSIGN_DEVELOPER_ERROR_UNAVAILABILITY(22, "In order to assign an employee to a task, he must have no unavailabilities for that period."),
	  ASSIGN_DEVELOPER_ERROR_HELPER(23, "That employee is assigned as a helper for this task."),
	  ESTIMATED_NO_HOURS_ACCURACY_ERROR(24, "Estimated number of hours must have 1/2 hour accuracy."),
	  WORKED_NO_HOURS_ACCURACY_ERROR(25, "Worked number of hours must have 1/2 hour accuracy."),
	  EMPLOYEE_NOT_AVAILABLE(26, "The unavailability is overlapped with some tasks or unavailabilities."),
	  ASSIGN_HELPER_DEVELOPER(27, "The helper is already a developer for this task."),
	  ASSIGN_DEVELOPER_ALREADY_AS_HELPER(28, "The developer is already a helper for this task."),
	  EMPTY_FIELD(29,"Empty field, please fill all the fields."),
	  STATE_NULL(30,"The state can't be null.");

	  private final int code;
	  private final String description;

	  private Error(int code, String description) {
	    this.code = code;
	    this.description = description;
	  }

	  public String getDescription() {
	     return description;
	  }

	  public int getCode() {
	     return code;
	  }

	  @Override
	  public String toString() {
	    return code + ": " + description;
	  }
}
