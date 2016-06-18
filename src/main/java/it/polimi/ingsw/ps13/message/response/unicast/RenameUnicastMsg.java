package it.polimi.ingsw.ps13.message.response.unicast;

/**
 * This message is sent from the server as a response to a correct RenameRequestMsg.
 * 
 * The recipient specified in this message is used by the client handler to correctly direct the message.
 * 
 * The newName specified in this message is used by both the client handler and the client  
 * to update a variable containing the player name.
 * This way, the client handler "knows" the new name of the player and can correctly direct future unicast
 * and multicast messages, and the client "knows" which data should be displayed to the user.
 *
 */
public class RenameUnicastMsg extends UnicastMsg {

	private static final long serialVersionUID = 0L;
	
	private final String newName;

	/**
	 * Creates a new RenameUnicastMsg with the specified message, recipient and new player name.
	 * 
	 * @param message the content of the message
	 * @param recipient the recipient of the message
	 * @param newName new unique identifier chosen by the player 
	 */
	public RenameUnicastMsg(String message, String recipient, String newName) {
		
		super(message, recipient);
		this.newName = newName;
		
	}
	
	/**
	 * Returns the new unique identifier chosen by the player.
	 * 
	 * @return new unique identifier chosen by the player
	 */
	public String getNewName() {
		
		return newName;
		
	}

}
