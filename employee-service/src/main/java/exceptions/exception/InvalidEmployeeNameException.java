package exceptions.exception;

public class InvalidEmployeeNameException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidEmployeeNameException(String message, Throwable cause) {
		super(message, cause);
	}
}
