package it.polimi.ingsw.ps13.message.response;

public class ChatResponseMsg extends ResponseMsg {

	private static final long serialVersionUID = 0L;

	private final String playerName;
	
	public ChatResponseMsg(String message, String playerName) {
		
		super(message);
		this.playerName = playerName;
		
	}
	
	public String getPlayerName() {
		
		return playerName;
		
	}
	
}
