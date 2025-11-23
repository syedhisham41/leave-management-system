package exceptions.exception;

public class UserRecordDeleteFailedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserRecordDeleteFailedException(String message, Throwable cause) {
		super(message, cause);
	}
}
