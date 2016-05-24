package it.polimi.ingsw.ps13.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import it.polimi.ingsw.ps13.model.Game;

/**
 * Controller for a single game.
 * When two players join the game, a countdown is started to start the game.
 * If in the meantime someone else tries to connect the timer resets and starts over.
 * If the number of waiting players reaches 8 the game starts instantly.
 *
 */
public class GameController implements Runnable {

	private static final Logger LOG = Logger.getLogger(GameController.class.getName());
	private static final int COUNTDOWN = 10;
	
	private Document config;
	private String configFilePath = "config.xml";		// default configuration file path
	
	private Game game;
	
	private Timer timer;
	private TimerTask timerTask;
	
	private final GamesController server;
	private final Map<String, ClientHandler> players;
	
	/**
	 * 
	 * @param configFilePath
	 */
	public GameController(String configFilePath, GamesController server) {
		
		// @TODO: ask user for configuration file path
		timer = new Timer();
		this.configFilePath = configFilePath;
		this.server = server;
		players = new HashMap<>();
		
	}
	
	/**
	 * 
	 */
	@Override
	public void run() {
		
		initGame();
		broadcast();
		
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
		
		game = new Game(config, new ArrayList<>(players.keySet()));
		
	}
	
	/**
	 * Sends current version of the model to every client connected to the game.
	 * 
	 */
	public void broadcast() {
		
		for (ClientHandler client : players.values()) {
			
			client.sendGame();
			
		}
		
	}
	
	public Map<String, ClientHandler> getPlayers() {
		
		return Collections.unmodifiableMap(players);
	
	}
	
	public void addPlayer(String name, ClientHandler handler) {
		
		players.put(name, handler);
        LOG.log(Level.INFO, "New player added to waiting game.");

        if ((players.size() >= 2) && (players.size() <= 7)) {

            timer.cancel();

            timerTask = new TimerTask() {
                @Override
                public void run() {
                    try {

                        new Thread(GameController.this).start();
                        server.createNewGame();

                    } catch (Exception e) {
                        LOG.log(Level.SEVERE, "Unexpected exception while creating a new game.", e);
                    }
                }
            };

            timer = new Timer();

            timer.schedule(timerTask, (long) COUNTDOWN * 1000);

        } else if (players.size() == 8) {

            timerTask.cancel();
            timer.cancel();
            // Start game instantly
            timer.schedule(timerTask, 0);

        }
		
	}
	
	public Game getGame() {
		
		return game;
		
	}
	
	public void setGame(Game game) {
		
		this.game = game;
		
	}
	
}
