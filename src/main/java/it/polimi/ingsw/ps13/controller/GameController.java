package it.polimi.ingsw.ps13.controller;

import java.awt.Color;
import java.util.Map;
import java.util.HashMap;
import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import it.polimi.ingsw.ps13.model.ColorFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller for a single game.
 *
 */
public class GameController {

	private static final Logger LOG = Logger.getLogger(GameController.class.getName());
	private Document config;
	private String configFilePath = "config.xml";		// ask user for config file path...
	private final Map<String, Color> colors;
	
	/**
	 * 
	 * @param configFilePath
	 */
	public GameController(String configFilePath) {
		
		this.configFilePath = configFilePath;
		colors = new HashMap<>();
		
	}
	
	/**
	 * Parses the XML configuration file, initializes the game model and
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
		
		
	}
	
}
