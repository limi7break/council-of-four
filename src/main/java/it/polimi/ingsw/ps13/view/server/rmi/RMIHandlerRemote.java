package it.polimi.ingsw.ps13.view.server.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

import it.polimi.ingsw.ps13.message.request.RequestMsg;

/**
 * This is the remote interface for the RMI handler.
 * The methods declared in this interface are visible to the client, and it can call them remotely.
 *
 */
@FunctionalInterface
public interface RMIHandlerRemote extends Remote {
	
	/**
	 * Called by the client on the handler stub to send a request message.
	 * 
	 * @param msg the request message sent from the client to the server
	 * @throws RemoteException
	 */
	public void processRequest(RequestMsg msg) throws RemoteException;
	
}
