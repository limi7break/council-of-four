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
import it.polimi.ingsw.ps13.controller.actions.IllegalActionException;
import it.polimi.ingsw.ps13.controller.actions.PassTurnAction;
import it.polimi.ingsw.ps13.message.request.ChatRequestMsg;
import it.polimi.ingsw.ps13.message.request.DisconnectRequestMsg;
import it.polimi.ingsw.ps13.message.request.RenameRequestMsg;
import it.polimi.ingsw.ps13.message.request.RequestMsg;
import it.polimi.ingsw.ps13.message.request.action.ActionRequestMsg;
import it.polimi.ingsw.ps13.message.response.ChatResponseMsg;
import it.polimi.ingsw.ps13.message.response.PingResponseMsg;
import it.polimi.ingsw.ps13.message.response.ResponseMsg;
import it.polimi.ingsw.ps13.message.response.UpdateResponseMsg;
import it.polimi.ingsw.ps13.message.response.multicast.MulticastMsg;
import it.polimi.ingsw.ps13.message.response.unicast.ConnectionUnicastMsg;
import it.polimi.ingsw.ps13.message.response.unicast.RenameUnicastMsg;
import it.polimi.ingsw.ps13.message.response.unicast.UnicastMsg;
import it.polimi.ingsw.ps13.model.Game;
import it.polimi.ingsw.ps13.model.player.Player;
import it.polimi.ingsw.ps13.util.observer.Observable;
import it.polimi.ingsw.ps13.util.observer.Observer;

/**
 * Controller for a single game.
 * Handles every request coming from a client connected to this controller (actions, chat messages...) 
 *
 */
public class GameController extends Observable<ResponseMsg> implements Observer<RequestMsg>, Runnable {

	private static final Logger LOG = Logger.getLogger(GameController.class.getName());
	private static final String DEFAULT_CONFIG = "config.xml";
	private static final int MAX_USERNAME_CHARACTERS = 14;
	private static final int TURN_TIMEOUT = 90;
	
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
	 */
	public GameController() {
		
		this.configFilePath = DEFAULT_CONFIG;
		players = new ArrayList<>();
		
		actionFactory = new ActionFactory();
		
	}
	
	/**
	 * Runs the GameController.
	 * 
	 */
	@Override
	public void run() {
		
		initGame();
		
	}
	
	/**
	 * Parses the XML configuration file, initializes the game model, notifies the players that the game is
	 * started, and sets the timer for turn timeout.
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
		
		notifyObserver(new PingResponseMsg());
		
		game = new Game(config, players);
		
		// Send created game to every client
		notifyObserver(new UpdateResponseMsg("=== !!! GAME STARTED !!! ===", game));
		notifyCurrentTurn();
		
		// Set timer for first turn
		setTimer();
		
	}
	
	/**
	 * If the game hasn't started yet, adds a player to this game and notifies other players in the room.
	 * 
	 * @param name the name of the player to be added (unique identifier inside a game)
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
	 * Returns the current game model state.
	 * 
	 * @return game the current game model state.
	 */
	public Game getGame() {
		
		return game;
		
	}
	
	/**
	 * This method handles a request message coming from one of the client views.
	 * 
	 */
	@Override
	public void update(RequestMsg msg) {
		
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
		
		else if (msg instanceof RenameRequestMsg) {
			RenameRequestMsg renameMsg = (RenameRequestMsg) msg;
			
			if (game == null) {
				if (renameMsg.getNewName().matches("^[a-zA-Z0-9._-]{1," + MAX_USERNAME_CHARACTERS + "}$")) {
					if (!players.contains(renameMsg.getNewName())) {
						players.remove(renameMsg.getPlayerName());
						players.add(renameMsg.getNewName());
						notifyObserver(new RenameUnicastMsg("You are now known as " + renameMsg.getNewName() + ".", renameMsg.getPlayerName(), renameMsg.getNewName()));
						notifyObserver(new MulticastMsg(renameMsg.getPlayerName() + " is now known as " + renameMsg.getNewName() + "." , renameMsg.getNewName()));
					} else {
						notifyObserver(new UnicastMsg("ERROR: Username is already in use in current room!", renameMsg.getPlayerName()));
					}
				} else {
					notifyObserver(new UnicastMsg("ERROR: Username is not valid! (max " + MAX_USERNAME_CHARACTERS + " alphanumeric characters, dots, underscores and dashes)", renameMsg.getPlayerName()));
				}
			} else {
				notifyObserver(new UnicastMsg("ERROR: The game is already running!", renameMsg.getPlayerName()));
			}
		}
		
		else if (msg instanceof DisconnectRequestMsg) {
			DisconnectRequestMsg disconnectMsg = (DisconnectRequestMsg) msg;
			
			String disconnectedPlayer = disconnectMsg.getPlayerName();
			notifyObserver(new ResponseMsg(disconnectedPlayer + " has disconnected!"));
			
			if (game != null) {
				game.getPlayer(disconnectedPlayer).setConnected(false);
				if (game.getConnectedPlayers() < 2) {
					timer.cancel();
					game.finalizeGame();
					notifyObserver(new UpdateResponseMsg("GAME FINISHED! THE WINNER IS " + calculateWinner() + "! CONGRATULATIONS!!", game));
					
				} else if (game.getCurrentPlayerName().equals(disconnectedPlayer)) {
					game.passTurn();
					notifyCurrentTurn();
					
					timer.cancel();
					setTimer();
				}
			} else {
				players.remove(disconnectedPlayer);
			}
		}
		
	}
	
