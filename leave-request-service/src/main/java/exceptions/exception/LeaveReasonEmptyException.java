package exceptions.exception;

public class LeaveReasonEmptyException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public LeaveReasonEmptyException (String message, Throwable cause) {
		super(message);
	}
}
