package exceptions.exception;

public class InvalidHalfDayTypeException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InvalidHalfDayTypeException (String message, Throwable cause) {
		super(message);
	}
}
