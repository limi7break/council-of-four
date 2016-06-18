package it.polimi.ingsw.ps13.view.client;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.SwingUtilities;

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
	private final Scanner scanner = new Scanner(System.in);
	
	private final ClientView view;
	private final ClientConnection connection;
	
	/**
	 * Asks user to choose desired UI and connection.
	 * 
	 */
	private GameClient() { 
		
		view = chooseUserInterface();
		connection = chooseConnection();
		
	}
	
	/**
	 * Starts the client based on user's UI and connection choices.
	 * 
	 */
	public static void main(String[] args) {
		
		GameClient client = new GameClient();
		client.init();
        
	}
	
	/**
	 * Makes user choose between RMI and SOCKET and tries to establish a connection with the server.
	 * 
	 * @return the initialized connection object
	 */
	public ClientConnection chooseConnection() {

        String connectionType;
        ClientConnection conn = null;

        do {
            System.out.println("Enter RMI or SOCKET to choose the connection type:");
            connectionType = scanner.nextLine();
        } while (!connectionType.matches("^(RMI|SOCKET)$"));

        try {
	        if (connectionType.matches("^(RMI)$")) {
	        	conn = new ClientRMI();
	        } else {
	        	conn = new ClientSocket();
	        }
        } catch(IOException | NotBoundException | AlreadyBoundException e) {
			LOG.log(Level.SEVERE, "There was a problem while trying to establish a connection with the server.", e);
        }
        
        return conn;
		
	}
	
	/**
	 * Makes user choose between CLI and GUI and creates a runnable view object. 
	 * 
	 * @return the runnable created view object
	 */
	public ClientView chooseUserInterface() {
		
		String interfaceType;

        do {
            System.out.println("Enter CLI or GUI to choose the interface type:");
            interfaceType = scanner.nextLine();
        } while (!interfaceType.matches("^(CLI|GUI)$"));

        if (interfaceType.matches("^(GUI)$")) {
        	return new ClientGUI();
        } else {
        	return new ClientCLI();
        }
		
	}
	
	/**
	 * Passes the connection to the view and starts it.
	 * 
	 */
	public void init() {
		
		view.setConnection(connection);
		
		if (view instanceof ClientGUI) {
			SwingUtilities.invokeLater(view);
		} else {
			new Thread(view).start();
		}
	}

}
