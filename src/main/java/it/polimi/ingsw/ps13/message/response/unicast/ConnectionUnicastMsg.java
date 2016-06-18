package it.polimi.ingsw.ps13.message.response.unicast;

/**
 * This message is sent from the server as soon as a client connects and it is assigned
 * a default player name.
 * 
 * The recipient specified in this message is set in a variable by the client as soon as it is
 * received. This way, the client "knows" which data should be displayed to the user.
 *
 */
public class ConnectionUnicastMsg extends UnicastMsg {

	private static final long serialVersionUID = 0L;

	/**
	 * Creates a new ConnectionUnicastMsg with the specified message and recipient.
	 * 
	 * @param message the content of the message
	 * @param recipient the recipient of the message
	 */
	public ConnectionUnicastMsg(String message, String recipient) {
		
		super(message, recipient);
	
	}

}
