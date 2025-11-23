package exceptions.exception;

public class InvalidSaltForHashFunctionGenerationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidSaltForHashFunctionGenerationException(String message, Throwable cause) {
		super(message, cause);
	}

}
