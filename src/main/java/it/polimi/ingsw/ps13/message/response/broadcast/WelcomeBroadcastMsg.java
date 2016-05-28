package it.polimi.ingsw.ps13.message.response.broadcast;

import it.polimi.ingsw.ps13.message.response.ResponseMsg;

public class WelcomeBroadcastMsg extends ResponseMsg {
	
	private static final long serialVersionUID = 0L;
	private final String playerName;
	
	public WelcomeBroadcastMsg(String playerName) {
		
		super(playerName + " entered the room.");
		this.playerName = playerName;
		
	}
	
	public String getPlayerName() {
		
		return playerName;
		
	}
	
}
