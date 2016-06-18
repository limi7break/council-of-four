package it.polimi.ingsw.ps13.message.request;

/**
 * This message is sent from the client when the player wants to change his player name before
 * the game starts.
 *
 */
public class RenameRequestMsg extends RequestMsg {
	
	private static final long serialVersionUID = 0L;
	
	private final String newName;

	/**
	 * Creates a new RenameRequestMsg with the specified new player name.
	 * 
	 * @param newName the new player name the player wants to set
	 */
	public RenameRequestMsg(String newName) {
		
		this.newName = newName;
		
	}
	
	/**
	 * Returns the new player name the player wants to set.
	 * 
	 * @return the new player name the player wants to set
	 */
	public String getNewName() {
		
		return newName;
		
	}

}
