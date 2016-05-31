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

import it.polimi.ingsw.ps13.model.board.Board;
import it.polimi.ingsw.ps13.model.board.BoardFactory;
import it.polimi.ingsw.ps13.model.council.Councillor;
import it.polimi.ingsw.ps13.model.market.Market;
import it.polimi.ingsw.ps13.model.player.Player;

/**
 * This class encapsulates the entire status of a game.
 * 
 * It's serializable so that it can be easily sent over a network:
 * for example the Server broadcasts a new updated Game whenever
 * a player successfully performs an action that modifies the game state.
 * 
 */

public class Game implements Serializable {

	private static final long serialVersionUID = 0L;
	private static final int INITIAL_POLITICS_CARDS = 6;
	private final Map<String, Color> colors;
	private final Board board;
	private final Map<Integer, Player> players;
	private final int numberOfPlayers;
	private final Market market;
	private int currentPlayerID;							// number of the player in the players map
	private boolean sellMarketPhase;
	private boolean buyMarketPhase;
	private int playerWhoBuiltLastEmporium;
	private boolean finished;
	
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
			
			this.players.put(i, new Player(playerName, randomColor, randomKey, i, board));
		}
		
		currentPlayerID = 0;
		
		sellMarketPhase = false;
		buyMarketPhase = false;
		
		for (Player p : this.players.values()) {
			p.drawPoliticsCards(INITIAL_POLITICS_CARDS);
		}
		
		playerWhoBuiltLastEmporium = -1;
	}
	
	/**
	 * 
	 * @return a map with every color imported for the game
	 */
	public Map<String, Color> getColors() {
		
		return colors;
		
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
	public Player getPlayer(String playerName) {
		
		Player player = null;
		
		for (Player p : players.values()) {
			if (p.getName().equals(playerName)) {
				player = p;
			}
		}
		
		return player;
		
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
	public String getCurrentPlayerName() {

		return getCurrentPlayer().getName();
		
	}
	
	/**
	 * 
	 * @return
	 */
	public void passTurn() {
		
		if (playerWhoBuiltLastEmporium != -1) {
			if (currentPlayerID == playerWhoBuiltLastEmporium-1) {
				finished = true;
			} else if (currentPlayerID == numberOfPlayers-1) {
				getCurrentPlayer().getTokens().setZeros();
				currentPlayerID = 0;
				getCurrentPlayer().getTokens().setInitial();
				getCurrentPlayer().drawPoliticsCards(1);
			} else {
				getCurrentPlayer().getTokens().setZeros();
				currentPlayerID++;
				getCurrentPlayer().getTokens().setInitial();
				getCurrentPlayer().drawPoliticsCards(1);
			}
		} else {
			if (currentPlayerID == numberOfPlayers-1) {
				if (!sellMarketPhase && !buyMarketPhase) {
					sellMarketPhase = true;
					
					getCurrentPlayer().getTokens().setZeros();
					currentPlayerID = 0;
					getCurrentPlayer().getTokens().setSell();
				}
				else if (sellMarketPhase) {
					sellMarketPhase = false;
					buyMarketPhase = true;
					
					getCurrentPlayer().getTokens().setZeros();
					currentPlayerID = 0;
					getCurrentPlayer().getTokens().setBuy();
				}
				else {
					buyMarketPhase = false;
					market.closeMarket();
					
					getCurrentPlayer().getTokens().setZeros();
					currentPlayerID = 0;
					getCurrentPlayer().drawPoliticsCards(1);
					getCurrentPlayer().getTokens().setInitial();
				}
			} else {
				if (!sellMarketPhase && !buyMarketPhase) {
					getCurrentPlayer().getTokens().setZeros();
					currentPlayerID++;
					getCurrentPlayer().getTokens().setInitial();
					getCurrentPlayer().drawPoliticsCards(1);
				}
				else if (sellMarketPhase) {
					getCurrentPlayer().getTokens().setZeros();
					currentPlayerID++;
					getCurrentPlayer().getTokens().setSell();
				}
				else {
					getCurrentPlayer().getTokens().setZeros();
					currentPlayerID++;
					getCurrentPlayer().getTokens().setBuy();
				}
			}
		}
		
	}
	
	/**
	 * 
	 * @param color
	 * @return
	 */
	public boolean isCouncillorAvailable(Color color){
		
		for(Councillor c: board.getCouncillors()){
			
			if(c.getColor().equals(color)) 
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
		
		Iterator<Councillor> it = board.getCouncillors().iterator();
		
		while(it.hasNext()) {
			Councillor current = it.next();
			
			if(current.getColor().equals(color)) {
				it.remove();
				return current;
			}
		}
		
		return null;
		
	}
	
	/**
	 * 
	 * @param currentPlayerID
	 */
	public void setCurrentPlayerID(int currentPlayerID) {
		
		this.currentPlayerID = currentPlayerID;
		
	}
	
	/**
	 * 
	 * @return
	 */
	public int getNumberOfPlayers() {
		
		return numberOfPlayers;
		
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isSellMarketPhase() {
		
		return sellMarketPhase;
		
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isBuyMarketPhase() {
		
		return buyMarketPhase;
		
	}

	/**
	 * 
	 * @param playerWhoBuiltLastEmporium
	 */
	public void setPlayerWhoBuiltLastEmporium(int playerWhoBuiltLastEmporium) {
		
		this.playerWhoBuiltLastEmporium = playerWhoBuiltLastEmporium;
		
	}

	/**
	 * 
	 * @return
	 */
	public boolean isFinished() {
		
		return finished;
		
	}
	
}