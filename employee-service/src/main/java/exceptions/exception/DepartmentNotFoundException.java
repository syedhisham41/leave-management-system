package exceptions.exception;

public class DepartmentNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DepartmentNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
