package it.polimi.ingsw.ps13.message.request;

/**
 * This message is sent from the client when the player wants to send a chat message.
 *
 */
public class ChatRequestMsg extends RequestMsg {
	
	private static final long serialVersionUID = 0L;

	private final String message;
	
	/**
	 * Creates a new ChatRequestMsg with the given message.
	 * 
	 * @param message the content of the chat message
	 */
	public ChatRequestMsg(String message) {
		
		this.message = message;
		
	}
	
	/**
	 * Returns the content of the chat message.
	 * 
	 * @return the content of the chat message
	 */
	public String getMessage() {
		
		return message;
		
	}
	
}
