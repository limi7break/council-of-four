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
	
	private static final Logger LOG = Logger.getLogger(SocketClient.class.getSimpleName());
	public static final String HOST = "localhost";
	public static final int PORT = 1337;
	
	private final Socket socket;
	
	/**
	 * A new socket connection is created.
	 * 
	 * @throws IOException
	 */
	public SocketClient() throws IOException {
		
		socket = new Socket(HOST, PORT);
		
		LOG.log(Level.INFO, "Connection established @ " + HOST + ":" + PORT + ".");
		
	}
	
	/**
	 * Two threads are started: one handles response messages received from the server
	 * while the other converts user input into appropriate request messages to be sent
	 * to the server.
	 * 
	 */
	@Override
	public void run() {
		
		try {
			new Thread(new SocketClientInHandler(new ObjectInputStream(socket.getInputStream()))).start();
			new Thread(new SocketClientOutHandler(new ObjectOutputStream(socket.getOutputStream()))).start();
		} catch(IOException e) {
			LOG.log(Level.SEVERE, "There was a problem while creating I/O client handlers.", e);
		}
		
	}
	
}
