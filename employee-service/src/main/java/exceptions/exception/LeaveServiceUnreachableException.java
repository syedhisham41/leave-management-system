package exceptions.exception;

public class LeaveServiceUnreachableException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LeaveServiceUnreachableException(String message, Throwable cause) {
		super(message, cause);
	}

}
