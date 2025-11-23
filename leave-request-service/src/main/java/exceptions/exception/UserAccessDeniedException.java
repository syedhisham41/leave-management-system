package exceptions.exception;

public class UserAccessDeniedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserAccessDeniedException(String message, Throwable cause) {
		super(message, cause);
	}
}
