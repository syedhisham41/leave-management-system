package exceptions.exception;

public class InvalidAuditActionTypeException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InvalidAuditActionTypeException (String message, Throwable cause) {
		super(message);
	}
}
