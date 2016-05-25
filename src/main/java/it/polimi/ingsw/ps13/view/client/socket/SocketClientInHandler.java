package it.polimi.ingsw.ps13.view.client.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This thread constantly waits for some object to be received from the server
 * and handles it according to the type of the object.
 * 
 * Basically this class can handle Game objects (model updates) and messages
 * (error, ack...)
 *
 */
public class SocketClientInHandler implements Runnable {
	
	private static final Logger LOG = Logger.getLogger(SocketClientInHandler.class.getSimpleName());
	private final ObjectInputStream ois;
	
	protected SocketClientInHandler(ObjectInputStream ois) {
		
		this.ois = ois;
		
	}
	
	/**
	 * 
	 */
	@Override
	public void run() {
		
		while (true) {
			
			try {
				@SuppressWarnings("unused")
				Object o = ois.readObject();
				
				// handle received object (Game, Msg)
			} catch(IOException | ClassNotFoundException e) {
				LOG.log(Level.WARNING, "There was a problem receiving data from the server.", e);
			}
			
		}
		
	}
	
}
