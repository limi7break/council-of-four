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
 * 
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
	 * 
	 * @return
	 */
	public int getVictoryPoints(){
		
		return supply.getVictoryPoints().getAmount();
		
	}
	
	/**
	 * 
	 * @param amount
	 */
	public void addVictoryPoints(int amount){
		
		supply.getVictoryPoints().add(amount);
		
	}
	
	/**
	 * 
	 * @return
	 */
	public int getCoins(){
		
		return supply.getCoins().getAmount();
		
	}
	
	/**
	 * 
	 * @param amount
	 */
	public void addCoins(int amount){
		
		supply.getCoins().add(amount);
		
	}
	
	/**
	 * 
	 * @param amount
	 */
	public void consumeCoins(int amount){
		
		supply.getCoins().consume(amount);
		
	}
	
	/**
	 * 
	 * @return
	 */
	public int getAssistants(){
		
		return supply.getAssistants().getAmount();
		
	}
	
	/**
	 * 
	 * @param 
	 */
	public void addAssistants(int amount){
		
		supply.getAssistants().add(amount);
		
	}
	
	/**
	 * 
	 * @param 
	 */
	public void consumeAssistants(int amount){
		
		supply.getAssistants().consume(amount);
		
	}
	
	/**
	 * 
	 * @return
	 */
	public int getNumberOfEmporiums() {
		
		return supply.getEmporiums().size();
		
	}
	
	/**
	 * 
	 * @return
	 */
	public Emporium removeEmporium() {
		
		return supply.getEmporiums().remove(0);
		
	}
	
	/**
	 * 
	 * @return
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
		
		supply.getPermitTiles().add(tile);
		
	}
	
	//getter and setter
	
	/**
	 * 
	 * @return
	 */
	public int getID() {
		
		return playerID;
		
	}
	
	/**
	 * 
	 * @return the name of the player
	 */
	public String getName() {
		
		return name;
		
	}
	
	/**
	 * 
	 * @return the color of the player
	 */
	public Color getColor() {
		
		return color;
		
	}
	
	/**
	 * 
	 * @return
	 */
	public int getNobilityPosition() {
		
		return nobilityPosition;
		
	}
	
	/**
	 * 
	 * @param nobilityPosition
	 */
	public void nobilityAdvance() {
		
		this.nobilityPosition += 1;
		
	}
	
	/**
	 * 
	 * @return
	 */
	public Set<String> getCityNames() {
		
		return Collections.unmodifiableSet(cityNames);
		
	}
	
	/**
	 * 
	 * @param city the city to check whether the player has built on
	 * @return true if the player has built an emporium on that city
	 */
	public boolean hasBuiltOn(String cityName) {
		
		return cityNames.contains(cityName);
		
	}
	
	/**
	 * 
	 * @param cityName
	 */
	public void addCity(String cityName) {
		
		cityNames.add(cityName);
		
	}
	
	/**
	 * 
	 * @return
	 */
	public List<PermitTile> getPermitTiles() {
		
		return supply.getPermitTiles();
		
	}
	
	/**
	 * 
	 * @return
	 */
	public ActionTokens getTokens() {
		
		return tokens;
		
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
	 * @param amount
	 */
	public void addMainActions(int amount){
		
		tokens.setMain(tokens.getMain() + amount);
		
	}
	
	/**
	 * 
	 */
	public void consumeMainAction(){
		
		if(tokens.getMain() < 1) 
			throw new IndexOutOfBoundsException();
		else
			tokens.setMain(tokens.getMain() - 1);
		
	}
	
	/**
	 * 
	 */
	public void consumeQuickAction(){
		
		if(tokens.getQuick() < 1) 
			throw new IndexOutOfBoundsException();
		else
			tokens.setQuick(tokens.getQuick() - 1);
		
	}
	
	/**
	 * 
	 */
	public void consumeSellAction() {
		
		if(tokens.getSell() < 1) 
			throw new IndexOutOfBoundsException();
		else
			tokens.setSell(tokens.getSell() - 1);
			
	}
	
	/**
	 * 
	 */
	public void consumeBuyAction() {
		
		if(tokens.getBuy() < 1) 
			throw new IndexOutOfBoundsException();
		else
			tokens.setBuy(tokens.getBuy() - 1);
			
	}
	
	/**
	 * 
	 * @param amount
	 */
	public void addTileBonusToken(int amount){
		
		tokens.setTileBonus(tokens.getTileBonus() + amount);
		
	}
	
	/**
	 * 
	 */
	public void consumeTileBonusToken(){
		
		if(tokens.getTileBonus() < 1) 
			throw new IndexOutOfBoundsException();
		else
			tokens.setTileBonus(tokens.getTileBonus() - 1);
		
	}
	
	/**
	 * 
	 * @param amount
	 */
	public void addRewardTokenToken(int amount){
		
		tokens.setRewardToken(tokens.getRewardToken() + amount);
		
	}
	
	/**
	 * 
	 */
	public void consumeRewardTokenToken(){
		
		if(tokens.getRewardToken() < 1) 
			throw new IndexOutOfBoundsException();
		else
			tokens.setRewardToken(tokens.getRewardToken() - 1);
		
	}
	
	/**
	 * 
	 * @param amount
	 */
	public void addTakeTileToken(int amount){
		
		tokens.setTakeTile(tokens.getTakeTile() + amount);
		
	}
	
	/**
	 * 
	 */
	public void consumeTakeTileToken(){
		
		if(tokens.getTakeTile() < 1) 
			throw new IndexOutOfBoundsException();
		else
			tokens.setTakeTile(tokens.getTakeTile() - 1);
		
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isConnected() {
		
		return connected;
		
	}
	
	/**
	 * 
	 * @param isConnected
	 */
	public void setConnected(boolean isConnected) {
		
		if (!connected && isConnected) {
			throw new UnsupportedOperationException("Reconnection isn\'t supported yet.");
		} else {
			connected = isConnected;
		}
		
	}
	
	/**
	 * 
	 * @param amount
	 */
	public void drawPoliticsCards(int amount) {
		
		PoliticsCardDeck deck = board.getPoliticsCardDeck();
		
		for(int i = 0; i<amount; i++) {
			
			this.receivePoliticsCard(deck.drawCard());
			
		}
		
	}
	
	/**
	 * Useful for debug.
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
