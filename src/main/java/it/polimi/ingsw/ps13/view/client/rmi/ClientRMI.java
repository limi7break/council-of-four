package it.polimi.ingsw.ps13.view.client.rmi;

import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps13.message.request.RequestMsg;
import it.polimi.ingsw.ps13.message.response.ResponseMsg;
import it.polimi.ingsw.ps13.view.client.ClientConnection;

/**
 * This client is started when the RMI connection type is chosen.
 *
 */
public class ClientRMI implements ClientConnection {
	
	public static final String HOST = "localhost";
	public static final int PORT = 7777;
	
	private static final Logger LOG = Logger.getLogger(ClientRMI.class.getSimpleName());

	public ClientRMI() {
		
		// initialize RMI connection
		LOG.log(Level.WARNING, "RMI has not been implemented yet.");
		
	}
	
	@Override
	public ResponseMsg receiveMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendMessage(RequestMsg msg) {
		// TODO Auto-generated method stub
		
	}
	
}
