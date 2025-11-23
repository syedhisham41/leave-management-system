package exceptions.exception;

public class InvalidAuditManagerFlagException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InvalidAuditManagerFlagException (String message, Throwable cause) {
		super(message);
	}
}
