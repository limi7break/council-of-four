package it.polimi.ingsw.ps13.model.player;

import java.awt.Color;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import it.polimi.ingsw.ps13.model.board.Board;
import it.polimi.ingsw.ps13.model.deck.PermitTile;
import it.polimi.ingsw.ps13.model.deck.PoliticsCard;
import it.polimi.ingsw.ps13.model.deck.PoliticsCardDeck;

/**
 * This class represents a player in a game.
 * A player has a unique identifier, which is the name, and other game-related data, such as the position on the
 * nobility track, the action tokens, the color, and the amount of victory points, coins and other statistics stored
 * in the player supply.
 * 
 * The connected flag is set to true by default upon player creation.
 * When said flag is set to false, the game goes on, skipping the player's turns.
 *
 */
public class Player implements Serializable {

	public static final long serialVersionUID = 0L;
	
	private final int playerID;
	private final String name;
	private final Color color;
	private final String colorName;
	private final PlayerSupply supply;
	private final Set<String> cityNames;
	private int nobilityPosition;
	private final ActionTokens tokens;
	private boolean connected;
	
	private final Board board;
	
	/**
	 * Creates a new Player with the specified parameters.
	 * 
	 * @param name the name of the player
	 * @param color the color of the player
	 * @param colorName the name of the player's color
	 * @param position the position of the player in the turn order (0 = first player, 1 = second player...)
	 * @param board the game board
	 */
	public Player(String name, Color color, String colorName, int position, Board board) {
		
		this.name = name;
		this.color = color;
		this.colorName = colorName;
		this.playerID = position;
		
		supply = new PlayerSupply(position, color);
		cityNames = new TreeSet<>();
		
		nobilityPosition = 0;
		
		tokens = new ActionTokens();
		connected = true;
		
		this.board = board;
	}
	
	/**
	 * Returns the amount of victory points of the player.
	 * 
	 * @return the amount of victory points of the player
	 */
	public int getVictoryPoints(){
		
		return supply.getVictoryPoints().getAmount();
		
	}
	
	/**
	 * Adds the specified amount of victory points to the player.
	 * 
	 * @param amount the amount of victory points to add to the player
	 */
	public void addVictoryPoints(int amount){
		
		supply.getVictoryPoints().add(amount);
		
	}
	
	/**
	 * Returns the amount of coins of the player.
	 * 
	 * @return the amount of coins of the player
	 */
	public int getCoins(){
		
		return supply.getCoins().getAmount();
		
	}
	
	/**
	 * Adds the specified amount of coins to the player.
	 * 
	 * @param amount the amount of coins to add to the player
	 */
	public void addCoins(int amount){
		
		supply.getCoins().add(amount);
		
	}
	
	/**
	 * Removes the specified amount of coins from the player.
	 * 
	 * @param amount the amount of coins to remove from the player
	 */
	public void consumeCoins(int amount){
		
		supply.getCoins().consume(amount);
		
	}
	
	/**
	 * Returns the amount of assistants of the player.
	 * 
	 * @return the amount of assistants of the player
	 */
	public int getAssistants(){
		
		return supply.getAssistants().getAmount();
		
	}
	
	/**
	 * Adds the specified amount of assistants to the player.
	 * 
	 * @param amount the amount of assistants to add to the player
	 */
	public void addAssistants(int amount){
		
		supply.getAssistants().add(amount);
		
	}
	
	/**
	 * Removes the specified amount of assistants from the player.
	 * 
	 * @param amount the amount of assistants to remove from the player
	 */
	public void consumeAssistants(int amount){
		
		supply.getAssistants().consume(amount);
		
	}
	
	/**
	 * Returns the number of emporiums the player has (and can still build).
	 * 
	 * @return the number of emporiums the player has
	 */
	public int getNumberOfEmporiums() {
		
		return supply.getEmporiums().size();
		
	}
	
