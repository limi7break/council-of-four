package it.polimi.ingsw.ps13.view.client;

import java.util.Scanner;

import it.polimi.ingsw.ps13.view.client.rmi.RMIClient;
import it.polimi.ingsw.ps13.view.client.socket.SocketClient;

/**
 * Entry point for a game client.
 *
 */
public class GameClient {
	
	/**
	 * Starts the right client based on user choice.
	 */
	public static void main(String[] args) {
		
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);

        String connectionType;

        do {
            System.out.println("Enter RMI or SOCKET to choose the connection type:");
            connectionType = scanner.nextLine();
        } while (!connectionType.matches("^(RMI|SOCKET)$"));

        if (connectionType.matches("^(RMI)$")) {
        	new Thread(new RMIClient()).start();
        } else {
        	new Thread(new SocketClient()).start();
        }
        
	}

}
