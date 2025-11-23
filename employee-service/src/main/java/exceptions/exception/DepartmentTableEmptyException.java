package exceptions.exception;

public class DepartmentTableEmptyException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DepartmentTableEmptyException (String message) {
		super(message);
	}

}
