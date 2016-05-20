package it.polimi.ingsw.ps13.model;

import java.awt.Color;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;

import it.polimi.ingsw.ps13.model.board.Board;
import it.polimi.ingsw.ps13.model.board.BoardFactory;
import it.polimi.ingsw.ps13.model.player.Player;

/**
 * This class encapsulates the entire status of a game.
 * 
 * It's Serializable so that it can be easily sent over a network:
 * for example the Server broadcasts a new updated GameState whenever
 * a Player successfully performs an Action.
 * 
 */

public class Game implements Serializable {

	private static final long serialVersionUID = 0L;
	private final Map<String, Color> colors;
	private final Board board;
	private final Map<String, Player> players;
	
	public Game(Document config, Map<String, Player> players) { 
		
		this.players = players;
		colors = new HashMap<>();
		ColorFactory.createColors(config, colors);
		board = BoardFactory.createBoard(config, colors);
		
	}
	
	/**
	 * 
	 * @return a map with every color imported for the game
	 */
	public Map<String, Color> getColors() {
		
		return Collections.unmodifiableMap(colors);
		
	}
	
	/**
	 * 
	 * @return
	 */
	public Board getBoard() {
		
		return board;
		
	}
	
	/**
	 * 
	 * @return
	 */
	public Map<String, Player> getPlayers() {
		
		return Collections.unmodifiableMap(players);
		
	}
	
}
