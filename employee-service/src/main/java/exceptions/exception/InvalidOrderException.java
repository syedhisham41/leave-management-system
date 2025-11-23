package exceptions.exception;

public class InvalidOrderException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidOrderException(String message, Throwable cause) {
		super(message, cause);
	}
}
