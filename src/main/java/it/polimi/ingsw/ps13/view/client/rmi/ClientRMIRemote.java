package it.polimi.ingsw.ps13.view.client.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

import it.polimi.ingsw.ps13.message.response.ResponseMsg;

@FunctionalInterface
public interface ClientRMIRemote extends Remote {

	void updateClient(ResponseMsg msg) throws RemoteException;
	
}
