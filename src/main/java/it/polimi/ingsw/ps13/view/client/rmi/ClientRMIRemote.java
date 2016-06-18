package it.polimi.ingsw.ps13.view.client.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

import it.polimi.ingsw.ps13.message.response.ResponseMsg;

/**
 * This is the remote interface for the RMI client.
 * The methods declared in this interface are visible to the server, and it can call them remotely.
 *
 */
@FunctionalInterface
public interface ClientRMIRemote extends Remote {

	/**
	 * Called by the server on the client stub to send a response message.
	 * 
	 * @param msg the response message sent from the server to the client 
	 * @throws RemoteException
	 */
	void updateClient(ResponseMsg msg) throws RemoteException;
	
}
