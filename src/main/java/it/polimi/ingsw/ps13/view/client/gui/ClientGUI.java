package it.polimi.ingsw.ps13.view.client.gui;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.SwingUtilities;

import it.polimi.ingsw.ps13.message.request.ChatRequestMsg;
import it.polimi.ingsw.ps13.message.request.RequestMsg;
import it.polimi.ingsw.ps13.message.response.ChatResponseMsg;
import it.polimi.ingsw.ps13.message.response.PingResponseMsg;
import it.polimi.ingsw.ps13.message.response.ResponseMsg;
import it.polimi.ingsw.ps13.message.response.UpdateResponseMsg;
import it.polimi.ingsw.ps13.message.response.unicast.ConnectionUnicastMsg;
import it.polimi.ingsw.ps13.model.Game;
import it.polimi.ingsw.ps13.view.client.ClientConnection;
import it.polimi.ingsw.ps13.view.client.ClientView;
import it.polimi.ingsw.ps13.view.client.gui.component.GUICreator;
import it.polimi.ingsw.ps13.view.client.gui.component.GUIForm;
import it.polimi.ingsw.ps13.view.client.gui.component.GUIMarket;
import it.polimi.ingsw.ps13.view.client.gui.component.GUIPanel;
import net.miginfocom.swing.MigLayout;

public class ClientGUI extends JFrame implements ClientView {
	
	private static final long serialVersionUID = 0L;

	private static final Logger LOG = Logger.getLogger(ClientGUI.class.getSimpleName());
	
	private final GUIForm form;
	private final transient GUICreator guiCreator;
	private GUIMarket market;

	private transient ClientConnection connection;
	
	private Game game;
	private String playerName;

	/**
	 * Create the frame.
	 */
	public ClientGUI() {
		
		setResizable(true);
		setTitle("Council of Four");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		guiCreator = new GUICreator();
		
		form = new GUIForm();
		form.getTextField().addActionListener(ae -> {
			
	        RequestMsg msg = new ChatRequestMsg(form.getTextField().getText());
	        form.getTextField().setText("");
	        
	        connection.sendMessage(msg);
	        
	        form.getTextField().requestFocusInWindow();
			
		});
		
	}
	
	/**
	 * Launch the GUI.
	 */
	@Override
	public void run() {
		
		init();
		startHandlers();
		
		try {
			setUndecorated(true);
			setVisible(true);
			form.getTextField().requestFocusInWindow();
		} catch (Exception e) {
			LOG.log(Level.WARNING, "There was a problem while initializing the GUI.", e);
		}
		
	}
	
	/**
	 * Create an initial panel without loading any game.
	 * At this point, game should be null so there is nothing to show.
	 * 
	 */
	public void init() {
		
		GUIPanel initialPanel = new GUIPanel();
		initialPanel.setLayout(new MigLayout("fill, flowy", "", ""));
		
		JLabel label = new JLabel("WAITING FOR GAME TO START...");
		label.setHorizontalAlignment(JLabel.CENTER);
		initialPanel.add(label);
		
		// Create and set text area and text field
		initialPanel.add(form, "growx");
		
		this.setContentPane(initialPanel);
		revalidate();
		repaint();
		
		setSize(800, 600);
		setLocationRelativeTo(null);
		
	}
	
	/**
	 * 
	 */
	@Override
	public void showModel() {
		
		JLayeredPane layers = new JLayeredPane();
		layers.setLayout(new BorderLayout());
		
		GUIPanel mainPane = guiCreator.createMainPane(game);
		mainPane.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
		mainPane.setTransparent(true);
		
		GUIPanel rightPane = guiCreator.createRightPane(game, form, playerName, connection);
		mainPane.add(rightPane, "cell 1 0, spany 3, top");
		
		layers.setLayer(mainPane, 1);
		layers.add(mainPane);
		
		this.setContentPane(layers);
		
		pack();
		
		GUIPanel connectionPane = guiCreator.createConnections(mainPane, game);
		connectionPane.setOpaque(true);
		layers.setLayer(connectionPane, 0);
		layers.add(connectionPane);
		
		revalidate();
		repaint();
		
		form.getTextField().requestFocusInWindow();
		
		if (game.isSellMarketPhase() && game.getPlayer(playerName).getTokens().getSell() > 0) {
			market = new GUIMarket(game.getPlayer(playerName), game.getMarket(), connection);
			market.showSellPhase();
		}
		else if (game.isBuyMarketPhase() && game.getPlayer(playerName).getTokens().getBuy() > 0) {
			market = new GUIMarket(game.getPlayer(playerName), game.getMarket(), connection);
			market.showBuyPhase();
		}
		
	}
	
	/**
	 * 
	 */
	public void handleMessage(ResponseMsg msg) {
		
		if (msg instanceof UpdateResponseMsg) {
			UpdateResponseMsg updateMsg = (UpdateResponseMsg)msg;
			
			if (this.game == null) {
				setExtendedState(JFrame.MAXIMIZED_BOTH);
			}
			
			if (market != null && market.isVisible()) {
				SwingUtilities.invokeLater(market::dispose);
			}
			
			this.game = updateMsg.getGame();
			showModel();
			
			form.append(updateMsg.getMessage());
		}
		else if (msg instanceof ChatResponseMsg) {
			ChatResponseMsg chatMsg = (ChatResponseMsg) msg;
			form.appendChat(chatMsg.getPlayerName(), chatMsg.getMessage());
		}
		else if (msg instanceof ConnectionUnicastMsg) {
			ConnectionUnicastMsg connMsg = (ConnectionUnicastMsg) msg;
			this.playerName = connMsg.getPlayerName();
			form.append(connMsg.getMessage());
		}
		else if (msg instanceof PingResponseMsg) {
			// Ping from the server is ignored
		}
		else {
			form.append(msg.getMessage());
		}
		
	}
	
	public void startHandlers() {
		
		// This is the input handler
		new Thread(() -> {
			while(connection.isActive()) {
				
				ResponseMsg msg = connection.receiveMessage();
				handleMessage(msg);
				
			}
		}).start();
		
	}

	@Override
	public void setConnection(ClientConnection connection) {
		 
		this.connection = connection;
		
	}
	
}
