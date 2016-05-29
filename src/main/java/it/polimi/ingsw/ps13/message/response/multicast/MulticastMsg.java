package it.polimi.ingsw.ps13.message.response.multicast;

import it.polimi.ingsw.ps13.message.response.ResponseMsg;

/**
 * This class represents a generic response multicast message.
 * 
 * When the game controller notifies the handlers with a multicast msg,
 * it is sent to every client connected to a handler, EXCEPT the one
 * whose player name is contained in the message.
 *
 */
public class MulticastMsg extends ResponseMsg {

	private static final long serialVersionUID = 0L;
	
	private final String playerName;
	
	public MulticastMsg(String message, String playerName) {
		
		super(message);
		this.playerName = playerName;
		
	}
	
	public String getPlayerName() {
		
		return playerName;
		
	}
	
}
