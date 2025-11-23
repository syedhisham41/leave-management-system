package exceptions.exception;

public class InvalidLeaveIdException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InvalidLeaveIdException (String message, Throwable cause) {
		super(message);
	}
}
