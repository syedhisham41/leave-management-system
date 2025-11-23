package exceptions.exception;

public class InvalidEmployeePasswordException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidEmployeePasswordException(String message, Throwable cause) {
		super(message, cause);
	}
}
