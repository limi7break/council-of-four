package it.polimi.ingsw.ps13.message.response.unicast;

import it.polimi.ingsw.ps13.message.response.ResponseMsg;

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
