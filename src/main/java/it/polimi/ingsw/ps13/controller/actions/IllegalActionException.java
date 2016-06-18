package it.polimi.ingsw.ps13.controller.actions;

/**
 * This exception is thrown by the isLegal method of every action, if the action is not legal.
 * 
 * It is used to notify the controller with the exact reason why the action is not legal.
 * The controller will then send the message to the player.
 *
 */
public class IllegalActionException extends Exception {

	private static final long serialVersionUID = 0L;

	/**
	 * Creates a new IllegalActionException.
	 * 
	 * @param message the message to be contained in the exception
	 */
	public IllegalActionException(String message) {
		
		super(message);
		
	}

}