	/**
	 * Removes an emporium from the player and returns it.
	 * 
	 * @return the removed emporium
	 */
	public Emporium removeEmporium() {
		
		return supply.getEmporiums().remove(0);
		
	}
	
	/**
	 * Returns a list view of the politics cards of the player.
	 * 
	 * @return a list view of the politics cards of the player
	 */
	public List<PoliticsCard> getPoliticsCards() {
		
		return supply.getPoliticsCards();
		
	}
	
	/**
	 * Gives the player a politics card: useful for market.
	 * 
	 * @param card
	 */
	public void receivePoliticsCard(PoliticsCard card) {
		
		supply.getPoliticsCards().add(card);
		
	}
	
	/**
	 * Gives the player a permit tile: useful for market.
	 * 
	 * @param tile
	 */
	public void receivePermitTile(PermitTile tile) {
		
		tile.setUsable(false);
		for (String cityName : tile.getCityNames()) {
			if (!hasBuiltOn(cityName)) {
				tile.setUsable(true);
			}
		}
		
		supply.getPermitTiles().add(tile);
		tile.getBonus().giveTo(this);
		
	}
	
	// Getters and setters
	
	/**
	 * Returns the player's ID, which corresponds to the position in the turn order (0 = first player, 1 = second player ... ).
	 * 
	 * @return the player's ID
	 */
	public int getID() {
		
		return playerID;
		
	}
	
	/**
	 * Returns the name of the player.
	 * 
	 * @return the name of the player
	 */
	public String getName() {
		
		return name;
		
	}
	
	/**
	 * Returns the color of the player.
	 * 
	 * @return the color of the player
	 */
	public Color getColor() {
		
		return color;
		
	}
	
	/**
	 * Returns the nobility position of the player.
	 * 
	 * @return the nobility position of the player
	 */
	public int getNobilityPosition() {
		
		return nobilityPosition;
		
	}

	/**
	 * Adds 1 to the nobility position of the player.
	 * 
	 */
	public void nobilityAdvance() {
		
		this.nobilityPosition += 1;
		
	}
	
	/**
	 * Returns a set view containing the names of the cities upon which the player has built an emporium.
	 * 
	 * @return a set view containing the names of the cities upon which the player has built an emporium
	 */
	public Set<String> getCityNames() {
		
		return Collections.unmodifiableSet(cityNames);
		
	}
	
	/**
	 * Returns true if the player has built an emporium on the city.
	 * 
	 * @param cityName the city to check whether the player has built on
	 * @return true if the player has built an emporium on that city
	 */
	public boolean hasBuiltOn(String cityName) {
		
		return cityNames.contains(cityName);
		
	}
	
	/**
	 * Adds a city to the set of cities upon which the player has built an emporium.
	 * 
	 * @param cityName the name of the city upon which the player has built an emporium
	 */
	public void addCity(String cityName) {
		
		cityNames.add(cityName);
		
	}

	/**
	 * Returns a list view of the permit tiles of the player.
	 * 
	 * @return a list view of the permit tiles of the player
     */
	public List<PermitTile> getPermitTiles() {
		
		return supply.getPermitTiles();
		
	}
	
