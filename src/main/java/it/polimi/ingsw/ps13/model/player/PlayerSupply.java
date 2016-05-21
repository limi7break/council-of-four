package it.polimi.ingsw.ps13.model.player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import it.polimi.ingsw.ps13.model.board.Emporium;
import it.polimi.ingsw.ps13.model.deck.PermitTile;
import it.polimi.ingsw.ps13.model.deck.PoliticsCard;
import it.polimi.ingsw.ps13.model.resource.Assistants;
import it.polimi.ingsw.ps13.model.resource.Coins;
import it.polimi.ingsw.ps13.model.resource.VictoryPoints;

/**
 * The equipment the player is given as he takes part of a game or as a gain during a game.
 *
 */
public class PlayerSupply implements Serializable  {

	private static final long serialVersionUID = 0L;
	
	private final VictoryPoints victoryPoints;
	private final Coins coins;
	private final Assistants assistants;
	private final List<Emporium> emporiums;
	private final List<PoliticsCard> politicsCards;
	private final List<PermitTile> permitTiles; 

	/**
	 * 
	 */
	public PlayerSupply(int position) {
		
		if(position < 0)
			throw new IllegalArgumentException();
		
		else{
			
			victoryPoints = new VictoryPoints(0);
			coins = new Coins(10 + position); 
			assistants = new Assistants(position + 1);
			emporiums = new ArrayList<>();
			politicsCards = new ArrayList<>();
			permitTiles = new ArrayList<>();
			
		}
			
	}
	
	/**
	 * 
	 */
	public int getVictoryPoints(){
		
		return victoryPoints.getAmount();
		
	}
	
	/**
	 * 
	 * @param amount
	 */
	public void addVictoryPoints(int amount){
		
		victoryPoints.add(amount);
		
	}
	
	/**
	 * 
	 * 
	 * @return
	 */
	public int getCoins(){
		
		return coins.getAmount();
		
	}
	
	/**
	 * 
	 * @param amount
	 */
	public void addCoins(int amount){
		
		coins.add(amount);
		
	}
	
	/**
	 * 
	 * @param amount
	 */
	public void consumeCoins(int amount){
		
		coins.consume(amount);
		
	}
	
	/**
	 * 
	 * @return
	 */
	public int getAssistants(){
		
		return assistants.getAmount();
		
	}
	
	/**
	 * 
	 * @param 
	 */
	public void addAssistants(int amount){
		
		assistants.add(amount);
		
	}
	
	/**
	 * 
	 * @param 
	 */
	public void consumeAssistants(int amount){
		
		assistants.consume(amount);
		
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Emporium> getEmporiums() {
		
		return Collections.unmodifiableList(emporiums);
		
	}
	
	/**
	 * 
	 * @return
	 */
	public Emporium removeEmporium() {
		
		return emporiums.remove(0);
		// check for end game, not here though
		
	}
	
	/**
	 * 
	 * @return
	 */
	public List<PoliticsCard> getPoliticsCards(){
		
		return Collections.unmodifiableList(politicsCards);
		
	}
	
	/**
	 * 
	 */
	public void addPoliticsCard(PoliticsCard card) {
		
		politicsCards.add(card);
		
	}
	
	/**
	 * 
	 */
	public void addPoliticsCards(Collection<PoliticsCard> cards) {
		
		politicsCards.addAll(cards);
		
	}
	
	/**
	 * 
	 * @param selectedCards
	 */
	public void removePoliticsCards(Collection<PoliticsCard> selectedCards) {
		
		if(politicsCards.containsAll(selectedCards)){
			
			politicsCards.removeAll(selectedCards);
			
		}
		
		else throw new IllegalArgumentException("Do not own all of these cards.");	
		
	}
	
	/**
	 * 
	 * @return
	 */
	public List<PermitTile> getPermitTiles(){
		
		return Collections.unmodifiableList(permitTiles);
		
	}
	
	/**
	 * 
	 * @param selectedPermitTiles
	 */
	public void removePermitTiles(Collection<PermitTile> selectedPermitTiles) {
		
		if(permitTiles.containsAll(selectedPermitTiles)){
			
			permitTiles.removeAll(selectedPermitTiles);
			
		}
		
		else throw new IllegalArgumentException("Do not own all of thes tiles.");
		
	}
	
	/**
	 * 
	 * @param obtained
	 */
	public void addPermitTile(PermitTile obtained) {
		
		permitTiles.add(obtained);
		
	}
	
}
