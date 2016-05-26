package it.polimi.ingsw.ps13.view.client.cli;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps13.message.request.ChatMsg;
import it.polimi.ingsw.ps13.message.response.ResponseMsg;
import it.polimi.ingsw.ps13.view.client.ClientConnection;
import it.polimi.ingsw.ps13.view.client.ClientView;

public class ClientCLI implements ClientView {
	
	private static final Logger LOG = Logger.getLogger(ClientCLI.class.getSimpleName());
	private static final Scanner scanner = new Scanner(System.in);
	
	ClientConnection connection;
	
	public ClientCLI(ClientConnection connection) {
		
		this.connection = connection;
		
	}
	
	@Override
	public void run() {
		
		// implement CLI
		LOG.log(Level.WARNING, "CLI has not been implemented yet.");
		
		startHandlers();
		showModel();
		
	}
	
	/**
	 * 
	 */
	public void showModel() {
		
		System.out.println("showmodel called");
		
	}
	
	/**
	 * 
	 */
	public void handleMessage(ResponseMsg msg) {
		
		msg.display();
		
	}
	
	public void startHandlers() {
		
		// This is the output handler
		new Thread(() -> {
			while(true) {
				
				System.out.print("chat: ");
				connection.sendMessage(new ChatMsg(scanner.nextLine()));
				
			}
		}).start();
				
		// This is the input handler
		new Thread(() -> {
			while(true) {
				
				ResponseMsg msg = connection.receiveMessage();
				handleMessage(msg);
				
			}
		}).start();
		
	}
	
}
