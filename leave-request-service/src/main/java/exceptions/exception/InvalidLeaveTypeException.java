package exceptions.exception;

public class InvalidLeaveTypeException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InvalidLeaveTypeException (String message, Throwable cause) {
		super(message);
	}
}
