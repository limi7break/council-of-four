package it.polimi.ingsw.ps13.message.response.multicast;

/**
 * This message has to be broadcasted to every player except the one who sent the message.
 * That's why it's a multicast response msg. (See MulticastMsg description for details)
 *
 */
public class ChatMulticastMsg extends MulticastMsg {

	private static final long serialVersionUID = 0L;
	
	public ChatMulticastMsg(String message, String playerName) {
		
		super(message, playerName);
		
	}
	
}
