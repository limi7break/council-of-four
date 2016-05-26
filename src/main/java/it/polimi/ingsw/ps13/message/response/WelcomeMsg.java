package it.polimi.ingsw.ps13.message.response;

public class WelcomeMsg implements ResponseMsg {
	
	private static final long serialVersionUID = 0L;
	private final String playerName;
	
	public WelcomeMsg(String playerName) {
		
		this.playerName = playerName;
		
	}
	
	public String getPlayerName() {
		
		return playerName;
		
	}
	
	@Override
	public void display() {
		
		System.out.println(playerName + " entered the room.");
		
	}
	
}
