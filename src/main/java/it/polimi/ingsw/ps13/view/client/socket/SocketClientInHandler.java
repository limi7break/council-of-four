package it.polimi.ingsw.ps13.view.client.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps13.message.response.ResponseMsg;

/**
 * This thread constantly waits for some response message to be received from the server
 * and handles it according to the type of the response.
 *
 */
public class SocketClientInHandler implements Runnable {
	
	private static final Logger LOG = Logger.getLogger(SocketClientInHandler.class.getSimpleName());
	private final ObjectInputStream ois;
	
	private boolean running;
	
	protected SocketClientInHandler(ObjectInputStream ois) {
		
		this.ois = ois;
		
	}
	
	/**
	 * 
	 */
	@Override
	public void run() {
		
		running = true;
		
		while (running) {
			try {
				@SuppressWarnings("unused")
				ResponseMsg msg = (ResponseMsg) ois.readObject();
				
				// handle received ResponseMsg
			} catch(IOException | ClassNotFoundException e) {
				LOG.log(Level.WARNING, "There was a problem receiving data from the server.", e);
			}
		}
		
	}
	
	public void stop() {
		
		running = false;
		
	}
	
}
