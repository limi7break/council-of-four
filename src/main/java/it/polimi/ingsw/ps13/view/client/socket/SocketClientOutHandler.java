package it.polimi.ingsw.ps13.view.client.socket;

import java.io.ObjectOutputStream;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * This thread converts user input into an appropriate message to be sent
 * to the server.
 *
 */
public class SocketClientOutHandler implements Runnable {
	
	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(SocketClientOutHandler.class.getSimpleName());
	@SuppressWarnings("unused")
	private final ObjectOutputStream oos;
	
	private boolean running;
	
	protected SocketClientOutHandler(ObjectOutputStream oos) {
		
		this.oos = oos;
		
	}
	
	/**
	 * 
	 */
	@Override
	public void run() {
		
		running = true;
		
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		
		while (running) {
			@SuppressWarnings("unused")
			String input = scanner.nextLine();
			
			// handle user input, define commands
		}
		
	}
	
	public void stop() {
		
		running = false;
		
	}
	
}
