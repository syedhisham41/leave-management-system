package exceptions.exception;

public class LeaveIdDoesntExistException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public LeaveIdDoesntExistException (String message, Throwable cause) {
		super(message);
	}
}
