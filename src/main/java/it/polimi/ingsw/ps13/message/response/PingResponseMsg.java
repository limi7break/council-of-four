package it.polimi.ingsw.ps13.message.response;

/**
 * This message is sent from the server when the game is just about to start.
 * It is usually ignored by the client.
 * 
 * It helps identifying disconnected RMI clients in order to remove them from
 * the game before it starts. 
 *
 */
public class PingResponseMsg extends ResponseMsg {

	private static final long serialVersionUID = 0L;

	/**
	 * Creates a PingResponseMsg.
	 * The content of the message is empty by default.
	 * 
	 */
	public PingResponseMsg() {
		
		super("");
		
	}

}
