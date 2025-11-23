package exceptions.exception;

public class InvalidEmployeeEmailException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidEmployeeEmailException(String message, Throwable cause) {
		super(message, cause);
	}
}
