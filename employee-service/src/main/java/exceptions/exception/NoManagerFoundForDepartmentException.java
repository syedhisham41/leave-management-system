package exceptions.exception;

public class NoManagerFoundForDepartmentException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoManagerFoundForDepartmentException(String message, Throwable cause) {
		super(message, cause);
	}
}
