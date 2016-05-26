package it.polimi.ingsw.ps13.view.client;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps13.view.client.cli.ClientCLI;
import it.polimi.ingsw.ps13.view.client.gui.ClientGUI;
import it.polimi.ingsw.ps13.view.client.rmi.ClientRMI;
import it.polimi.ingsw.ps13.view.client.socket.ClientSocket;

/**
 * Entry point for a game client.
 *
 */
public class GameClient {
	
	private static final Logger LOG = Logger.getLogger(GameClient.class.getSimpleName());
	private static final Scanner scanner = new Scanner(System.in);
	
	private GameClient() { }
	
	ClientView view;
	ClientConnection connection;
	
	/**
	 * Starts the right client based on user choice.
	 */
	public static void main(String[] args) {
		
		GameClient client = new GameClient();
		
		try {
			client.startConnection();
		} catch(IOException e) {
			LOG.log(Level.SEVERE, "There was a problem while trying to establish a connection with the server.", e);
		}
			
		//client.startUserInterface();
        client.startCLI();
        
	}
	
	private void startCLI() {
		
		view = new ClientCLI(connection);
    	new Thread(view).start();
		
	}
	
	public void startConnection() throws IOException {

        String connectionType;

        do {
            System.out.println("Enter RMI or SOCKET to choose the connection type:");
            connectionType = scanner.nextLine();
        } while (!connectionType.matches("^(RMI|SOCKET)$"));

        if (connectionType.matches("^(RMI)$")) {
        	
        	connection = new ClientRMI();
        	
        } else {
        	
        	connection = new ClientSocket();
        	
        }
		
	}
	
	public void startUserInterface() {
		
		String interfaceType;

        do {
            System.out.println("Enter CLI or GUI to choose the interface type:");
            interfaceType = scanner.nextLine();
        } while (!interfaceType.matches("^(CLI|GUI)$"));

        if (interfaceType.matches("^(GUI)$")) {
        	
        	view = new ClientGUI(connection);
        	new Thread(view).start();
        	
        } else {
        	
        	view = new ClientCLI(connection);
        	new Thread(view).start();
        	
        }
		
	}

}