	/**
	 * Returns the action tokens of the player.
	 * 
	 * @return the action tokens of the player
	 */
	public ActionTokens getTokens() {
		
		return tokens;
		
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
	 * Adds the specified amount of main action tokens to the player.
	 * 
	 * @param amount the amount of main actions tokens to add
	 */
	public void addMainActions(int amount){
		
		tokens.setMain(tokens.getMain() + amount);
		
	}
	
	/**
	 * Removes a main action token from the player.
	 * 
	 */
	public void consumeMainAction(){
		
		if(tokens.getMain() < 1) 
			throw new IndexOutOfBoundsException();
		else
			tokens.setMain(tokens.getMain() - 1);
		
	}
	
	/**
	 * Removes a quick action token from the player.
	 * 
	 */
	public void consumeQuickAction(){
		
		if(tokens.getQuick() < 1) 
			throw new IndexOutOfBoundsException();
		else
			tokens.setQuick(tokens.getQuick() - 1);
		
	}
	
	/**
	 * Removes a sell action token from the player.
	 * 
	 */
	public void consumeSellAction() {
		
		if(tokens.getSell() < 1) 
			throw new IndexOutOfBoundsException();
		else
			tokens.setSell(tokens.getSell() - 1);
			
	}
	
	/**
	 * Removes a buy action token from the player.
	 * 
	 */
	public void consumeBuyAction() {
		
		if(tokens.getBuy() < 1) 
			throw new IndexOutOfBoundsException();
		else
			tokens.setBuy(tokens.getBuy() - 1);
			
	}
	
	/**
	 * Adds the specified amount of "get tb" tokens to the player.
	 * 
	 * @param amount the amount of "get tb" actions to add
	 */
	public void addTileBonusToken(int amount){
		
		tokens.setTileBonus(tokens.getTileBonus() + amount);
		
	}
	
	/**
	 * Removes a "get tb" action token from the player.
	 * 
	 */
	public void consumeTileBonusToken(){
		
		if(tokens.getTileBonus() < 1) 
			throw new IndexOutOfBoundsException();
		else
			tokens.setTileBonus(tokens.getTileBonus() - 1);
		
	}
	
	/**
	 * Adds the specified amount of "get rt" tokens to the player.
	 * 
	 * @param amount the amount of "get rt" actions to add
	 */
	public void addRewardTokenToken(int amount){
		
		tokens.setRewardToken(tokens.getRewardToken() + amount);
		
	}
	
	/**
	 * Removes a "get rt" action token from the player.
	 * 
	 */
	public void consumeRewardTokenToken(){
		
		if(tokens.getRewardToken() < 1) 
			throw new IndexOutOfBoundsException();
		else
			tokens.setRewardToken(tokens.getRewardToken() - 1);
		
	}
	
	/**
	 * Adds the specified amount of "get tile" tokens to the player.
	 * 
	 * @param amount the amount of "get tile" actions to add
	 */
	public void addTakeTileToken(int amount){
		
		tokens.setTakeTile(tokens.getTakeTile() + amount);
		
	}
	
	/**
	 * Removes a "get tile" action token from the player.
	 * 
	 */
	public void consumeTakeTileToken(){
		
		if(tokens.getTakeTile() < 1) 
			throw new IndexOutOfBoundsException();
		else
			tokens.setTakeTile(tokens.getTakeTile() - 1);
		
	}
	
	/**
	 * Returns true if the player is connected.
	 * 
	 * @return true if the player is connected
	 */
	public boolean isConnected() {
		
		return connected;
		
	}
	
	/**
	 * Sets the connection status for the player.
	 * 
	 * @param isConnected the connection status for the player (true = connected, false = not connected).
	 */
	public void setConnected(boolean isConnected) {
		
		if (!connected && isConnected) {
			throw new UnsupportedOperationException("Reconnection isn\'t supported yet.");
		} else {
			connected = isConnected;
		}
		
	}
	
	/**
	 * Draws the specified amount of politics cards from the board's politics cards deck.
	 * 
	 * @param amount the amount of politics cards to draw
	 */
	public void drawPoliticsCards(int amount) {
		
		PoliticsCardDeck deck = board.getPoliticsCardDeck();
		
		for(int i = 0; i<amount; i++) {
			
			this.receivePoliticsCard(deck.drawCard());
			
		}
		
	}
	
	/**
	 * Used for Command Line Interface (CLI).
	 * 
	 */
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("\nMy name is ").append(name).append("\n")
		  .append("COLOR: ").append(colorName).append("\n")
		  .append(supply.toString()).append("\n")
		  .append("NOBILITY POSITION: ").append(nobilityPosition).append("\n")
		  .append("CITIES: ").append(cityNames.toString()).append("\n")
		  .append("ACTIONS: ").append(tokens.toString()).append("\n\n");
			
		return sb.toString();
	
	}
	
}
