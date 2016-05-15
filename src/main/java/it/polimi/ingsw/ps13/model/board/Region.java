package it.polimi.ingsw.ps13.model.board;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import it.polimi.ingsw.ps13.model.bonus.Bonus;
import it.polimi.ingsw.ps13.model.city.City;
import it.polimi.ingsw.ps13.model.council.Councillor;
import it.polimi.ingsw.ps13.model.council.CouncillorBalcony;
import it.polimi.ingsw.ps13.model.deck.PermitTile;
import it.polimi.ingsw.ps13.model.deck.PermitTileDeck;

/**
 * 
 * @author alessiomongelluzzo
 *
 */
public class Region implements Serializable{

	private static final long serialVersionUID = 0L;
	
	private final String name;
	private final Set<City> citySet;
	private final Bonus bonusTile;
	private boolean bonusAvailable;
	private final CouncillorBalcony councillorBalcony;
	private final PermitTileDeck permitTileDeck;
	
	/**
	 * 
	 */
	public Region(String n, Set<City> cs, Bonus bonus, Collection<Councillor> c, Collection<PermitTile> pt){
		
		name = n;
		
		citySet = new HashSet<>(); 
		citySet.addAll(cs);
		
		bonusTile = bonus;
		bonusAvailable = true;
		
		councillorBalcony = new CouncillorBalcony(c);
		permitTileDeck = new PermitTileDeck(pt);
		
	}

	
	
	
	
	/**
	 * 
	 * @return
	 */
	public String getName() {
		
		return name;
		
	}
	
	/**
	 * 
	 * @return
	 */
	public Set<City> getCitySet() {
		
		return citySet;
		
	}
	
	/**
	 * 
	 * @return
	 */
	public Bonus getBonusTile() {
		
		return bonusTile;
		
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isBonusAvailable() {
		
		return bonusAvailable;
		
	}
	
	/**
	 * 
	 * @param bonusAvailable
	 */
	public void setBonusAvailable(boolean bonusAvailable) {
		
		this.bonusAvailable = bonusAvailable;
		
	}
	
	/**
	 * 
	 * @return
	 */
	public CouncillorBalcony getCouncillorBalcony() {
		
		return councillorBalcony;
		
	}
	
	/**
	 * 
	 * @return
	 */
	public PermitTileDeck getPermitTileDeck() {
		
		return permitTileDeck;
		
	}
	
	
	
	
}
