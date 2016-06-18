package it.polimi.ingsw.ps13.view.server.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

import it.polimi.ingsw.ps13.view.client.rmi.ClientRMIRemote;

/**
 * This is the remote interface for the RMI server.
 * The methods declared in this interface are visible to the client, and it can call them remotely.
 *
 */
@FunctionalInterface
public interface RMIServerRemote extends Remote {

	/**
	 * Called by the client on the server stub to get the handler stub.
	 * 
	 * @param clientStub the client stub the server will use to send response messages to the client
	 * @return the handler stub the client will use to send request messages to the server
	 * @throws RemoteException
	 */
	public RMIHandlerRemote connect(ClientRMIRemote clientStub) throws RemoteException;
	
}
