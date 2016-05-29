package it.polimi.ingsw.ps13.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import it.polimi.ingsw.ps13.message.request.ChatRequestMsg;
import it.polimi.ingsw.ps13.message.request.RequestMsg;
import it.polimi.ingsw.ps13.message.response.ResponseMsg;
import it.polimi.ingsw.ps13.message.response.UpdateResponseMsg;
import it.polimi.ingsw.ps13.message.response.multicast.ChatMulticastMsg;
import it.polimi.ingsw.ps13.message.response.multicast.MulticastMsg;
import it.polimi.ingsw.ps13.model.Game;
import it.polimi.ingsw.ps13.util.observer.Observable;
import it.polimi.ingsw.ps13.util.observer.Observer;

/**
 * Controller for a single game.
 *
 */
public class GameController extends Observable<ResponseMsg> implements Observer<RequestMsg>, Runnable {

	private static final Logger LOG = Logger.getLogger(GameController.class.getName());
	private static final String DEFAULT_CONFIG = "config.xml";
	
	private Document config;
	
	private Game game;
	private final String configFilePath;
	
	private final List<String> players;
	
	/**
	 * Creates a new game with the default configuration file.
	 * 
	 * @param configFilePath
	 */
	public GameController() {
		
		this.configFilePath = DEFAULT_CONFIG;
		players = new ArrayList<>();
		
	}
	
	/**
	 * 
	 */
	@Override
	public void run() {
		
		initGame();
		
	}
	
	/**
	 * Parses the XML configuration file and initializes the game model.
	 * 
	 */
	public void initGame() {
		
		try {
			File configFile = new File(configFilePath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			config = dBuilder.parse(configFile);
			config.getDocumentElement().normalize();
		} catch(Exception e) {
			LOG.log(Level.SEVERE, "There was a problem reading the configuration file.", e);
		}
		
		game = new Game(config, players);
		
		// Send created game to every client
		notifyObserver(new UpdateResponseMsg("Initial game broadcast.", game));
		
	}
	
	/**
	 * Adds a player to this game, only if it hasn't started yet.
	 * 
	 * @param name
	 */
	protected void addPlayer(String name) {
		
		if (game == null) {
			players.add(name);
			notifyObserver(new MulticastMsg(name + " entered the room." ,name));
		}
		
	}
	
	/**
	 * Getter for game status.
	 * 
	 * @return
	 */
	public Game getGame() {
		
		return game;
		
	}
	
	/**
	 * 
	 */
	@Override
	public void update(RequestMsg msg) {
		
		// handle request
		
		if (msg instanceof ChatRequestMsg) {
			ChatRequestMsg chatMsg = (ChatRequestMsg) msg;
			notifyObserver(new ChatMulticastMsg(chatMsg.getMessage(), chatMsg.getPlayerName()));
		}
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
}
