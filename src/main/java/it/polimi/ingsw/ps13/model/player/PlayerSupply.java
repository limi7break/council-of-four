package it.polimi.ingsw.ps13.model.player;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps13.model.deck.PermitTile;
import it.polimi.ingsw.ps13.model.deck.PoliticsCard;
import it.polimi.ingsw.ps13.model.resource.Assistants;
import it.polimi.ingsw.ps13.model.resource.Coins;
import it.polimi.ingsw.ps13.model.resource.Resource;
import it.polimi.ingsw.ps13.model.resource.VictoryPoints;

/**
 * The equipment the player is given as he takes part of a game or as a gain during a game.
 *
 */
public class PlayerSupply implements Serializable  {

	private static final long serialVersionUID = 0L;
	private static final int NUMBER_OF_EMPORIUMS = 10;
	
	private final VictoryPoints victoryPoints;
	private final Coins coins;
	private final Assistants assistants;
	private final List<Emporium> emporiums;
	private final List<PoliticsCard> politicsCards;
	private final List<PermitTile> permitTiles; 

	/**
	 * 
	 */
	protected PlayerSupply(int position, Color color) {
		
		if(position < 0) {
			throw new IllegalArgumentException();
		} else {
			victoryPoints = new VictoryPoints(0);
			coins = new Coins(10 + position); 
			assistants = new Assistants(position + 1);
			emporiums = new ArrayList<>();
			politicsCards = new ArrayList<>();
			permitTiles = new ArrayList<>();
		}
		
		for (int i=0; i<NUMBER_OF_EMPORIUMS; i++) {
			emporiums.add(new Emporium(color));
		}
			
	}
	
	/**
	 * 
	 */
	protected Resource getVictoryPoints() {
		
		return victoryPoints;
	
	}
	
	/**
	 * 
	 */
	protected Resource getCoins(){
		
		return coins;
		
	}
	
	/**
	 * 
	 * @return
	 */
	protected Resource getAssistants(){
		
		return assistants;
		
	}
	
	/**
	 * 
	 * @return
	 */
	protected List<Emporium> getEmporiums() {
		
		return emporiums;
		
	}
	
	/**
	 * 
	 * @return
	 */
	protected List<PoliticsCard> getPoliticsCards(){
		
		return politicsCards;
		
	}
	
	/**
	 * 
	 * @return
	 */
	protected List<PermitTile> getPermitTiles(){
		
		return permitTiles;
		
	}
	
	/**
	 * Useful for debug.
	 * 
	 */
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("Victory Points: ").append(victoryPoints.getAmount()).append("\n")
		  .append("Coins: ").append(coins.getAmount()).append("\n")
		  .append("Assistants: ").append(assistants.getAmount()).append("\n")
		  .append("Emporiums:\n").append(emporiums.size()).append("\n")
		  .append("Politics cards:\n").append(politicsCards.toString()).append("\n")
		  .append("Permit tiles:\n").append(permitTiles.toString()).append("\n");
		
		return sb.toString();
		
	}
	
}
