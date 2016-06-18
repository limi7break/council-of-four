package it.polimi.ingsw.ps13.view.client;

import it.polimi.ingsw.ps13.message.request.RequestMsg;
import it.polimi.ingsw.ps13.message.response.ResponseMsg;

/**
 * This interface is implemented by every connection a client can use.
 * 
 * A client connection should be able to receive response messages and
 * send request messages.
 *
 */
public interface ClientConnection {

	/**
	 * Returns the received response message from the server.
	 * 
	 * @return the received response message from the server.
	 */
	public ResponseMsg receiveMessage();
	
	/**
	 * Sends the request message to the server.
	 * 
	 * @param msg the request message to send to the server
	 */
	public void sendMessage(RequestMsg msg);
	
	/**
	 * Returns true if the connection is active.
	 * 
	 * @return true if the connection is active
	 */
	public boolean isActive();
	
}
