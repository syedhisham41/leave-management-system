package exceptions.exception;

public class NoManagerFoundForEmployeeException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoManagerFoundForEmployeeException(String message, Throwable cause) {
		super(message, cause);
	}
}
