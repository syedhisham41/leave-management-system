package exceptions.exception;

public class LeaveIdMissingException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public LeaveIdMissingException (String message, Throwable cause) {
		super(message);
	}
}
