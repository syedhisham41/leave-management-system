package exceptions.exception;

public class InsufficientPasswordLengthException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InsufficientPasswordLengthException(String message, Throwable cause) {
		super(message, cause);
	}

}
