package it.polimi.ingsw.ps13.message.request;

import java.io.Serializable;

/**
 * This class represents a generic request message sent from the client to the server.
 * 
 * The client must identify himself by using its name, unique inside each game.
 * 
 * The message is created and sent with no player name set.
 * The player name is set by the client handler on the server side,
 * as soon as the message is received, before the controller is notified with the message.
 *
 */
public abstract class RequestMsg implements Serializable {

	public static final long serialVersionUID = 0L;
	
	private String playerName;

	protected RequestMsg() { }
	
	/**
	 * Returns the unique identifier of the player who sent this request message.
	 * 
	 * @return unique identifier of the player who sent this request message
	 */
	public String getPlayerName() {
		
		return playerName;
		
	}
	
	/**
	 * Sets unique identifier of the player who sent this request message.
	 * 
	 * @param playerName unique identifier of the player who sent this request message
	 */
	public void setPlayerName(String playerName) {
		
		this.playerName = playerName;
		
	}
	
}
