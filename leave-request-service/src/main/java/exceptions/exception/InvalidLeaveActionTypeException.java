package exceptions.exception;

public class InvalidLeaveActionTypeException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InvalidLeaveActionTypeException (String message, Throwable cause) {
		super(message);
	}
}
