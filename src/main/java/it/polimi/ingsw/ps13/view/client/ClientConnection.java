package it.polimi.ingsw.ps13.view.client;

import it.polimi.ingsw.ps13.message.request.RequestMsg;
import it.polimi.ingsw.ps13.message.response.ResponseMsg;

public interface ClientConnection {

	public ResponseMsg receiveMessage();
	
	public void sendMessage(RequestMsg msg);
	
	public boolean isActive();
	
}
