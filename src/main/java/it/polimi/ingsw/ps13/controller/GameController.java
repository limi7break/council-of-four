package it.polimi.ingsw.ps13.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import it.polimi.ingsw.ps13.controller.actions.Action;
import it.polimi.ingsw.ps13.controller.actions.ActionFactory;
import it.polimi.ingsw.ps13.controller.actions.ActionVisitor;
import it.polimi.ingsw.ps13.controller.actions.PassTurnAction;
import it.polimi.ingsw.ps13.message.request.ChatRequestMsg;
import it.polimi.ingsw.ps13.message.request.RequestMsg;
import it.polimi.ingsw.ps13.message.request.action.ActionRequestMsg;
import it.polimi.ingsw.ps13.message.response.ResponseMsg;
import it.polimi.ingsw.ps13.message.response.UpdateResponseMsg;
import it.polimi.ingsw.ps13.message.response.multicast.ChatMulticastMsg;
import it.polimi.ingsw.ps13.message.response.multicast.MulticastMsg;
import it.polimi.ingsw.ps13.message.response.unicast.ConnectionUnicastMsg;
import it.polimi.ingsw.ps13.message.response.unicast.UnicastMsg;
import it.polimi.ingsw.ps13.model.Game;
import it.polimi.ingsw.ps13.model.player.Player;
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
	
	private final ActionVisitor actionFactory;
	
	private final List<String> players;
	
	/**
	 * Creates a new game with the default configuration file.
	 * 
	 * @param configFilePath
	 */
	public GameController() {
		
		this.configFilePath = DEFAULT_CONFIG;
		players = new ArrayList<>();
		
		actionFactory = new ActionFactory();
		
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
		
		// Notify current player that it's time to play
		notifyObserver(new UnicastMsg("It\'s YOUR turn, biatch! Bring it on!!", game.getCurrentPlayerName()));
		notifyObserver(new MulticastMsg(game.getCurrentPlayerName() + "\'s turn.", game.getCurrentPlayerName()));
		
	}
	
	/**
	 * Adds a player to this game, only if it hasn't started yet.
	 * 
	 * @param name
	 */
	protected void addPlayer(String name) {
		
		if (game == null) {
			players.add(name);
			notifyObserver(new MulticastMsg(name + " entered the room." , name));
			notifyObserver(new ConnectionUnicastMsg("Welcome " + name + "! Have fun.", name));
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
		
		if (msg instanceof ActionRequestMsg) {
			ActionRequestMsg actionMsg = (ActionRequestMsg) msg;
			handleActionMsg(actionMsg);
			
		}
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
	private void handleActionMsg(ActionRequestMsg msg) {
		
		if (msg.getPlayerName() == game.getCurrentPlayerName()) {
			
			Action action = msg.accept(actionFactory);
			
			if (action.isLegal(game)) {
				
				action.apply(game);
				notifyObserver(new UpdateResponseMsg(msg.getPlayerName() + " successfully performed " + action.getClass().getSimpleName() + ". Model updated.", game));
				
				if (action instanceof PassTurnAction) {
					notifyObserver(new UnicastMsg("It\'s YOUR turn, biatch! Bring it on!!", game.getCurrentPlayerName()));
					notifyObserver(new MulticastMsg(game.getCurrentPlayerName() + "\'s turn.", game.getCurrentPlayerName()));
					
					if (game.isFinished()) {
						notifyObserver(new ResponseMsg("GAME FINISHED! THE WINNER IS " + calculateWinner() + "! CONGRATULATIONS!!"));
					}
				}
				
			} else {
				notifyObserver(new UnicastMsg("ERROR: action is not legal :(", msg.getPlayerName()));
			}
				
		} else {
			notifyObserver(new UnicastMsg("ERROR: it\'s not your turn.", msg.getPlayerName()));
		}
		
	}
	
	private String calculateWinner() {
		
		String winner = "";
		
		int maxVictoryPoints = 0;
		for (Player p : game.getPlayers().values()) {
			if (p.getVictoryPoints() > maxVictoryPoints)
				winner = p.getName();
		}
		
		return winner;
		
	}
	
}
