package it.polimi.ingsw.ps13.controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This controller handles multiple games.
 * 
 * 
 */
public class GamesController {
	
	private static final Logger LOG = Logger.getLogger(GamesController.class.getName());
	
	public static final int PORT = 1337;
	private boolean stopped = false;
	
	private final Set<GameController> games;
	
	private GameController waitingGame;
	// @TODO: ask user for config file path, choosing among a list of different ones available on the server
	private String configFilePath = "config.xml";
	
	public GamesController() { 
	
		games = new HashSet<>();
		createNewGame();
	
	}
	
	/**
	 * @TODO: This method will be removed: this class is not the entry point for the
	 * server side, it's here for initial testing.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		
		GamesController server = new GamesController();
		LOG.log(Level.INFO, "Welcome to the Council of Four!");
		
		try {
			server.acceptConnections();
		} catch (IOException e) {
			LOG.log(Level.SEVERE, "Unexpected exception while initializing the server.", e);
		}
		
	}
	
	/**
	 * @TODO: create dedicated thread for this, with a new Runnable class.
	 * 
	 * @throws IOException
	 */
	public void acceptConnections() throws IOException {
		
		ServerSocket ssock = new ServerSocket(PORT);
		
		LOG.log(Level.INFO, "Accepting connections on port 1337...");
		
		while (!stopped) {
			Socket sock = ssock.accept();
			LOG.log(Level.INFO, "Connection accepted.");
			ClientHandler handler = new ClientHandler(sock, waitingGame);
			new Thread(handler).start();
		}
		
		ssock.close();
		
	}
	
	/**
	 * Creates a new waiting game and adds it to the set.
	 * 
	 */
	public void createNewGame() {
		
		waitingGame = new GameController(configFilePath, this);
		games.add(waitingGame);
		LOG.log(Level.INFO, "New game created.");
		
	}
	
	/**
	 * Stops the server.
	 * This method will be moved to the "connection acceptor"
	 * 
	 */
	public void stop() {
		
		stopped = true;
		
	}
	
}
