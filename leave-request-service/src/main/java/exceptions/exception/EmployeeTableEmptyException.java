package exceptions.exception;

public class EmployeeTableEmptyException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmployeeTableEmptyException (String message) {
		super(message);
	}

}
