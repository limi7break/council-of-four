package it.polimi.ingsw.ps13.model;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import org.w3c.dom.Document;

import it.polimi.ingsw.ps13.message.response.ResponseMsg;
import it.polimi.ingsw.ps13.model.board.Board;
import it.polimi.ingsw.ps13.model.board.BoardFactory;
import it.polimi.ingsw.ps13.model.council.Councillor;
import it.polimi.ingsw.ps13.model.market.Market;
import it.polimi.ingsw.ps13.model.player.Player;
import it.polimi.ingsw.ps13.util.observer.Observable;

/**
 * This class encapsulates the entire status of a game.
 * 
 * It's serializable so that it can be easily sent over a network:
 * for example the Server broadcasts a new updated Game whenever
 * a player successfully performs an action.
 * 
 */

public class Game extends Observable<ResponseMsg> implements Serializable {

	private static final long serialVersionUID = 0L;
	private final Map<String, Color> colors;
	private final Board board;
	private final Map<Integer, Player> players;
	private final int numberOfPlayers;
	private final Market market;
	private int currentPlayerID;							// number of the player in the players map
	
	public Game(Document config, List<String> players) { 
		
		market = new Market();
		colors = new HashMap<>();
		ColorFactory.createColors(config, colors);
		board = BoardFactory.createBoard(config, colors);
		numberOfPlayers = players.size();
		
		Collections.shuffle(players);
		
		this.players = new TreeMap<>();
		for (int i=0; i<numberOfPlayers; i++) {
			String playerName = players.get(i);
			
			// Select random color for each player
			// @TODO: prevent a random color from being picked twice (maybe ask user for color)?
			Random random = new Random();
			List<String> keys = new ArrayList<>(colors.keySet());
			String randomKey = keys.get(random.nextInt(keys.size()));
			Color randomColor = colors.get(randomKey);
			
			this.players.put(i, new Player(playerName, randomColor, i));
		}
		
		currentPlayerID = 0;
		
		// start game maybe?
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
	public Map<Integer, Player> getPlayers() {
		
		return Collections.unmodifiableMap(players);
		
	}
	
	/**
	 * 
	 * @return
	 */
	public Market getMarket() {
		
		return market;
		
	}
	
	/**
	 * 
	 * @return
	 */
	public Player getCurrentPlayer() {
		
		return players.get(currentPlayerID);
		
	}
	
	/**
	 * 
	 * @return
	 */
	public int getCurrentPlayerID() {
		
		return currentPlayerID;
		
	}
	
	/**
	 * 
	 * @return
	 */
	public void passTurn() {
		
		if (currentPlayerID == numberOfPlayers-1) {
			currentPlayerID = 0;
		} else {
			currentPlayerID++;
		}
		
	}
	
	/**
	 * 
	 * @param color
	 * @return
	 */
	public boolean isCouncillorAvailable(Councillor councillor){
		
		for(Councillor c: board.getCouncillors()){
			
			if(c.equals(councillor)) 
				return true;

		}
		
		return false;
		
	}
	
	/**
	 * This method assumes there are no duplicate keys in the colors map.
	 * 
	 * @param color
	 * @return
	 */
	public String getColorName(Color color) {
		
		for (Map.Entry<String, Color> entry : colors.entrySet()) {
			
			if (color.equals(entry.getValue())) {
				return entry.getKey();
			}
			
		}
		
		return null;
		
	}
	
	/**
	 * Removes and returns the first councillor with the given color belonging to the free councillors of the board.
	 * If there are none, returns null.
	 * 
	 * @param color
	 * @return
	 */
	public Councillor getCouncillor(Color color) {
		
		Councillor councillor = null;
		
		Iterator<Councillor> it = board.getCouncillors().iterator();
		
		while(it.hasNext() && councillor == null) {
			
			if(it.next().getColor().equals(color)) {
				
				councillor = it.next();
				board.getCouncillors().remove(it.next());
				
			}
			
		}
		
		return councillor;
		
	}
	
}