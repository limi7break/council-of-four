package it.polimi.ingsw.ps13.model.player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import it.polimi.ingsw.ps13.model.board.Emporium;
import it.polimi.ingsw.ps13.model.city.City;
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
	private final Collection<Emporium> emporiums;
	private final Collection<PoliticsCard> politicsCards;
	private final Collection<PermitTile> permitTiles; 
	private final Set<City> citySet; 

	/**
	 * 
	 */
	public PlayerSupply(int position) {
		
		if(position < 1) 
			throw new IllegalArgumentException();
		
		else{
			
			victoryPoints = new VictoryPoints(0);
			coins = new Coins(9 + position); 
			assistants = new Assistants(position);
			emporiums = new ArrayList<>();
			politicsCards = new ArrayList<>();	 // politicsCards are initialized together with Player  
			permitTiles = new ArrayList<>();
			citySet = new HashSet<>();
			
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
	 * @param vp
	 */
	public void addVictoryPoints(int vp){
		
		victoryPoints.add(vp);
		
	}
	
	/**
	 * 
	 * @param vp
	 */
	public void consumeVictoryPoints(int vp){
		
		victoryPoints.consume(vp);
		
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
	 * @param c
	 */
	public void addCoins(int c){
		
		coins.add(c);
		
	}
	
	/**
	 * 
	 * @param c
	 */
	public void consumeCoins(int c){
		
		coins.consume(c);
		
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
	 * @param a
	 */
	public void addAssistants(int a){
		
		assistants.add(a);
		
	}
	
	/**
	 * 
	 * @param a
	 */
	public void consumeAssistants(int a){
		
		assistants.consume(a);
		
	}
	
	/**
	 * 
	 * @return
	 */
	public Collection<PoliticsCard> getPoliticsCards(){
		
		return politicsCards;
		
	}
	
	/**
	 * 
	 * @param pc
	 */
	public void setPoliticsCards(Collection<PoliticsCard> pc){
		
		politicsCards.clear();
		politicsCards.addAll(pc);
		
	}
	
	/**
	 * 
	 * @return
	 */
	public Collection<PermitTile> getPermitTiles(){
		
		return permitTiles;
		
	}
	
	/**
	 * 
	 * @param pt
	 */
	public void setPermitTiles(Collection<PermitTile> pt){
		
		permitTiles.clear();
		permitTiles.addAll(pt);
		
	}
	
	/**
	 * 
	 * @return
	 */
	public Collection<City> getCitySet(){
		
		return citySet;
		
	}
	
	/**
	 * 
	 * @param cl
	 */
	public void setCitySet(Collection<City> cl){
		
		citySet.clear();
		citySet.addAll(cl);
		
	}
	
	/**
	 * 
	 * @return
	 */
	public Collection<Emporium> getEmporiums() {
		
		return emporiums;
		
	}
	
}
