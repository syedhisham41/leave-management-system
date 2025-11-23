package exceptions.exception;

public class InvalidAuditLeaveDaysException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InvalidAuditLeaveDaysException (String message, Throwable cause) {
		super(message);
	}
}