	/**
	 * Performs legality check on the action, applies it to the current game state and notifies every client view
	 * with the new game state.
	 * 
	 * @param action the action to apply, after a legality check is performed
	 * @param playerName unique identifier of the player wanting to perform the action
	 */
	private void handleAction(Action action, String playerName) {
		
		boolean legal = false;
		
		try {
			legal = action.isLegal(game);
		} catch(IllegalActionException e) {
			notifyObserver(new UnicastMsg("ERROR: Action is not legal! (" + e.getMessage() + ")", playerName));
			return;
		}
		
		if (legal) {
			
			action.apply(game);
			notifyObserver(new UpdateResponseMsg(playerName + " performed " + action.getClass().getSimpleName() + ".", game));
			
			if (action instanceof PassTurnAction) {
				checkIfGameIsFinished();
			}
			
		} else {
			notifyObserver(new UnicastMsg("ERROR: Action is not legal!", playerName));
		}

	}
	
	/**
	 * Notifies every client view about the current turn.
	 * 
	 */
	private void notifyCurrentTurn() {
		
		notifyObserver(new UnicastMsg("It\'s YOUR turn, " + game.getCurrentPlayerName() + "! Bring it on!!\n(but move your ass, you only have " + TURN_TIMEOUT + " seconds.)", game.getCurrentPlayerName()));
		notifyObserver(new MulticastMsg(game.getCurrentPlayerName() + "\'s turn.", game.getCurrentPlayerName()));
		
	}
	
	/**
	 * Sets the timer for turn timeout.
	 * This method does not cancel the previously set timer.
	 * 
	 */
	private void setTimer() {
		
		timer = new Timer();
		
		timerTask = new TimerTask() {
            @Override
            public void run() {
            	
            	String slowPlayer = game.getCurrentPlayerName();
            	
            	game.passTurn();
            	notifyObserver(new UpdateResponseMsg("Time\'s up for " + slowPlayer + "!", game));
            	checkIfGameIsFinished();
            }
        };
		
		timer.schedule(timerTask, (long) TURN_TIMEOUT * 1000);
		
	}
	
	/**
	 * Checks if game is finished. If that's the case, final operations on the game are performed,
	 * and every client view is notified of the winner.
	 * 
	 * This method is called after a player either performs a PassTurnAction or timer is up.
	 * 
	 */
	private void checkIfGameIsFinished() {
		
		if (!game.isFinished()) {
    		notifyCurrentTurn();
    		
    		// Reset timer for next turn
        	timer.cancel();
        	setTimer();
    	} else {
    		timer.cancel();
    		game.finalizeGame();
			notifyObserver(new UpdateResponseMsg("GAME FINISHED! THE WINNER IS " + calculateWinner() + "! CONGRATULATIONS!!", game));
    	}
		
	}
	
	/**
	 * Calculates the winner of the game (player with highest number of victory points).
	 * 
	 * @return unique identifier of the player who has won the game
	 */
	private String calculateWinner() {
		
		String winner = "";
		
		int maxVictoryPoints = -1;
		for (Player p : game.getPlayers().values()) {
			if (p.getVictoryPoints() > maxVictoryPoints) {
				maxVictoryPoints = p.getVictoryPoints();
				winner = p.getName();
			}
			
			// draw contest
			if(p.getVictoryPoints() == maxVictoryPoints && p.getVictoryPoints() != 0) {
				
				winner = drawContest(p, game.getPlayer(winner));
				
			}
			
		}
		
		return winner;
		
	}
	
	/**
	 * Applies draw rules to calculate who's the winner in case of same amount of victory points.
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
