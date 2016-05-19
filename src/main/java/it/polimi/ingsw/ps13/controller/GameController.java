package it.polimi.ingsw.ps13.controller;

import java.awt.Color;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import it.polimi.ingsw.ps13.model.ColorFactory;
import it.polimi.ingsw.ps13.model.board.Board;
import it.polimi.ingsw.ps13.model.board.BoardFactory;

/**
 * Controller for a single game.
 *
 */
public class GameController {

	private static final Logger LOG = Logger.getLogger(GameController.class.getName());
	private static final Map<String, Color> colors = new HashMap<>();
	private Document config;
	private String configFilePath = "config.xml";		// ask user for config file path...
	@SuppressWarnings("unused")
	private Board board;
	
	/**
	 * 
	 * @param configFilePath
	 */
	public GameController(String configFilePath) {
		
		this.configFilePath = configFilePath;
		
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
		
		ColorFactory.createColors(colors, config);
		board = BoardFactory.createBoard(config);
		
	}
	
	/**
	 * 
	 * @return a map with every color imported for the game
	 */
	public static Map<String, Color> getColors() {
		
		return Collections.unmodifiableMap(colors);
		
	}
	
}
