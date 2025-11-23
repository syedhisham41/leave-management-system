package exceptions.exception;

public class DbNotFoundException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DbNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
