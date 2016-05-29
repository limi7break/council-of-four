package it.polimi.ingsw.ps13.message.response.unicast;

import it.polimi.ingsw.ps13.message.response.ResponseMsg;

/**
 * This class represents a generic response unicast message.
 * 
 * When the game controller notifies the handlers with a unicast msg,
 * it is sent only to the client whose player name is contained
 * in the message.
 *
 */
public class UnicastMsg extends ResponseMsg {

	private static final long serialVersionUID = 0L;
	
	private final String playerName;
	
	public UnicastMsg(String message, String playerName) {
		
		super(message);
		this.playerName = playerName;
		
	}
	
	public String getPlayerName() {
		
		return playerName;
		
	}
	
}
