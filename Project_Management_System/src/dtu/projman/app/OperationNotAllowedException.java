package dtu.projman.app;

/*
 * @author Atakan Kaya
 */
public class OperationNotAllowedException extends Exception {
	
	Error error;
	
	public OperationNotAllowedException(Error error){
		super(error.getDescription());
		
		setError(error);		
	}

	public Error getError() {
		return error;
	}
	
	public void setError(Error error) {
		this.error = error;
	}
	
	public String getErrorDescription() {
		return "Error: " + error.getDescription();
	}
}
