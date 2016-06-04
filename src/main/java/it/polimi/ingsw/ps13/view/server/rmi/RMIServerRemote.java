package it.polimi.ingsw.ps13.view.server.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

import it.polimi.ingsw.ps13.view.client.rmi.ClientRMIRemote;

public interface RMIServerRemote extends Remote {

	public RMIHandlerRemote connect(ClientRMIRemote clientStub) throws RemoteException;
	
}
