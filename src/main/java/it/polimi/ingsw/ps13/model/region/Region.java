package it.polimi.ingsw.ps13.model.region;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import it.polimi.ingsw.ps13.model.bonus.Bonus;
import it.polimi.ingsw.ps13.model.council.CouncillorBalcony;
import it.polimi.ingsw.ps13.model.deck.PermitTile;
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
	public Region(String name, Bonus bonus, CouncillorBalcony councillorBalcony, PermitTileDeck permitTileDeck){
		
		this.name = name;
		this.bonus = bonus;
		bonusAvailable = true;
		this.councillorBalcony = councillorBalcony;
		this.permitTileDeck = permitTileDeck;
		
		cityNames = new TreeSet<>(); 
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
	void addCityName(String name) {
		
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
	
	/**
	 * Used for Command Line Interface (CLI).
	 * 
	 */
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("\n[Region]\n\n")
		  .append("NAME: ").append(name).append("\n")
		  .append("CITIES: ").append(cityNames.toString()).append("\n")
		  .append("BONUS: ").append(bonus.toString())
		  .append("\navailable = ").append(bonusAvailable).append("\n\n")
		  .append("BALCONY: ").append(councillorBalcony.toString()).append("\n\n")
		  .append("PERMIT TILES:\n");

		List<PermitTile> visibleTiles = permitTileDeck.getVisibleTiles();
		  
		for (int i=0; i<visibleTiles.size(); i++) {
			sb.append("[" + i + "]")
			  .append(visibleTiles.get(i).toString()).append("\n");
		}
		
		return sb.toString();
		
	}
	
}
