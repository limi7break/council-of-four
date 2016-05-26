package it.polimi.ingsw.ps13.message.request;

import java.io.Serializable;

/**
 * This class represents a generic request message from the client to the server.
 * The client must identify himself by using its name, unique inside each game.
 *
 */
public abstract class RequestMsg implements Serializable {

	public static final long serialVersionUID = 0L;
	
	private String playerName;

	protected RequestMsg() { }
	
	public String getPlayerName() {
		
		return playerName;
		
	}
	
	public void setPlayerName(String playerName) {
		
		this.playerName = playerName;
		
	}
	
}
