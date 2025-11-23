package exceptions.exception;

public class InvalidAuditIdException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InvalidAuditIdException (String message, Throwable cause) {
		super(message);
	}
}
