package it.polimi.ingsw.ps13.message.response;

import java.io.Serializable;

/**
 * This class represents a generic response broadcast message.
 * 
 * When the game controller notifies the handlers with a response msg,
 * it is broadcast to every client connected to a handler.
 *
 */
public class ResponseMsg implements Serializable {
	
	public static final long serialVersionUID = 0L;
	
	private final String message;
	
	/**
	 * Creates a new ResponseMsg with the specified message content.
	 * 
	 * @param message the content of the message
	 */
	public ResponseMsg(String message) {
		
		this.message = message;
		
	}
	
	/**
	 * Returns the content of the message.
	 * 
	 * @return the content of the message
	 */
	public String getMessage() {
		
		return message;
		
	}
	
}
