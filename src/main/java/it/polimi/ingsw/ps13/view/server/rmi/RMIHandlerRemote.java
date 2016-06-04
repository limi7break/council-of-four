package it.polimi.ingsw.ps13.view.server.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

import it.polimi.ingsw.ps13.message.request.RequestMsg;

public interface RMIHandlerRemote extends Remote {
	
	/*public void registerClient(ClientRMIRemote clientStub) throws RemoteException;*/
	
	public void processRequest(RequestMsg msg) throws RemoteException;
	
}
