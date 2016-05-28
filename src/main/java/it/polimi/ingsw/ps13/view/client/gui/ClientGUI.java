package it.polimi.ingsw.ps13.view.client.gui;

import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps13.model.Game;
import it.polimi.ingsw.ps13.view.client.ClientConnection;
import it.polimi.ingsw.ps13.view.client.ClientView;

public class ClientGUI implements ClientView {
	
	private static final Logger LOG = Logger.getLogger(ClientGUI.class.getSimpleName());
	
	private ClientConnection connection;

	private Game game;

	@Override
	public void run() {
		
		// implement GUI
		LOG.log(Level.WARNING, "GUI has not been implemented yet.");
		
		startHandlers();
		
	}
	
	/**
	 * 
	 */
	@Override
	public void showModel() {
		
		// implement this method
		
	}
	
	/**
	 * 
	 */
	public void handleMessage(String msg) {
		
		// implement this method (GUIMessageVisitor?)
		
	}
	
	public void startHandlers() {
		
		// implement this method
		
	}

	@Override
	public void setConnection(ClientConnection connection) {
		 
		this.connection = connection;
		
	}
	
}
