package it.polimi.ingsw.ps13.view.server.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps13.message.request.RequestMsg;
import it.polimi.ingsw.ps13.message.response.ResponseMsg;
import it.polimi.ingsw.ps13.message.response.multicast.MulticastMsg;
import it.polimi.ingsw.ps13.message.response.unicast.UnicastMsg;
import it.polimi.ingsw.ps13.view.server.Handler;

/**
 * This class represents a single socket connection on the server.
 * 
 * When a request msg is received, it's passed to the game controller via notify.
 * The controller then notifies the handler with a response msg, which is sent to the client. 
 *
 */
public class SocketHandler extends Handler implements Runnable {
	
	private static final Logger LOG = Logger.getLogger(SocketHandler.class.getSimpleName());

	private final ObjectInputStream ois;
	private final ObjectOutputStream oos;
	
	private final String playerName;
	
	private boolean running;
	
	public SocketHandler(Socket socket, String playerName) throws IOException {
		
		oos = new ObjectOutputStream(socket.getOutputStream());
		oos.flush();
		ois = new ObjectInputStream(socket.getInputStream());
		
		this.playerName = playerName;
		
	}
	
	/**
	 * Response msg coming from the game controller is sent to the client.
	 * 
	 * If it is an unicast response msg and this IS NOT the recipient's handler, the message is not sent.
	 * If it is a multicast response msg and this IS the handler of the player name written on the message,
	 * the message is not sent.
	 * 
	 */
	@Override
	public void update(ResponseMsg msg) {
		
		// A MulticastMsg is sent to everyone except to the player whose name is written on the message
		// Only the recipient of a UnicastMsg receives it
		if (!( (msg instanceof MulticastMsg && ((MulticastMsg) msg).getPlayerName() == playerName)
			|| (msg instanceof UnicastMsg && ((UnicastMsg) msg).getPlayerName() != playerName))) {
		
			try {
				oos.reset();
				oos.writeObject(msg);
				oos.flush();
	
			} catch (IOException e) {
				LOG.log(Level.WARNING, "A problem was encountered while sending data to the client.", e);
			}
			
		}
		
	}
	
	/**
	 * 
	 */
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
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
				
				this.notifyObserver(msg);

			} catch (IOException | ClassNotFoundException e) {
				LOG.log(Level.WARNING, "A problem was encountered while reading data from the client.", e);
			}
		}
		
	}
	
	public void stop() {
		
		running = false;
		
	}
	
}
