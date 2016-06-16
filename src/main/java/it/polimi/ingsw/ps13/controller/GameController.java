package it.polimi.ingsw.ps13.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
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
import it.polimi.ingsw.ps13.message.request.DisconnectRequestMsg;
import it.polimi.ingsw.ps13.message.request.RequestMsg;
import it.polimi.ingsw.ps13.message.request.action.ActionRequestMsg;
import it.polimi.ingsw.ps13.message.response.ChatResponseMsg;
import it.polimi.ingsw.ps13.message.response.ResponseMsg;
import it.polimi.ingsw.ps13.message.response.UpdateResponseMsg;
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
	private static final int TIMEOUT = 10;
	
	private Document config;
	
	private Game game;
	private final String configFilePath;
	
	private Timer timer;
	private TimerTask timerTask;
	
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
		notifyObserver(new UpdateResponseMsg("=== !!! GAME STARTED !!! ===", game));
		notifyCurrentTurn();
		
		// Set timer for first turn
		setTimer();
		
	}
	
	/**
	 * Adds a player to this game, only if it hasn't started yet.
	 * 
	 * @param name
	 */
	protected void addPlayer(String name) {
		
		if (game == null) {
			int size = players.size();
			
			UnicastMsg otherPlayersMsg = new UnicastMsg(
					players.isEmpty() 	? "There are no other players in this room."
										: "There " 	+ (size==1 ? "is " : "are ") 
													+ size + " other player"
													+ (size==1 ? "" : "s")
													+ " in this room: " + players.toString(), name);
			
			players.add(name);
			notifyObserver(new MulticastMsg(name + " entered the room." , name));
			notifyObserver(new ConnectionUnicastMsg("Welcome " + name + "! Have fun.", name));
			notifyObserver(otherPlayersMsg);
			
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
			notifyObserver(new ChatResponseMsg(chatMsg.getMessage(), chatMsg.getPlayerName()));
		}
		
		else if (msg instanceof ActionRequestMsg) {
			ActionRequestMsg actionMsg = (ActionRequestMsg) msg;
			
			if (actionMsg.getPlayerName() == game.getCurrentPlayerName()) {
				Action action = actionMsg.accept(actionFactory);
				handleAction(action, msg.getPlayerName());
			} else {
				notifyObserver(new UnicastMsg("ERROR: It\'s not your turn!", msg.getPlayerName()));
			}
		}
		
		else if (msg instanceof DisconnectRequestMsg) {
			DisconnectRequestMsg disconnectMsg = (DisconnectRequestMsg) msg;
			
			String disconnectedPlayer = disconnectMsg.getPlayerName();
			game.getPlayer(disconnectedPlayer).setConnected(false);
			if (game.getConnectedPlayers() < 2) {
				timer.cancel();
				game.finalizeGame();
				notifyObserver(new UpdateResponseMsg("GAME FINISHED! THE WINNER IS " + calculateWinner() + "! CONGRATULATIONS!!", game));
				
				// @TODO: close game
			} else if (game.getCurrentPlayerName().equals(disconnectedPlayer)) {
				game.passTurn();
				notifyCurrentTurn();
				
				timer.cancel();
				setTimer();
			}
			
			notifyObserver(new ResponseMsg(disconnectedPlayer + " has disconnected!"));
		}
		
	}

	@Override
	public void update() {
		
		// empty update not implemented
		
	}
	
	private void handleAction(Action action, String playerName) {
		
		if (action.isLegal(game)) {
			
			action.apply(game);
			notifyObserver(new UpdateResponseMsg(playerName + " performed " + action.getClass().getSimpleName() + ".", game));
			
			if (action instanceof PassTurnAction) {
				if (!game.isFinished()) {
					notifyCurrentTurn();
					
					// Reset timer for next turn
					timer.cancel();
					setTimer();
				}
				else {
					timer.cancel();
					game.finalizeGame();
					notifyObserver(new UpdateResponseMsg("GAME FINISHED! THE WINNER IS " + calculateWinner() + "! CONGRATULATIONS!!", game));
				}
			}
			
		} else {
			notifyObserver(new UnicastMsg("ERROR: Action is not legal!", playerName));
		}

	}
	
	private void notifyCurrentTurn() {
		
		notifyObserver(new UnicastMsg("It\'s YOUR turn, " + game.getCurrentPlayerName() + "! Bring it on!!\n(but move your ass, you only have " + TIMEOUT + " seconds.)", game.getCurrentPlayerName()));
		notifyObserver(new MulticastMsg(game.getCurrentPlayerName() + "\'s turn.", game.getCurrentPlayerName()));
		
	}
	
	private void setTimer() {
		
		timer = new Timer();
		
		timerTask = new TimerTask() {
            @Override
            public void run() {
            	
            	String slowPlayer = game.getCurrentPlayerName();
            	
            	game.passTurn();
            	notifyObserver(new UpdateResponseMsg("Time\'s up for " + slowPlayer + "!", game));
            	notifyCurrentTurn();
            	
            	// Reset timer for next turn
            	timer.cancel();
            	setTimer();
            	
            }
        };
		
		timer.schedule(timerTask, (long) TIMEOUT * 1000);
		
	}
	
	private String calculateWinner() {
		
		String winner = "";
		
		int maxVictoryPoints = -1;
		for (Player p : game.getPlayers().values()) {
			if (p.getVictoryPoints() > maxVictoryPoints) {
				maxVictoryPoints = p.getVictoryPoints();
				winner = p.getName();
			}
			
			//draw contest
			if(p.getVictoryPoints() == maxVictoryPoints && p.getVictoryPoints() != 0) {
				
				winner = drawContest(p, game.getPlayer(winner));
				
			}
			
		}
		
		return winner;
		
	}
	
	/**
	 * Applies draw rules to calculate whose the winner in case of same amount of victory points.
	 * 
	 * @param p1
	 * @param p2
	 * @return
	 */
	private String drawContest(Player p1, Player p2) {
		
		int p1Stock = p1.getAssistants() + p1.getPoliticsCards().size();
		int p2Stock = p2.getAssistants() + p2.getPoliticsCards().size();
		
		if(p1Stock > p2Stock)
			return p1.getName();
		
		else 
			return p2.getName();		// game rules do not clarify how to behave in case of same amount of
										// assistants and politics cards in addition to victory points draw.
										// Therefore we choose p2 will be the winner in this case.
		
	}
	
}
