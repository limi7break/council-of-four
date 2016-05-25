package it.polimi.ingsw.ps13.model.player;

import java.awt.Color;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import it.polimi.ingsw.ps13.model.deck.PermitTile;
import it.polimi.ingsw.ps13.model.deck.PoliticsCard;

/**
 * 
 *
 */
public class Player implements Serializable {

	public static final long serialVersionUID = 0L;
	
	private final String name;
	private final Color color;
	private final PlayerSupply supply;
	private final Set<String> cityNames;
	private int nobilityPosition;
	private int mainActions;
	private boolean quickActionAvailable;
	
	public Player(String name, Color color, int position) { 
		
		this.name = name;
		this.color = color;
		supply = new PlayerSupply(position, color);
		cityNames = new TreeSet<>();
		
		nobilityPosition = 0;
		mainActions = 0;
		quickActionAvailable = false;
		
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
	 * @param index
	 * @return
	 */
	public PoliticsCard selectPoliticsCard(int index) {
		
		return supply.getPoliticsCards().get(index);
		
	}
	
	/**
	 * 
	 * @param cards
	 */
	public void discardPoliticsCards(Collection<PoliticsCard> cards) {
		
		supply.getPoliticsCards().removeAll(cards);
		
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
	 * 
	 * @param index
	 * @return
	 */
	public PermitTile selectPermitTile(int index) {
		
		return supply.getPermitTiles().get(index);
		
	}
	
	/**
	 * 
	 * @param position
	 * @param regionDeck
	 */
	public void givePermitTile(PermitTile tile) {
		
		supply.getPermitTiles().remove(tile);
		
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
	public int getMainActions() {
		
		return mainActions;
		
	}
	
	/**
	 * 
	 * @param amount
	 * @return
	 */
	public void addMainActions(int amount) {
		
		mainActions += amount;
		
	}
	
	/**
	 * 
	 */
	public void consumeMainAction() {
		
		mainActions--;
		
	}
	
	/**
	 * 
	 * @param quickActionAvailable
	 */
	public void setQuickActionAvailable(boolean quickActionAvailable) {
		
		if (!this.quickActionAvailable && quickActionAvailable) {
			throw new IllegalArgumentException("The possibility to perform a quick action cannot be regained in the same turn");
		} else {
			this.quickActionAvailable = quickActionAvailable;
		}
		
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isQuickActionAvailable() {
		
		return quickActionAvailable;
		
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
	public void setNobilityPosition(int nobilityPosition) {
		
		this.nobilityPosition = nobilityPosition;
		
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
	 * @return
	 */
	public List<PermitTile> getPermitTiles() {
		
		return supply.getPermitTiles();
		
	}
	
	/**
	 * 
	 * @param cityName
	 * @return
	 */
	public boolean canBuildOn(String cityName) {
		
		for(PermitTile p: getPermitTiles()) {
			
			if(p.getCityNames().contains(cityName) && !p.isUsed())
				return true;
			
		}
		
		return false;
		
	}
	
	
	/**
	 * Useful for debug.
	 * 
	 */
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("[Player]\n")
			.append("Name: ").append(name).append("\n")
			.append("Color: ").append("(").append(color.getRed()).append(", ").append(color.getGreen()).append(", ").append(color.getBlue()).append(")\n")
			.append(supply.toString()).append("\n")
			.append("Nobility position: ").append(nobilityPosition).append("\n")
			.append("Main actions: ").append(mainActions).append("\n")
			.append("Quick action available: ").append(quickActionAvailable).append("\n")
			.append("Cities: ").append(cityNames.toString()).append("\n");
			
			sb.append("\n");
			return sb.toString();
	
	}
	
}
