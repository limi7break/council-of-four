package it.polimi.ingsw.ps13.view.client.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps13.message.request.RequestMsg;
import it.polimi.ingsw.ps13.message.response.ResponseMsg;
import it.polimi.ingsw.ps13.view.client.ClientConnection;

/**
 * This object is created when the socket connection type is chosen.
 *
 */
public class ClientSocket implements ClientConnection {
	
	private static final Logger LOG = Logger.getLogger(ClientSocket.class.getSimpleName());
	public static final String HOST = "localhost";
	public static final int PORT = 1337;
	
	private final Socket socket;
	private final ObjectInputStream ois;
	private final ObjectOutputStream oos;
	
	private boolean active;
	
	/**
	 * Creates a new socket connection.
	 * 
	 * The constructor of this object tries to connect to the server socket and get the object input
	 * and output streams.
	 * 
	 * @throws IOException
	 */
	public ClientSocket() throws IOException {
		
		active = true;
		
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		System.out.print("IP Address: ");
		String host = scanner.nextLine();
		
		socket = new Socket(host, PORT);
		LOG.log(Level.INFO, "SOCKET Connection established @ " + host + ":" + PORT + ".");
		
		oos = new ObjectOutputStream(socket.getOutputStream());
		oos.flush();
		ois = new ObjectInputStream(socket.getInputStream());
		
	}

	/**
	 * This method is called by a thread which reads response messages from the server.
	 * It blocks on readObject, and returns as soon as a new message is received.
	 * 
	 * If the server disconnects, shuts this connection down by setting the active flag to false.
	 * 
	 */
	@Override
	public ResponseMsg receiveMessage() {
		
		ResponseMsg msg = null;
		
		try {
			
			msg = (ResponseMsg) ois.readObject();
			
		} catch (IOException e) {
			active = false;
			return new ResponseMsg("You have lost connection with the server!");
		} catch (ClassNotFoundException e) {
			LOG.log(Level.WARNING, "A problem was encountered while receiving data from the server.", e);
		}
		
		return msg;
		
	}

	/**
	 * Sends a request message to the server through the object output stream.
	 * 
	 */
	@Override
	public synchronized void sendMessage(RequestMsg msg) {
		
		if (!active) {
			throw new IllegalStateException("Connection is closed!");
		}
		
		try {
			oos.reset();
			oos.writeObject(msg);
			oos.flush();
			
		} catch(IOException e) {
			LOG.log(Level.WARNING, "A problem was encountered while sending data to the server.", e);
		}
		
	}
	
	/**
	 * Returns true if the connection is active.
	 * 
	 * @return true if the connection is active
	 */
	@Override
	public boolean isActive() {
		
		return active;
		
	}
	
}
