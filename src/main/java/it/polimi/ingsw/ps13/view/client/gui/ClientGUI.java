package it.polimi.ingsw.ps13.view.client.gui;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;

import it.polimi.ingsw.ps13.model.Game;
import it.polimi.ingsw.ps13.view.client.ClientConnection;
import it.polimi.ingsw.ps13.view.client.ClientView;
import it.polimi.ingsw.ps13.view.client.gui.component.GUICreator;
import it.polimi.ingsw.ps13.view.client.gui.component.GUIPanel;

public class ClientGUI extends JFrame implements ClientView {
	
	private static final long serialVersionUID = 0L;

	private static final Logger LOG = Logger.getLogger(ClientGUI.class.getSimpleName());
	
	private final GUICreator guiCreator;
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
		
		guiCreator = new GUICreator();
		
	}
	
	/**
	 * 
	 */
	@Override
	public void showModel() {
		
		JLayeredPane layers = new JLayeredPane();
		layers.setLayout(new BorderLayout());
		
		GUIPanel newPane = guiCreator.createGUI(game);
		newPane.setBounds(0, 0, 1440, 900);
		newPane.setTransparent(true);
		layers.setLayer(newPane, 1);
		layers.add(newPane);
		
		this.setContentPane(layers);
		
		pack();
		
		GUIPanel connectionPane = guiCreator.createConnections(newPane, game);
		connectionPane.setBounds(0, 0, 1440, 900);
		connectionPane.setOpaque(true);
		layers.setLayer(connectionPane, 0);
		layers.add(connectionPane);
		
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
