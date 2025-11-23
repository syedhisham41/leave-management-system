package exceptions.exception;

public class InvalidApproverIdException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InvalidApproverIdException (String message, Throwable cause) {
		super(message);
	}
}
