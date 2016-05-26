package it.polimi.ingsw.ps13.view.server.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps13.message.request.RequestMsg;
import it.polimi.ingsw.ps13.message.response.ResponseMsg;
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
	
	public SocketHandler(Socket socket) throws IOException {
		
		ois = new ObjectInputStream(socket.getInputStream());
		oos = new ObjectOutputStream(socket.getOutputStream());
		
	}
	
	/**
	 * Response msg coming from the game controller is sent to the client.
	 * 
	 */
	@Override
	public void update(ResponseMsg msg) {
		
		try {
			oos.writeObject(msg);
			oos.flush();

		} catch (IOException e) {
			LOG.log(Level.WARNING, "A problem was encountered while sending data to the client.", e);
		}
		
	}
	
	/**
	 * Request msg is received and passed to the game controller via notify.
	 * 
	 */
	@Override
	public void run() {
		
		while (true) {
			try {
				RequestMsg msg = (RequestMsg) ois.readObject();
				
				this.notifyObserver(msg);

			} catch (IOException | ClassNotFoundException e) {
				LOG.log(Level.WARNING, "A problem was encountered while reading data from the client.", e);
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
	
}
