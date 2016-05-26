package it.polimi.ingsw.ps13.message.response;

public class ChatBroadcastMsg implements ResponseMsg {

	private static final long serialVersionUID = 0L;
	
	private final String playerName;
	private final String message;
	
	public ChatBroadcastMsg(String playerName, String message) {
		
		this.playerName = playerName;
		this.message = message;
		
	}
	
	@Override
	public void display() {
		
		System.out.println("[" + playerName + "] " + message);
		
	}
	
}
