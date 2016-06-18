package it.polimi.ingsw.ps13.message.response.unicast;

import it.polimi.ingsw.ps13.message.response.ResponseMsg;

/**
 * This class represents a generic response unicast message.
 * 
 * When the game controller notifies the handlers with a unicast msg,
 * it is sent only to the client whose player name is specified
 * in the message.
 *
 */
public class UnicastMsg extends ResponseMsg {

	private static final long serialVersionUID = 0L;
	
	private final String recipient;
	
	/**
	 * Creates a new UnicastMsg with the specified message and recipient.
	 * 
	 * @param message the content of the message
	 * @param recipient the recipient of the message
	 */
	public UnicastMsg(String message, String recipient) {
		
		super(message);
		this.recipient = recipient;
		
	}
	
	/**
	 * Returns the recipient of the message.
	 * 
	 * @return the recipient of the message
	 */
	public String getRecipient() {
		
		return recipient;
		
	}
	
}
