package it.polimi.ingsw.ps13.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class represents a client connection with an ObjectInputStream and
 * ObjectOutputStream pair.
 *
 */
public class ClientHandler implements Runnable {
	
	private static final Logger LOG = Logger.getLogger(ClientHandler.class.getName());
	
	private final GameController game;
	
	private final ObjectInputStream ois;
	private final ObjectOutputStream oos;
	
	public ClientHandler(Socket sock, GameController game) throws IOException {
		
		this.game = game;
		
		ois = new ObjectInputStream(sock.getInputStream());
		oos = new ObjectOutputStream(sock.getOutputStream());
		
	}
	
	@Override
	public void run() {
		
		String username = greetPlayer();
		game.addPlayer(username, this);
		
	}
	
	/**
	 * Registers the player to the current game and makes sure the chosen username
	 * is not already in use in the current game.
	 * 
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public String greetPlayer() {
		
		String username;
		boolean alreadyInUse;
		
		try {
			do {
				username = (String) ois.readObject();
				alreadyInUse = (game.getPlayers().keySet()).contains(username);
				
				if (!alreadyInUse) {
					oos.writeObject("Connection accepted. :)");
				} else {
					oos.writeObject("ERROR: username " + username + " is already in use :)");
				}
				
			} while(alreadyInUse);
			
			oos.writeObject("Welcome " + username + "!!");
			return username;
		} catch(Exception e) {
			LOG.log(Level.SEVERE, "There was a problem during the greeting phase.", e);
		}
		
		return null;
		
	}
	
	public void sendGame() {
		
		try {
			oos.writeObject(game.getGame());
			oos.flush();
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "There was a problem while sending the game to the client.", e);
		}
		
	}
	
}