package it.polimi.ingsw.ps13.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import it.polimi.ingsw.ps13.model.Game;

/**
 * Controller for a single game.
 *
 */
public class GameController {

	private static final Logger LOG = Logger.getLogger(GameController.class.getName());
	private Document config;
	private String configFilePath = "config.xml";		// default configuration file path
	private final Set<String> players;
	@SuppressWarnings("unused")
	private Game game;
	
	/**
	 * 
	 * @param configFilePath
	 */
	public GameController(String configFilePath, Set<String> players) {
		
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
		
		game = new Game(config, new ArrayList<String>(players));
		
	}
	
}
