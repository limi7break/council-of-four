package it.polimi.ingsw.ps13.view.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps13.controller.GamesController;
import it.polimi.ingsw.ps13.view.server.socket.SocketHandler;

/**
 * Entry point for a game server.
 *
 */
public class GameServer {

	private static final Logger LOG = Logger.getLogger(GameServer.class.getSimpleName());
	
	private static final int PORT = 1337;
	@SuppressWarnings("unused")
	private static final int RMI_PORT = 7777;
	
	private final GamesController mainController;
	
	private boolean running;
	
	public GameServer() {
		
		running = true;
		mainController = new GamesController();
		
	}
	
	/**
	 * 
	 */
	public void startRMI() {
		
		// @TODO: implement RMI server
		
	}
	
	/**
	 * 
	 */
	public void startSocket() {
		
		try {
			ServerSocket serverSocket = new ServerSocket(PORT);
	
			LOG.log(Level.INFO, "SERVER SOCKET READY ON PORT " + PORT);
	
			// player number should reset when current game starts and a new game is created
			int playerNumber=0;
			
			while (running) {
				Socket socket = serverSocket.accept();
	
				playerNumber++;
				String playerName = "Giocatore " + playerNumber;
				
				SocketHandler handler = new SocketHandler(socket, playerName);
				
				// Apple style MVC?
				mainController.getWaitingGame().registerObserver(handler);
				handler.registerObserver(mainController.getWaitingGame());
				
				mainController.addPlayer(playerName);
				new Thread(handler).start();
			}
			
			serverSocket.close();
		} catch (IOException e) {
			LOG.log(Level.SEVERE, "A problem was encountered while creating socket handler.", e);
		}
		
	}
	
	/**
	 * 
	 */
	public static void main(String[] args) {

		GameServer server = new GameServer();
		server.startRMI();
		server.startSocket();
		
	}

}
