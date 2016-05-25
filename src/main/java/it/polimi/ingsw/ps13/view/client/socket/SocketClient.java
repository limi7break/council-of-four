package it.polimi.ingsw.ps13.view.client.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This client is started when the socket connection type is chosen.
 *
 */
public class SocketClient implements Runnable {
	
	public static final String HOST = "localhost";
	public static final int PORT = 1337;

	private static final Logger LOG = Logger.getLogger(SocketClient.class.getSimpleName());
	
	/**
	 * Socket connection is created and two threads are started:
	 * one for creating messages towards the server and one for
	 * receiving and handling messages from the server.
	 * 
	 */
	@Override
	public void run() {
		
		try {
			@SuppressWarnings("resource")
			Socket socket = new Socket(HOST, PORT);
			
			LOG.log(Level.INFO, "Connection established @ " + HOST + ":" + PORT + ".");
			
			new Thread(new SocketClientInHandler(new ObjectInputStream(socket.getInputStream()))).start();
			new Thread(new SocketClientOutHandler(new ObjectOutputStream(socket.getOutputStream()))).start();
		} catch(IOException e) {
			LOG.log(Level.SEVERE, "There was a problem while establishing a connection to the server.", e);
		}
		
	}
	
}
