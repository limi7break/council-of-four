package it.polimi.ingsw.ps13.model.player;

import java.awt.Color;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import it.polimi.ingsw.ps13.model.deck.PermitTileDeck;
import it.polimi.ingsw.ps13.model.deck.PoliticsCard;
import it.polimi.ingsw.ps13.model.deck.PoliticsCardDeck;

/**
 * 
 *
 */
public class Player implements Serializable {

	public static final long serialVersionUID = 0L;
	
	private final String name;
	private final Color color;
	private final PlayerSupply supply;
	private int nobilityPosition;
	private int mainActions;
	private boolean quickAction;
	private final Set<String> cityNames;
	
	public Player(String name, Color color, int position) { 
		
		this.name = name;
		this.color = color;
		supply = new PlayerSupply(position);
		
		nobilityPosition = 0;
		mainActions = 0;
		quickAction = false;
		cityNames = new TreeSet<>();
		
	}
	
	/**
	 * Adds a politics card, drawn from a politics card deck, to the hand of the player.
	 * 
	 * @param deck
	 */
	public void drawPoliticsCard(PoliticsCardDeck deck) {
		
		supply.addPoliticsCard(deck.drawCard());
		
	}
	
	/**
	 * Adds a number of politics cards, drawn from a politics card deck, to the hand of the player.
	 * 
	 * @param amount
	 * @param deck
	 */
	public void drawPoliticsCards(int number, PoliticsCardDeck deck) {
		 
		for(int i=0; i<number; i++){
			
			supply.addPoliticsCard(deck.drawCard());
			
		}
		
	}
	
	/**
	 * 
	 * @param usedCards
	 */
	public void discardPoliticsCard(Collection<PoliticsCard> usedCards, PoliticsCardDeck deck) {
		
		if(supply.getPoliticsCards().containsAll(usedCards) == true){
			
			supply.removePoliticsCards(usedCards);
			deck.discardCard(usedCards);
			
		}
		
		else throw new IllegalArgumentException("This player cannot use these cards.");
		
	}
	
	/**
	 * 
	 * @param position
	 * @param regionDeck
	 */
	public void takePermitTile(int position, PermitTileDeck regionDeck) {
		
		supply.addPermitTile(regionDeck.takeTile(position));
		
	}
	
	/**
	 * 
	 */
	public void removeEmporium() {
		
		supply.removeEmporium();
		//N.B. end of the game to be checked.
		
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
	 * @return
	 */
	public PlayerSupply getSupply() {
		
		return supply;
		
	}
	
	/**
	 * 
	 * @param mainActions
	 */
	public void setMainActions(int mainActions) {
		
		this.mainActions = mainActions;
		
	}
	
	/**
	 * 
	 * @param quickAction
	 */
	public void setQuickAction(boolean quickAction) {
		
		this.quickAction = quickAction;
		
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isQuickAction() {
		
		return quickAction;
		
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
	
}
