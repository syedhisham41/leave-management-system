package exceptions.exception;

public class InsufficientLeaveBalanceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InsufficientLeaveBalanceException (String message, Throwable cause) {
		super(message);
	}
}
