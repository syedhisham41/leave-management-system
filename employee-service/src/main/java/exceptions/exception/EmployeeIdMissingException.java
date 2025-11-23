package exceptions.exception;

public class EmployeeIdMissingException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmployeeIdMissingException(String message, Throwable cause) {
		super(message, cause);
	}
}
