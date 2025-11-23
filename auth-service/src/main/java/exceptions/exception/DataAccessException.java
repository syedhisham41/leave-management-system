package exceptions.exception;

public class DataAccessException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DataAccessException(String message, Throwable cause) {
		super(message, cause);
	}

}
