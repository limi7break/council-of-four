package it.polimi.ingsw.ps13.controller;

import java.awt.Color;
import java.util.Map;
import java.util.HashMap;
import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import it.polimi.ingsw.ps13.model.ColorFactory;

/**
 * Controller for a single game.
 *
 */
public class GameController {

	private Document config;
	private String configFilePath = "config.xml";		// ask user for config file path...
	private final Map<String, Color> colors;			// name of the colors is stored in lowercase
	
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
			e.printStackTrace();
		}
		
		ColorFactory.createColors(colors, config);
		
		
	}
	
}
