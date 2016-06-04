package it.polimi.ingsw.ps13.view.server.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

import it.polimi.ingsw.ps13.message.request.RequestMsg;

@FunctionalInterface
public interface RMIHandlerRemote extends Remote {
	
	public void processRequest(RequestMsg msg) throws RemoteException;
	
}
