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

/**
 * This class represents a single RMI connection on the server.
 * 
 * When a request msg is received, it's passed to the game controller via notify.
 * The controller then notifies the handler with a response msg, which is sent to the client. 
 *
 * This handler sends response messages to the client through the client stub's
 * updateClient method, and receives request messages through the remote processRequest
 * method.
 * 
 */
public class RMIHandler extends Handler implements RMIHandlerRemote, Serializable {

	private static final long serialVersionUID = 0L;

	private static final Logger LOG = Logger.getLogger(RMIHandler.class.getSimpleName());
	
	private final transient ClientRMIRemote clientStub;
	private String playerName;
	
	private boolean running;
	
	/**
	 * Creates a new RMIHandler with the specified player name (unique identifier).
	 * 
	 * @param clientStub the client stub used for sending response messages
	 * @param playerName unique identifier of the player using this connection
	 */
	public RMIHandler(ClientRMIRemote clientStub, String playerName) {
		
		this.clientStub = clientStub;
		this.playerName = playerName;
		
		running = true;
		
	}
	
	/**
	 * Response msg coming from the game controller is sent to the client.
	 * 
	 * Unicast messages are sent only to the recipient.
	 * Multicast messages are sent to everyone except the excluded player.
	 * 
	 */
	@Override
	public void update(ResponseMsg msg) {

		if (running &&
			!( (msg instanceof MulticastMsg && ((MulticastMsg) msg).getExcludedPlayer() == playerName)
			|| (msg instanceof UnicastMsg && ((UnicastMsg) msg).getRecipient() != playerName))) {
			
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
		
		if (running) {
			msg.setPlayerName(playerName);
			notifyObserver(msg);
		}
		
	}
	
	/**
	 * Stops the handler.
	 * The handler will not be able to send or receive messages.
	 * 
	 */
	public void stop() {
		
		running = false;
		
	}

}
