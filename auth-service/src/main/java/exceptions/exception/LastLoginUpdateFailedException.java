package exceptions.exception;

public class LastLoginUpdateFailedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LastLoginUpdateFailedException(String message, Throwable cause) {
		super(message, cause);
	}
}
