package it.polimi.ingsw.ps13.view.server.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import it.polimi.ingsw.ps13.view.server.Handler;

/**
 * 
 *
 */
public class SocketHandler extends Handler implements Runnable {

	@SuppressWarnings("unused")
	private final Socket socket;
	@SuppressWarnings("unused")
	private ObjectInputStream ois;
	@SuppressWarnings("unused")
	private ObjectOutputStream oos;
	
	public SocketHandler(Socket socket) throws IOException {
		
		this.socket = socket;
		
		ois = new ObjectInputStream(socket.getInputStream());
		oos = new ObjectOutputStream(socket.getOutputStream());
		
	}
	
	/**
	 * 
	 */
	@Override
	public void run() {
		
		// implement this method
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
}
