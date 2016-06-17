package it.polimi.ingsw.ps13.view.server.rmi;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps13.message.request.DisconnectRequestMsg;
import it.polimi.ingsw.ps13.message.request.RequestMsg;
import it.polimi.ingsw.ps13.message.response.ResponseMsg;
import it.polimi.ingsw.ps13.message.response.multicast.MulticastMsg;
import it.polimi.ingsw.ps13.message.response.unicast.RenameUnicastMsg;
import it.polimi.ingsw.ps13.message.response.unicast.UnicastMsg;
import it.polimi.ingsw.ps13.view.client.rmi.ClientRMIRemote;
import it.polimi.ingsw.ps13.view.server.Handler;

public class RMIHandler extends Handler implements RMIHandlerRemote, Serializable {

	private static final long serialVersionUID = 0L;

	private static final Logger LOG = Logger.getLogger(RMIHandler.class.getSimpleName());
	
	private final transient ClientRMIRemote clientStub;
	private String playerName;
	
	private boolean running;
	
	public RMIHandler(ClientRMIRemote clientStub, String playerName) {
		
		this.clientStub = clientStub;
		this.playerName = playerName;
		
		running = true;
		
	}
	
	@Override
	public void update(ResponseMsg msg) {
		
		// A MulticastMsg is sent to everyone except to the player whose name is written on the message
		// Only the recipient of a UnicastMsg receives it
		if (running &&
			!( (msg instanceof MulticastMsg && ((MulticastMsg) msg).getPlayerName() == playerName)
			|| (msg instanceof UnicastMsg && ((UnicastMsg) msg).getPlayerName() != playerName))) {
			
			if (msg instanceof RenameUnicastMsg) {
				this.playerName = ((RenameUnicastMsg)msg).getNewName();
			}
			
			try {
				
				clientStub.updateClient(msg);
				
			} catch (RemoteException e) {
				stop();
				notifyObserver(new DisconnectRequestMsg(playerName));
				LOG.log(Level.INFO, playerName + " disconnected.", e);
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
	
	@Override
	public void update() {

		// empty update not implemented
		
	}
	
	public void stop() {
		
		running = false;
		
	}

}
