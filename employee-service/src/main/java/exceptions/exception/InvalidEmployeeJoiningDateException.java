package exceptions.exception;

public class InvalidEmployeeJoiningDateException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidEmployeeJoiningDateException(String message, Throwable cause) {
		super(message, cause);
	}
}
