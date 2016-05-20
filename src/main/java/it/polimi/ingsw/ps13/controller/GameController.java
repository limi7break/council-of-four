package it.polimi.ingsw.ps13.controller;

import java.io.File;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import it.polimi.ingsw.ps13.model.Game;
import it.polimi.ingsw.ps13.model.player.Player;

/**
 * Controller for a single game.
 *
 */
public class GameController {

	private static final Logger LOG = Logger.getLogger(GameController.class.getName());
	private Document config;
	private String configFilePath = "config.xml";		// default configuration file path
	private final Map<String, Player> players;
	@SuppressWarnings("unused")
	private Game game;
	
	/**
	 * 
	 * @param configFilePath
	 */
	public GameController(String configFilePath, Map<String, Player> players) {
		
		// @TODO: ask user for configuration file path
		this.configFilePath = configFilePath;
		this.players = players;
		
	}
	
	/**
	 * Parses the XML configuration file, initializes the game model
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
		
	}
	
}
