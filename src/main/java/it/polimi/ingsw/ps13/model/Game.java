package it.polimi.ingsw.ps13.model;

import java.awt.Color;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.w3c.dom.Document;

import it.polimi.ingsw.ps13.model.board.Board;
import it.polimi.ingsw.ps13.model.board.BoardFactory;
import it.polimi.ingsw.ps13.model.market.Market;
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
	private final Market market;
	
	public Game(Document config, List<String> players) { 
		
		market = new Market();
		colors = new HashMap<>();
		ColorFactory.createColors(config, colors);
		board = BoardFactory.createBoard(config, colors);

		Collections.shuffle(players);
		
		this.players = new TreeMap<>();
		for (int i=0; i<players.size(); i++) {
			String playerName = players.get(i);
			// interact with the user to ask which color??
			this.players.put(playerName, new Player(playerName, Color.BLACK, i));
		}
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
	
	/**
	 * 
	 * @return
	 */
	public Market getMarket() {
		
		return market;
		
	}
	
}
