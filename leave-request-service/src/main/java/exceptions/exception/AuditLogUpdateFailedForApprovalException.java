package exceptions.exception;

public class AuditLogUpdateFailedForApprovalException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AuditLogUpdateFailedForApprovalException (String message, Throwable cause) {
		super(message);
	}
}
