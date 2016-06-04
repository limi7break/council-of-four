package it.polimi.ingsw.ps13.view.server.rmi;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps13.message.request.RequestMsg;
import it.polimi.ingsw.ps13.message.response.ResponseMsg;
import it.polimi.ingsw.ps13.message.response.multicast.MulticastMsg;
import it.polimi.ingsw.ps13.message.response.unicast.UnicastMsg;
import it.polimi.ingsw.ps13.view.client.rmi.ClientRMIRemote;
import it.polimi.ingsw.ps13.view.server.Handler;

public class RMIHandler extends Handler implements RMIHandlerRemote, Serializable {

	private static final long serialVersionUID = 0L;

	private static final Logger LOG = Logger.getLogger(RMIHandler.class.getSimpleName());
	
	private final ClientRMIRemote clientStub;
	private final String playerName;
	
	public RMIHandler(ClientRMIRemote clientStub, String playerName) {
		
		this.clientStub = clientStub;
		this.playerName = playerName;
		
	}
	
	@Override
	public void update(ResponseMsg msg) {
		
		// A MulticastMsg is sent to everyone except to the player whose name is written on the message
		// Only the recipient of a UnicastMsg receives it
		if (!( (msg instanceof MulticastMsg && ((MulticastMsg) msg).getPlayerName() == playerName)
			|| (msg instanceof UnicastMsg && ((UnicastMsg) msg).getPlayerName() != playerName))) {
				
			try {
				
				clientStub.updateClient(msg);
	
			} catch (RemoteException e) {
				LOG.log(Level.WARNING, "A problem was encountered while sending data to the client. (" + playerName + ")", e);
			}
			
		}
		
	}

	/**
	 * Request msg is received and passed to the game controller via notify.
	 * 
	 */
	@Override
	public void processRequest(RequestMsg msg) throws RemoteException {
		
		msg.setPlayerName(playerName);
		notifyObserver(msg);
		
	}

	/*@Override
	public void registerClient(ClientRMIRemote clientStub) throws RemoteException {
		
		if (this.clientStub == null) {
			
			this.clientStub = clientStub;
			
		} else {
			throw new RemoteException("A client stub is already set for this RMI handler.");
		}
		
	}*/
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
