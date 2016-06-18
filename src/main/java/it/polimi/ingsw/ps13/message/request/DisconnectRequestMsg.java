package it.polimi.ingsw.ps13.message.request;

/**
 * This message is created by a client handler on the server (it is not sent from the client)
 * when it fails to communicate with the client. The controller is notified with this
 * message and reacts accordingly.
 *
 */
public class DisconnectRequestMsg extends RequestMsg {

	private static final long serialVersionUID = 0L;

	/**
	 * Creates a new DisconnectRequestMsg with the specified player name.
	 * 
	 * @param playerName unique identifier of the disconnected player
	 */
	public DisconnectRequestMsg(String playerName) {
		
		this.setPlayerName(playerName);
		
	}
	
}
