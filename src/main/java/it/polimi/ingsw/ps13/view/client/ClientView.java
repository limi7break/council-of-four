package it.polimi.ingsw.ps13.view.client;

/**
 * This interface is implemented by every view a client can use.
 * 
 * A client view should be able to show the model in some way and use a client
 * connection to send and receive messages to and from the server.
 *
 */
public interface ClientView extends Runnable {
	
	/**
	 * Shows the local current game model.
	 * 
	 */
	public void showModel();
	
	/**
	 * Sets the connection for the client.
	 * 
	 * @param connection the connection the client should use to communicate with the server
	 */
	public void setConnection(ClientConnection connection);
	
}
