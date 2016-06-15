package it.polimi.ingsw.ps13.message.request;

public class DisconnectRequestMsg extends RequestMsg {

	private static final long serialVersionUID = 0L;

	public DisconnectRequestMsg(String playerName) {
		
		this.setPlayerName(playerName);
		
	}
	
}
