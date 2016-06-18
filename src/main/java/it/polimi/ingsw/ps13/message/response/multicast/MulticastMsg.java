package it.polimi.ingsw.ps13.message.response.multicast;

import it.polimi.ingsw.ps13.message.response.ResponseMsg;

/**
 * This class represents a generic response multicast message.
 * 
 * When the game controller notifies the handlers with a multicast msg,
 * it is sent to every client connected to a handler, EXCEPT the one
 * whose player name is specified in the message.
 * 
 * It is useful when an event occurs such that everyone has to be notified, but
 * only a specific player should have a custom notify message. In this case,
 * an unicast message is sent to that player, and a multicast message is sent to
 * every other. 
 *
 */
public class MulticastMsg extends ResponseMsg {

	private static final long serialVersionUID = 0L;
	
	private final String excludedPlayer;
	
	/**
	 * Creates a new MulticastMsg with the specified message and excluded player name.
	 * 
	 * @param message the content of the message
	 * @param excludedPlayer unique identifier of the player who should NOT receive the message
	 */
	public MulticastMsg(String message, String excludedPlayer) {
		
		super(message);
		this.excludedPlayer = excludedPlayer;
		
	}
	
	/**
	 * Returns the unique identifier of the player who should NOT receive the message.
	 * 
	 * @return unique identifier of the player who should NOT receive the message
	 */
	public String getExcludedPlayer() {
		
		return excludedPlayer;
		
	}
	
}
