package it.polimi.ingsw.ps13.message.response;

/**
 * This message is sent from the server to notify every player of a chat message.
 *
 */
public class ChatResponseMsg extends ResponseMsg {

	private static final long serialVersionUID = 0L;

	private final String sender;
	
	/**
	 * Creates a new ChatResponseMsg with the specified message and sender.
	 * 
	 * @param message the content of the message
	 * @param sender the sender of the message
	 */
	public ChatResponseMsg(String message, String sender) {
		
		super(message);
		this.sender = sender;
		
	}
	
	/**
	 * Returns the sender of the message.
	 * 
	 * @return the sender of the message
	 */
	public String getSender() {
		
		return sender;
		
	}
	
}
