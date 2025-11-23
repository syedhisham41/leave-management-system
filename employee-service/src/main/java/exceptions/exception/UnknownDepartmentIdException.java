package exceptions.exception;

public class UnknownDepartmentIdException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UnknownDepartmentIdException(String message, Throwable cause) {
		super(message, cause);
	}
}
