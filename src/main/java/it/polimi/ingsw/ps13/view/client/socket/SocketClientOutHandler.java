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
	
	private static final Logger LOG = Logger.getLogger(SocketClientOutHandler.class.getSimpleName());
	private final ObjectOutputStream oos;
	
	protected SocketClientOutHandler(ObjectOutputStream oos) {
		
		this.oos = oos;
		
	}
	
	/**
	 * 
	 */
	@Override
	public void run() {
		
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		
		while (true) {
			
			String input = scanner.nextLine();
			
			// handle user input, define commands
			
		}
		
	}
	
}
