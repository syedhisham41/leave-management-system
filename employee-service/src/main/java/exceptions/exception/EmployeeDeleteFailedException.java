package exceptions.exception;

public class EmployeeDeleteFailedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmployeeDeleteFailedException(String message, Throwable cause) {
		super(message, cause);
	}
}
