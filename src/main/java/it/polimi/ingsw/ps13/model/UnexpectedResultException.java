package it.polimi.ingsw.ps13.model;

/**
 * This excpetion is thrown if checking the result of any operation goes wrong.
 * (e.g. creating and array whose size is different than expected)
 * 
 */
public class UnexpectedResultException extends RuntimeException {

	private static final long serialVersionUID = 0L;
	
	/**
	 * 
	 */
	public UnexpectedResultException(){
		
		super();
		
	}
	
	/**
	 * 
	 * @param message
	 */
	public UnexpectedResultException(String message){
		
		super(message);
		
	}
	
	/**
	 * 
	 * @param message
	 * @param cause
	 */
	public UnexpectedResultException(String message, Throwable cause) { 
		
		super(message, cause); 
		
	}
	
	/**
	 * 
	 * @param cause
	 */
	public UnexpectedResultException(Throwable cause) {
		  
		  super(cause);
		  
	}
		
}
