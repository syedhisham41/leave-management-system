package exceptions.exception;

public class InvalidDepartmentIdException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidDepartmentIdException(String message, Throwable cause) {
		super(message, cause);
	}
}
