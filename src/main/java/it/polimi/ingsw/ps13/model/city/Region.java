package it.polimi.ingsw.ps13.model.city;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import it.polimi.ingsw.ps13.model.bonus.Bonus;
import it.polimi.ingsw.ps13.model.council.CouncillorBalcony;
import it.polimi.ingsw.ps13.model.deck.PermitTileDeck;

/**
 * 
 *
 */
public class Region implements Serializable{

	private static final long serialVersionUID = 0L;
	
	private final String name;
	private final Set<String> cityNames;
	private final Bonus bonus;
	private boolean bonusAvailable;
	private final CouncillorBalcony councillorBalcony;
	private final PermitTileDeck permitTileDeck;
	
	/**
	 * 
	 */
	protected Region(String name, Bonus bonus, CouncillorBalcony councillorBalcony, PermitTileDeck permitTileDeck){
		
		this.name = name;
		this.bonus = bonus;
		bonusAvailable = true;
		this.councillorBalcony = councillorBalcony;
		this.permitTileDeck = permitTileDeck;
		
		cityNames = new HashSet<>(); 
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
	public Bonus getBonus() {
		
		return bonus;
		
	}
	
	/**
	 * 
	 * @return the cities of this region
	 */
	public Set<String> getCityNames() {
		
		return Collections.unmodifiableSet(cityNames);
		
	}
	
	/**
	 * 
	 * @param city
	 */
	public void addCityName(String name) {
		
		cityNames.add(name);
		
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
	public void setBonusAvailable(boolean regionBonusAvailable) {
		
		if (!this.bonusAvailable && regionBonusAvailable) {
			throw new IllegalArgumentException("Bonus relative to a region cannot be reset to true");
		} else {
			this.bonusAvailable = regionBonusAvailable;
		}
		
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
