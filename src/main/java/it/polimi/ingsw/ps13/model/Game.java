package it.polimi.ingsw.ps13.model;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import it.polimi.ingsw.ps13.model.board.Board;
import it.polimi.ingsw.ps13.model.board.BoardFactory;
import it.polimi.ingsw.ps13.model.market.Market;
import it.polimi.ingsw.ps13.model.player.Emporium;
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
	private int playerWhoBuiltLastEmporium;
	private boolean finished;
	private boolean sellMarketPhase;
	private boolean buyMarketPhase;
	
	/**
	 * Creates a new Game from the specified configuration document.
	 * The players are created starting from their names.
	 * The position of the players in the turn order is random.
	 * 
	 * @param config the configuration document
	 * @param players a list of the player names
	 */
	public Game(Document config, List<String> players) { 
		
		market = new Market();
		colors = new LinkedHashMap<>();
		ColorFactory.createColors(config, colors);
		board = BoardFactory.createBoard(config, colors);
		numberOfPlayers = players.size();
		
		Collections.shuffle(players);
		
		this.players = new TreeMap<>();
		Iterator<String> it = colors.keySet().iterator();
		
		for (int i=0; i<numberOfPlayers; i++) {
			String playerName = players.get(i);
			
			//Colors are sequentially given to the players
			String colorName = it.next();
			
			Color color = colors.get(colorName);
			
			this.players.put(i, new Player(playerName, color, colorName, i, board));
			
		}
		
		for (Player p : this.players.values()) {
			p.drawPoliticsCards(INITIAL_POLITICS_CARDS);
		}
		
		playerWhoBuiltLastEmporium = -1;
		finished = false;
		sellMarketPhase = false;
		buyMarketPhase = false;
		
		// Check if market is disabled in configuration file, else leave it as is (enabled by default)
		Element marketElement = (Element) config.getElementsByTagName("market").item(0);
		if ("false".equals(marketElement.getAttribute("enabled")))
			market.setEnabled(false);
		
		// Setups the game for a two player game.
		if(numberOfPlayers == 2)
			twoPlayerSetup(it);
		
		currentPlayerID = 0;
		getCurrentPlayer().getTokens().setInitial();
		
	}
	
	/**
	 * Applies the rules for a two player game: 1 to 3 random cities per region are given an emporium by default.
	 * The color of the emporium is always the third color(the first color not assigned), since players are 2.
	 * 
	 * @param color
	 */
	private void twoPlayerSetup(Iterator<String> color) {
		
		Random rand = new Random();
		
		Color unusedColor = colors.get(color.next());
		
		for(String region : this.board.getRegions().keySet()){
			
			int initialCities = rand.nextInt(3) + 1;
			
			for(int i=0; i<initialCities; i++){
				
				List<String> allCities = new ArrayList<>(this.board.getRegion(region).getCityNames());
				
				String cityName;
				
				do{
					int randomCity = rand.nextInt(allCities.size());
					cityName = allCities.get(randomCity);
				}
				while(this.board.getCity(cityName).getNumberOfEmporiums()!=0);
				
				
				this.board.getCity(cityName).addEmporium(new Emporium(unusedColor));
				
			}
			
		}
		
	}
	
	/**
	 * Returns a map with every color imported for the game.
	 * 
	 * @return a map with every color imported for the game
	 */
	public Map<String, Color> getColors() {
		
		return colors;
		
	}
	
	/**
	 * Returns the game board.
	 * 
	 * @return the game board
	 */
	public Board getBoard() {
		
		return board;
		
	}
	
	/**
	 * Returns an unmodifiable map view of the players with their position in the turn order.
	 * 
	 * @return an unmodifiable map view of the players with their position in the turn order
	 */
	public Map<Integer, Player> getPlayers() {
		
		return Collections.unmodifiableMap(players);
		
	}
	
	/**
	 * Gets a player given its name.
	 * Assumes players don't have duplicate names in the same game.
	 * 
	 * @param playerName the selected name
	 * @return the player with the specified name.
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
	 * Returns the game's market.
	 * 
	 * @return the game's market
	 */
	public Market getMarket() {
		
		return market;
		
	}
	
	/**
	 * Returns the ID (position in the turn order) of the current player of the game.
	 * 
	 * @return the ID of the current player of the game.
	 */
	public int getCurrentPlayerID() {
		
		return currentPlayerID;
		
	}
	
	/**
	 * Returns the current player of this game.
	 * 
	 * @return the current player of this game
	 */
	public Player getCurrentPlayer() {
		
		return players.get(currentPlayerID);
		
	}
	
	/**
	 * Returns the name of the current player of this game.
	 * 
	 * @return the name of the current player of this game
	 */
	public String getCurrentPlayerName() {

		return getCurrentPlayer().getName();
		
	}
	
	/**
	 * Returns the first connected player after the passed currentPlayerID, following the turn order.
	 * 
	 * @param currentPlayerID the current player's ID
	 * @return the first connected player after the passed currentPlayerID 
	 */
	public int getNextPlayerID(int currentPlayerID) {
		
		int nextPlayerID;
		
		if (currentPlayerID == numberOfPlayers-1)
			nextPlayerID = 0;
		else
			nextPlayerID = currentPlayerID+1;
		
		if (players.get(nextPlayerID).isConnected())
			return nextPlayerID;
		else
			return getNextPlayerID(nextPlayerID); 
		
	}
	
	/**
	 * This method handles the logic for when a player ends the turn, either by performing a PassTurnAction,
	 * or when the time for the turn expires and the controller ends the turn automatically.
	 * 
	 * It also handles the three game phases (normal game phase, sell market phase, and buy market phase).
	 * The sell and buy market phases are skipped if the market is disabled.
	 * The buy market phase is skipped if during the sell market phase no one sold anything.
	 * 
	 * When a player places his last emporium, market phases are skipped and every other player has the last turn before
	 * the game is finished.
	 * 
	 */
	public void passTurn() {
		
		if (finished) {
			throw new IllegalStateException("Can\'t pass turn when game is finished!");
		}
		
		int nextPlayerID = getNextPlayerID(currentPlayerID);
		
		// if a player has already built his last emporium, every other player has the last turn
		if (playerWhoBuiltLastEmporium != -1) {
			
			if ( nextPlayerID == playerWhoBuiltLastEmporium ) {
				finished = true;
			} else {
				getCurrentPlayer().getTokens().setZeros();
				currentPlayerID = nextPlayerID;
				getCurrentPlayer().getTokens().setInitial();
				getCurrentPlayer().drawPoliticsCards(1);
			}
			
		}
		
		// Normal turn flow
		else {
			// If we are in the NORMAL GAME PHASE
			if (!sellMarketPhase && !buyMarketPhase) {
				
				// Transition from normal game phase to sell market phase
				if (nextPlayerID == 0 && market.isEnabled()){
					sellMarketPhase = true;
					
					getCurrentPlayer().getTokens().setZeros();
					currentPlayerID = nextPlayerID;
					getCurrentPlayer().getTokens().setSell();
				}
				
				// No transition, continue with normal game phase
				else {
					getCurrentPlayer().getTokens().setZeros();
					currentPlayerID = nextPlayerID;
					getCurrentPlayer().getTokens().setInitial();
					getCurrentPlayer().drawPoliticsCards(1);
				}
				
			}
			
			// If we are in the SELL MARKET PHASE
			else if (sellMarketPhase) {
				
				// No transition, continue with sell market phase
				if (nextPlayerID != 0) {
					getCurrentPlayer().getTokens().setZeros();
					currentPlayerID = nextPlayerID;
					getCurrentPlayer().getTokens().setSell();
				}
				
				// If market is not empty,
				// Transition from sell market phase to buy market phase
				else if (!market.getEntryList().isEmpty()) {
					sellMarketPhase = false;
					buyMarketPhase = true;
					
					getCurrentPlayer().getTokens().setZeros();
					currentPlayerID = nextPlayerID;
					getCurrentPlayer().getTokens().setBuy();
				}
				
				// If market is empty,
				// Transition from sell market phase to normal game phase
				else {
					sellMarketPhase = false;
					
					getCurrentPlayer().getTokens().setZeros();
					currentPlayerID = nextPlayerID;
					getCurrentPlayer().getTokens().setInitial();
					getCurrentPlayer().drawPoliticsCards(1);
				}
				
			}
			
			// If we are in the BUY MARKET PHASE
			else {
				
				// No transition, continue with buy market phase
				if (nextPlayerID != 0) {
					getCurrentPlayer().getTokens().setZeros();
					currentPlayerID = nextPlayerID;
					getCurrentPlayer().getTokens().setBuy();
				}
				
				// Transition from buy market phase to normal game phase
				else {
					buyMarketPhase = false;
					market.closeMarket();
					
					getCurrentPlayer().getTokens().setZeros();
					currentPlayerID = nextPlayerID;
					getCurrentPlayer().getTokens().setInitial();
					getCurrentPlayer().drawPoliticsCards(1);
				}
				
			}
		}
		
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
	 * Returns the number of players in the game.
	 * 
	 * @return the number of players in the game
	 */
	public int getNumberOfPlayers() {
		
		return numberOfPlayers;
		
	}
	
	/**
	 * Returns true if the game is currently in the sell market phase.
	 * 
	 * @return true if the game is currently in the sell market phase
	 */
	public boolean isSellMarketPhase() {
		
		return sellMarketPhase;
		
	}
	
	/**
	 * Returns true if the game is currently in the buy market phase.
	 * 
	 * @return true if the game is currently in the buy market phase
	 */
	public boolean isBuyMarketPhase() {
		
		return buyMarketPhase;
		
	}
	
	/**
	 * Returns the ID of the player who first built his last emporium, or -1 if no one has
	 * built his last emporium yet.
	 * 
	 * @return the ID of the player who first built his last emporium, or -1 if no one has built his last emporium yet
	 */
	public int getPlayerWhoBuiltLastEmporium() {
		
		return playerWhoBuiltLastEmporium;
		
	}

	/**
	 * Sets the ID of the player who first built his last emporium. After this value is set, every
	 * other player can play his last turn and after that the game is finished.
	 * 
	 * This value needs to be set in order to know when the game is really finished.
	 * 
	 * @param playerWhoBuiltLastEmporium the ID of the player who first build his last emporium
	 */
	public void setPlayerWhoBuiltLastEmporium(int playerWhoBuiltLastEmporium) {
		
		this.playerWhoBuiltLastEmporium = playerWhoBuiltLastEmporium;
		
	}

	/**
	 * Returns true if the game is finished.
	 * 
	 * @return true if the game is finished
	 */
	public boolean isFinished() {
		
		return finished;
		
	}
	
	/**
	 * Returns the number of connected players in this game.
	 * It is used to terminate the game early if everyone disconnects and only one players remains in a game.
	 * 
	 * @return the number of connected players
	 */
	public int getConnectedPlayers() {
		
		int connectedPlayers = 0;
		
		for (Player p : players.values()) {
			if (p.isConnected()) {
				connectedPlayers++;
			}
		}
		
		return connectedPlayers;
		
	}
	
	/**
	 * Adds extra victory points to the players who own the highest amount of permit tiles and to
	 * the most advanced in nobility track.
	 */
	public void finalizeGame() {
		
		for (Player p : players.values()) {
			p.getTokens().setZeros();
		}
		
		bestNobilityProgress();
		bestPermitTilesAcquisition();
		
	}
	
	/**
	 * The player whose nobility position is most advanced gains 5 victory points, the second 2 victory points.
	 * If more players have the highest nobility points, then they only gain 5 victory points.
	 * Otherwise, all the players who have the second position gain 2 points.
	 */
	private void bestNobilityProgress() {
	       
        List<Player> firstPlace = new ArrayList<>();
        List<Player> secondPlace = new ArrayList<>();
       
        int first = -1;
        int second = -1;
        for(Player p : players.values()){
            if(p.getNobilityPosition() > first) {
                second = first;
                first = p.getNobilityPosition();
                secondPlace.clear();
                secondPlace.addAll(firstPlace);
                firstPlace.clear();
                firstPlace.add(p);
            } else if(p.getNobilityPosition() == first) {
                firstPlace.add(p);
            } else if(p.getNobilityPosition() > second) {      
                second = p.getNobilityPosition();
                secondPlace.clear();
                secondPlace.add(p);
            } else if(p.getNobilityPosition() == second) {
                secondPlace.add(p);
            }
        }
       
        if(firstPlace.size() > 1) {
            for(Player p : firstPlace){
                p.addVictoryPoints(5);
            }
        }
        else {
            firstPlace.get(0).addVictoryPoints(5);
            for(Player p : secondPlace){
                p.addVictoryPoints(2);
            }
        }
       
    }
	
	/**
	 * The player who owns the highest number of permit tiles gains 3 victory points.
	 * Rules do not clarify how to behave in case of same number
	 * of permit tiles. We choose all the players gain 
	 * the points.
	 */
	private void bestPermitTilesAcquisition() {
		
		int maxTiles = -1;
		List<Player> winners = new ArrayList<>();;
		
		for(Player p : players.values()){
			
			if (p.getPermitTiles().size() == maxTiles){
				winners.add(p);
			}
			else if (p.getPermitTiles().size() > maxTiles){
				maxTiles = p.getPermitTiles().size();
				winners.clear();
				winners.add(p);
			}
			
		}
		
		for (Player p : winners) {
			p.addVictoryPoints(3);
		}
		
	}
	
}