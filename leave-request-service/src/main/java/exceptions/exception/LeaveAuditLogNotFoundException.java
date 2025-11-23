package exceptions.exception;

public class LeaveAuditLogNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public LeaveAuditLogNotFoundException (String message, Throwable cause) {
		super(message);
	}
}
