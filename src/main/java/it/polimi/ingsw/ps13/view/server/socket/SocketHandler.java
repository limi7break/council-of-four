package it.polimi.ingsw.ps13.view.server.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps13.message.request.DisconnectRequestMsg;
import it.polimi.ingsw.ps13.message.request.RequestMsg;
import it.polimi.ingsw.ps13.message.response.ResponseMsg;
import it.polimi.ingsw.ps13.message.response.multicast.MulticastMsg;
import it.polimi.ingsw.ps13.message.response.unicast.RenameUnicastMsg;
import it.polimi.ingsw.ps13.message.response.unicast.UnicastMsg;
import it.polimi.ingsw.ps13.view.server.Handler;

/**
 * This class represents a single socket connection on the server.
 * 
 * When a request msg is received, it's passed to the game controller via notify.
 * The controller then notifies the handler with a response msg, which is sent to the client. 
 *
 * This handler sends response messages to the client through the socket's output stream
 * and receives request messages through the socket's input stream.
 *
 */
public class SocketHandler extends Handler implements Runnable {
	
	private static final Logger LOG = Logger.getLogger(SocketHandler.class.getSimpleName());

	private final ObjectInputStream ois;
	private final ObjectOutputStream oos;
	
	private String playerName;
	
	private boolean running;
	
	/**
	 * Creates a new SocketHandler with the specified player name (unique identifier).
	 * 
	 * @param socket the socket used for communication with the client
	 * @param playerName unique identifier of the player using this connection
	 * @throws IOException
	 */
	public SocketHandler(Socket socket, String playerName) throws IOException {
		
		oos = new ObjectOutputStream(socket.getOutputStream());
		oos.flush();
		ois = new ObjectInputStream(socket.getInputStream());
		
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
	public synchronized void update(ResponseMsg msg) {

		if (running && 
			!( (msg instanceof MulticastMsg && ((MulticastMsg) msg).getExcludedPlayer() == playerName)
			|| (msg instanceof UnicastMsg && ((UnicastMsg) msg).getRecipient() != playerName))) {
			
			if (msg instanceof RenameUnicastMsg) {
				this.playerName = ((RenameUnicastMsg)msg).getNewName();
			}
			
			try {
				oos.reset();
				oos.writeObject(msg);
				oos.flush();
	
			} catch (IOException e) {
				LOG.log(Level.WARNING, "A problem was encountered while sending data to the client. (" + playerName + ")", e);
				stop();
			}
		}
		
	}
	
	/**
	 * Request msg is received and passed to the game controller via notify.
	 * 
	 */
	@Override
	public void run() {
		
		running = true;
		
		while (running) {
			try {
				RequestMsg msg = (RequestMsg) ois.readObject();
				msg.setPlayerName(playerName);
				
				notifyObserver(msg);

			} catch (IOException e) {
				stop();
				notifyObserver(new DisconnectRequestMsg(playerName));
				LOG.log(Level.INFO, playerName + " disconnected.", e);
			} catch (ClassNotFoundException e) {
				LOG.log(Level.WARNING, "A problem was encountered while reading data from the client. (" + playerName + ")", e);
			}
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
