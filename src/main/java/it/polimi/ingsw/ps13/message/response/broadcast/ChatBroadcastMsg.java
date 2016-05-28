package it.polimi.ingsw.ps13.message.response.broadcast;

import it.polimi.ingsw.ps13.message.response.ResponseMsg;

/**
 * This message has to be broadcasted to every client except the one who sent the message.
 *
 */
public class ChatBroadcastMsg extends ResponseMsg {

	private static final long serialVersionUID = 0L;
	
	private final String playerName;
	
	public ChatBroadcastMsg(String playerName, String message) {
		
		super(message);
		this.playerName = playerName;
		
	}
	
	public String getPlayerName() {
		
		return playerName;
		
	}
	
}
