package exceptions.exception;

public class LeaveRequestNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public LeaveRequestNotFoundException (String message, Throwable cause) {
		super(message);
	}
}
