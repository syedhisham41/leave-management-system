package exceptions.exception;

public class LeaveAuditLogTableEmptyException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public LeaveAuditLogTableEmptyException (String message, Throwable cause) {
		super(message);
	}
}
