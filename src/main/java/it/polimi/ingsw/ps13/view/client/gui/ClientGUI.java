package it.polimi.ingsw.ps13.view.client.gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JPanel;

import it.polimi.ingsw.ps13.model.Game;
import it.polimi.ingsw.ps13.view.client.ClientConnection;
import it.polimi.ingsw.ps13.view.client.ClientView;
import it.polimi.ingsw.ps13.view.client.gui.component.GUICreator;

public class ClientGUI extends JFrame implements ClientView {
	
	private static final long serialVersionUID = 0L;

	private static final Logger LOG = Logger.getLogger(ClientGUI.class.getSimpleName());
	
	@SuppressWarnings("unused")
	private transient ClientConnection connection;
	
	private Game game;

	/**
	 * Launch the GUI.
	 */
	@Override
	public void run() {
		
		showModel();
		
		try {
			setVisible(true);
		} catch (Exception e) {
			LOG.log(Level.WARNING, "There was a problem while initializing the GUI.", e);
		}
		
	}

	/**
	 * Create the frame.
	 */
	public ClientGUI() {
		
		// ONLY FOR TESTING!!
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("default.cof")));
			game = (Game) ois.readObject();
			ois.close();
		} catch(ClassNotFoundException | IOException e) {
			LOG.log(Level.WARNING, "A problem was encountered while importing the game", e);
		}
		
		setResizable(true);
		setTitle("Council of Four");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(50, 50, 1440, 900);
		
	}
	
	/**
	 * 
	 */
	@Override
	public void showModel() {
		
		JPanel newPane = GUICreator.createGUI(game);
		setContentPane(newPane);
		revalidate();
		repaint();
		
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
	
	/**
	 * @TODO: Remove this method sooner or later. ONLY FOR TESTING!!
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		ClientGUI gui = new ClientGUI();
		new Thread(gui).start();
		
	}

}
