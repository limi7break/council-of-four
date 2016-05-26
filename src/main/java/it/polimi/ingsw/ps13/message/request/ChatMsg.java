package it.polimi.ingsw.ps13.message.request;

public class ChatMsg extends RequestMsg {
	
	private static final long serialVersionUID = 0L;

	private final String message;
	
	public ChatMsg(String message) {
		
		this.message = message;
		
	}
	
	public String getMessage() {
		
		return message;
		
	}
	
}
