package exceptions.exception;

public class LeaveRequestApprovalFailedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public LeaveRequestApprovalFailedException (String message, Throwable cause) {
		super(message);
	}
}
